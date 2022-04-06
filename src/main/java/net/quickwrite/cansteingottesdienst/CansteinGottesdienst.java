package net.quickwrite.cansteingottesdienst;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import net.quickwrite.cansteingottesdienst.commands.rlgl.RedLightGreenLightCommand;
import net.quickwrite.cansteingottesdienst.rlgl.RedLightGreenLightGame;
import net.quickwrite.cansteingottesdienst.rlgl.RedLightGreenLightSettings;
import net.quickwrite.cansteingottesdienst.tabcomplete.RedLightGreenLightTabCompleter;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class CansteinGottesdienst extends JavaPlugin {

    private static CansteinGottesdienst instance;
    public static StateFlag INFINITE_CROPS;
    public static String PREFIX = "[Gottesdienst] ";
    public static final String PATH = "canstein";

    private RedLightGreenLightGame raceGame;

    @Override
    public void onEnable() {
        instance = this;
        ConfigurationSerialization.registerClass(RedLightGreenLightSettings.class);

        getCommand("rlgl").setExecutor(new RedLightGreenLightCommand());
        getCommand("rlgl").setTabCompleter(new RedLightGreenLightTabCompleter());
    }

    @Override
    public void onLoad(){
        initializeWorldGuard();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initializeWorldGuard() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

        INFINITE_CROPS = addFlag(registry, "infinite-crops", false);
    }

    public StateFlag addFlag(FlagRegistry registry, String name, boolean def){
        try{
            StateFlag flag = new StateFlag(name, def);
            registry.register(flag);
            return flag;
        }catch (FlagConflictException e){
            Flag<?> existing = registry.get(name);
            if(existing instanceof StateFlag){
                return (StateFlag) existing;
            }else{
                throw new RuntimeException("Somehow the '" + name + "' flag cannot be initialized!\n" +
                        "Check if another plugin is conflicting with the flags.");
            }
        }
    }

    public boolean initGame(Player player){
        if(raceGame != null) {
            raceGame.stop();
        }
        raceGame = new RedLightGreenLightGame(player.getWorld());
        if(!raceGame.isValid()) {
            raceGame = null;
            return false;
        }
        raceGame.start();
        return true;
    }

    public RedLightGreenLightGame getRaceGame() {
        return raceGame;
    }

    public static CansteinGottesdienst getInstance() {
        return instance;
    }

    public void stopGame() {
        if(raceGame != null){
            raceGame.stop();
        }
        raceGame = null;
    }
}
