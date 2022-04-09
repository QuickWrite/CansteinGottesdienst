package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CustomBlock {

    protected ItemStack headItem, dropStack, invItem;
    protected Material baseBlock;
    protected ArrayList<Location> locations;
    protected HashMap<Location, ArmorStand> armorstands;
    protected String identifier;

    public CustomBlock(String identifier, ItemStack headItem, ItemStack dropStack, ItemStack invItem, Material baseBlock) {
        this.identifier = identifier;
        this.headItem = headItem;
        this.dropStack = dropStack;
        this.invItem = invItem;
        this.baseBlock = baseBlock;
        locations = new ArrayList<>();
        armorstands = new HashMap<>();
    }

    public void onBlockPlace(Location loc){
        final Location l = normalizeLocation(loc);
        Location def = l.clone();
        locations.add(def);
        l.add(.5, 0, .5);
        ArmorStand armorStand = l.getWorld().spawn(l, ArmorStand.class);
        armorStand.getEquipment().setItem(EquipmentSlot.HEAD, headItem);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);

        for(EquipmentSlot e : EquipmentSlot.values()){
            armorStand.addEquipmentLock(e, ArmorStand.LockType.ADDING_OR_CHANGING);
        }
        armorstands.put(def, armorStand);
        new BukkitRunnable() {
            @Override
            public void run() {
                l.getBlock().setType(baseBlock);
            }
        }.runTaskLater(CansteinGottesdienst.getInstance(), 1);
        //
    }

    public boolean onBlockBreak(Player p, Location loc){
        loc = normalizeLocation(loc);
        if(!armorstands.containsKey(loc)) return false;

        ArmorStand armorStand = armorstands.get(loc);
        armorStand.remove();
        armorstands.remove(loc);
        loc.getBlock().setType(Material.AIR);
        return true;
    }

    public boolean isCustomBlock(Location location) {
        return armorstands.containsKey(location);
    }

    public void dropItem(Location loc){
        Item i = loc.getWorld().spawn(loc, Item.class);
        i.setItemStack(dropStack);
    }

    public Location normalizeLocation(Location l){
        return new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ(), 0, 0);
    }


    public void giveItem(Player p){
        p.getInventory().addItem(invItem);
    }

    public ItemStack getInvItem() {
        return invItem;
    }

    public Material getBaseBlock() {
        return baseBlock;
    }

    public String getIdentifier() {
        return identifier;
    }
}
