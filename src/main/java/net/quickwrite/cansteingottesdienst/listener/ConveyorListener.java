package net.quickwrite.cansteingottesdienst.listener;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import net.quickwrite.cansteingottesdienst.blocks.CustomBlock;
import net.quickwrite.cansteingottesdienst.items.Items;
import net.quickwrite.cansteingottesdienst.util.worldguard.WorlGuardUtil;
import net.quickwrite.cansteingottesdienst.util.storage.Flags;
import net.quickwrite.cansteingottesdienst.util.storage.WinepressList;
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
import org.bukkit.inventory.ItemStack;
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
                            endConvertItem(stand.getEquipment().getHelmet(), stand.getLocation());

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
        if(item.getItemStack().getType().isAir()) return;
        ArmorStand stand = item.getLocation().getWorld().spawn(CustomBlock.normalizeLocation(item.getLocation()).subtract(0, 1.69, 0.75), ArmorStand.class, entity -> {
            entity.getEquipment().setItem(EquipmentSlot.HEAD, item.getItemStack());
            entity.setHeadPose(new EulerAngle(Math.toRadians(90), 0, 0));
            entity.setInvulnerable(true);
            entity.setGravity(false);
            entity.setInvisible(true);
        });
        movingArmorstands.put(stand, stand.getLocation());

        item.remove();
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

    private void endConvertItem(ItemStack itemStack, Location location) {
        ItemStack drop;

        if (itemStack.getType().equals(Material.WHEAT)) {
            drop = Items.FLOUR.getItemStack().clone();
            drop.setAmount(itemStack.getAmount());
        } else if (itemStack.isSimilar(Items.GRAPES.getItemStack())) {
            ApplicableRegionSet regionSet = WorlGuardUtil.getRegionSet(location.add(0,2,0).getBlock());

            Integer result = regionSet.queryValue(
                    WorlGuardUtil.getBukkitPlayer(Bukkit.getOnlinePlayers().iterator().next()),
                    Flags.WINE_PRESS);

            if (result == null) {
                drop = itemStack;
            } else {
                for (ProtectedRegion region : regionSet.getRegions()) {
                    Integer flag = region.getFlag(Flags.WINE_PRESS);

                    if (flag == null)
                        continue;

                    WinepressList.WinepressInfo info = WinepressList.getInfo(region, location.getWorld());
                    info.setMax(flag);
                    info.bump(itemStack.getAmount());
                }
                return;
            }
        } else {
            drop = itemStack;
        }

        location.getWorld().dropItem(location.add(0, 1.7, 0.75), drop);
    }

}
