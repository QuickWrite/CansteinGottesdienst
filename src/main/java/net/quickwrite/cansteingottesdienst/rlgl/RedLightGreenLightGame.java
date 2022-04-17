package net.quickwrite.cansteingottesdienst.rlgl;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RedLightGreenLightGame {

    private final ArrayList<Player> playingPlayers;
    private ArrayList<Player> alivePlayers;
    private ArrayList<Player> onlySound;
    private BukkitTask current;
    private final Random random;
    private final HashMap<Player, Location> startingLocations;
    private final RedLightGreenLightSettings settings;
    public static final String SETTINGS_PATH = CansteinGottesdienst.PATH + ".rlgl.settings";

    public RedLightGreenLightGame(World world){
        playingPlayers = new ArrayList<>();
        onlySound = new ArrayList<>();
        for(Player p : world.getPlayers()){
            if(!p.hasPermission("canstein.rlgl.bypass")) {
                playingPlayers.add(p);
            }else{
                onlySound.add(p);
            }
        }
        alivePlayers = (ArrayList<Player>) playingPlayers.clone();
        random = new Random();
        startingLocations = new HashMap<>();
        settings = (RedLightGreenLightSettings) CansteinGottesdienst.getInstance().getConfig().get(SETTINGS_PATH);
        System.out.println(settings);
    }

    public boolean isValid(){
        return settings != null;
    }

    public void start() {
        startRound();
        for(Player p : playingPlayers){
            startingLocations.put(p, p.getLocation());
        }
    }

    private void startRound(){
        int[] delay = {random.nextInt(settings.getMaxTime() - settings.getMinTime()) + settings.getMinTime()};
        for(Player p : playingPlayers){
            p.playSound(p.getLocation(), settings.getSound(), 10, 1);
            p.sendTitle(settings.getTitleRun(), "", 0, 20*2, 0);
        }
        for(Player p : onlySound){
            p.playSound(p.getLocation(), settings.getSound(), 10, 1);
            p.sendTitle(settings.getTitleRun(), "", 0, 20*2, 0);
        }
        current = new BukkitRunnable(){

            @Override
            public void run() {
                if(delay[0] <= 0){
                    //Finish Players have to stop
                    cancel();
                    startHaltListener();

                    Bukkit.getOnlinePlayers().stream()
                            .filter(player -> player.hasPermission("canstein.rlgl.bypass"))
                            .forEach(player -> player.sendMessage("Das Spiel hat gestoppt!"));
                }
                if(delay[0] == 20) {
                    for(Player p : playingPlayers){
                        p.stopSound(settings.getSound());
                        p.sendTitle(settings.getTitleHalt(), "", 0, 20*2, 0);
                    }
                    for(Player p : onlySound){
                        p.stopSound(settings.getSound());
                        p.sendTitle(settings.getTitleHalt(), "", 0, 20*2, 0);
                    }
                }
                for(int i = playingPlayers.size() - 1; i >= 0; i--){
                    Player p = playingPlayers.get(i);
                    if(hasFinished(p)){
                        playingPlayers.remove(p);
                        p.stopSound(settings.getSound());
                        if(playingPlayers.size() == 0){
                            CansteinGottesdienst.getInstance().stopGame();
                        }
                        break;
                    }
                }
                delay[0] --;
            }
        }.runTaskTimer(CansteinGottesdienst.getInstance(), 0, 1);
    }

    private boolean hasFinished(Player p){
        Location l = p.getLocation();
        p.sendMessage("Du hast es geschafft!");
        return l.getBlockX() * settings.getDirection().getxMod() > settings.getX() || l.getBlockZ() * settings.getDirection().getzMod() > settings.getZ();
    }

    private void startHaltListener() {
        alivePlayers = (ArrayList<Player>) playingPlayers.clone();
        HashMap<Player, Location> locations = new HashMap<>();
        for(Player p : alivePlayers){
            locations.put(p, p.getLocation());
        }
        int[] delay = {settings.getHaltTime()};
        current = new BukkitRunnable(){
            @Override
            public void run() {
                if(delay[0] <= 0){
                    //Finish Players have to stop
                    cancel();
                    startRound();
                }else{
                    for(Player p : playingPlayers){
                        if(!alivePlayers.contains(p)){
                            if(p.isOnGround()){
                                alivePlayers.add(p);
                                locations.put(p, p.getLocation());
                                p.setVelocity(p.getVelocity().setX(0).setZ(0));
                            }
                        }
                    }
                    for(int i = alivePlayers.size() - 1; i >= 0; i--){
                        Player p = alivePlayers.get(i);
                        if(p.getLocation().distance(locations.get(p)) > 0.1){
                            alivePlayers.remove(p);
                            p.setVelocity(p.getLocation().toVector().subtract(startingLocations.get(p).toVector()).normalize().multiply(-1.4).setY(.8)); // Push back
                        }
                    }
                }
                delay[0] --;
            }
        }.runTaskTimer(CansteinGottesdienst.getInstance(), 0, 1);
    }

    public static void setSettings(Player p){
        FileConfiguration config = CansteinGottesdienst.getInstance().getConfig();
        RedLightGreenLightSettings set = (RedLightGreenLightSettings) config.get(SETTINGS_PATH);
        if(set != null) set.set(Direction.fromPitch((int) p.getEyeLocation().getYaw()), p.getLocation().getBlockX(), p.getLocation().getBlockZ());
        else set = new RedLightGreenLightSettings(Direction.fromPitch((int) p.getEyeLocation().getYaw()), p.getLocation().getBlockX(), p.getLocation().getBlockZ());
        config.set(SETTINGS_PATH, set);
        CansteinGottesdienst.getInstance().saveConfig();
    }

    public void stop(){
        if(current != null){
            current.cancel();
            current = null;
        }
    }
}
