package net.quickwrite.cansteingottesdienst.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CustomBlock {

    protected ItemStack headItem, dropStack, invItem;
    protected Material baseBlock;
    protected ArrayList<Location> locations;
    protected HashMap<Location, ArmorStand> armorstands;

    public CustomBlock(ItemStack headItem, ItemStack dropStack, ItemStack invItem, Material baseBlock) {
        this.headItem = headItem;
        this.dropStack = dropStack;
        this.invItem = invItem;
        this.baseBlock = baseBlock;
        locations = new ArrayList<>();
        armorstands = new HashMap<>();
    }

    public void onBlockPlace(Location l){
        l = normalizeLocation(l);
        Location def = l.clone();
        locations.add(def);
        l.add(.5, 0, .5);
        ArmorStand armorStand = l.getWorld().spawn(l, ArmorStand.class);
        //armorStand.setInvisible(true);
        armorStand.getEquipment().setItem(EquipmentSlot.HEAD, headItem);

        for(EquipmentSlot e : EquipmentSlot.values()){
            armorStand.addEquipmentLock(e, ArmorStand.LockType.ADDING_OR_CHANGING);
        }
        armorstands.put(l, armorStand);
        l.getBlock().setType(baseBlock);
    }

    public boolean onBlockBreak(Player p, Location loc){
        loc = normalizeLocation(loc);
        ArmorStand armorStand = armorstands.get(loc);
        if(armorStand != null) armorStand.remove();
        loc.getBlock().setType(Material.AIR);
        return true;
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
}
