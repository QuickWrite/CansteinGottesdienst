package net.quickwrite.cansteingottesdienst.listener;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.blocks.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class ConveyorListener implements Listener {

    private HashMap<ArmorStand, Location> movingArmorstands;
    public static final float SPEED = 0.1f;

    public ConveyorListener(){
        movingArmorstands = new HashMap<>();

        startMovingTask();
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        trackItem(event.getItemDrop());
    }

    public void trackItem(Item item){
        new BukkitRunnable(){

            @Override
            public void run() {
                if(item.isOnGround()) {
                    if(item.getLocation().subtract(0, 3, 0).getBlock().getType().equals(Material.MAGENTA_GLAZED_TERRACOTTA)){
                        convertItem(item);
                    }
                    cancel();
                }
            }
        }.runTaskTimer(CansteinGottesdienst.getInstance(), 0, 1);
    }

    public void startMovingTask(){
        new BukkitRunnable(){

            @Override
            public void run() {
                ArrayList<ArmorStand> removeStands = new ArrayList<>();
                for(ArmorStand stand : movingArmorstands.keySet()) {

                    Location to = movingArmorstands.get(stand);
                    if (stand.getLocation().distance(to) < 0.05) {
                        Location loc = stand.getLocation();
                        Vector d = getDirection(getLocation(stand));
                        if (d == null) {
                            removeStands.add(stand);
                            stand.remove();
                            continue;
                        }
                        movingArmorstands.put(stand, loc.clone().add(d.normalize().multiply(-1)));
                    }
                    stand.teleport(stand.getLocation().add(stand.getLocation().subtract(movingArmorstands.get(stand)).toVector().normalize().multiply(-SPEED)));
                }
                for(ArmorStand stand : removeStands){
                    movingArmorstands.remove(stand);
                }
            }
        }.runTaskTimer(CansteinGottesdienst.getInstance(), 0, 1);
    }

    public void convertItem(Item item){
        ArmorStand stand = item.getLocation().getWorld().spawn(CustomBlock.normalizeLocation(item.getLocation()).subtract(0, 1.69, 0.75), ArmorStand.class, entity -> {
            entity.getEquipment().setItem(EquipmentSlot.HEAD, item.getItemStack());
            entity.setHeadPose(new EulerAngle(Math.toRadians(90), 0, 0));
            entity.setInvulnerable(true);
            entity.setGravity(false);
            entity.setInvisible(true);
        });
        movingArmorstands.put(stand, stand.getLocation());
    }

    public Location getLocation(ArmorStand stand){
        return stand.getLocation().subtract(0, 0, -0.75);
    }

    public Vector getDirection(Location loc){
        Block b = loc.getWorld().getBlockAt(loc.add(0, -1, 0));
        if(b.getBlockData() instanceof Directional){
            Directional d = (Directional) b.getBlockData();
            return d.getFacing().getDirection();
        }
        return null;
    }

}
