package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Consumer;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;

public abstract class CustomBlock {

    public static final NamespacedKey BLOCK_KEY = new NamespacedKey(CansteinGottesdienst.getInstance(), "customBlock");
    public static final NamespacedKey CUSTOM_BLOCK_TYPE_KEY = new NamespacedKey(CansteinGottesdienst.getInstance(), "customBlockTypeKey");

    protected ItemStack headItem, dropStack, invItem;
    protected String identifier;
    protected CustomBlock convertTo;

    public CustomBlock(String identifier, ItemStack headItem, ItemStack dropStack, ItemStack invItem) {
        this.identifier = identifier;
        this.headItem = headItem;
        this.dropStack = dropStack;
        this.invItem = invItem;
    }

    public boolean onBlockPlace(Location loc){
        final Location l = normalizeLocation(loc);
        if(!l.getBlock().isEmpty()) return false;
        l.getWorld().spawn(l, ArmorStand.class, armorStand1 -> {
            armorStand1.getEquipment().setItem(EquipmentSlot.HEAD, headItem);
            armorStand1.setGravity(false);
            //armorStand.setInvulnerable(true);
            armorStand1.setInvisible(true);
            armorStand1.getPersistentDataContainer().set(BLOCK_KEY, PersistentDataType.INTEGER, 1);
            armorStand1.getPersistentDataContainer().set(CUSTOM_BLOCK_TYPE_KEY, PersistentDataType.STRING, identifier);

            for(EquipmentSlot e : EquipmentSlot.values()){
                armorStand1.addEquipmentLock(e, ArmorStand.LockType.ADDING_OR_CHANGING);
            }
        });

        return true;
    }

    public int removeBlocks(World world) {
        int length = 0;
        for(Entity e : world.getEntities()){
            if(!(e instanceof ArmorStand)) continue;
            if(!e.getPersistentDataContainer().getOrDefault(CUSTOM_BLOCK_TYPE_KEY, PersistentDataType.STRING, "").equalsIgnoreCase(identifier)) continue;
            e.remove();
            length++;
        }
        return length;
    }

    public static ArmorStand getCustomBlockAt(Location location){
        Collection<Entity> entities = location.getWorld().getNearbyEntities(
                normalizeLocation(location),
                0.1,
                0.1,
                0.1,
                entity -> entity instanceof ArmorStand
        );

        for(Entity e : entities){
            if(e.getPersistentDataContainer().getOrDefault(BLOCK_KEY, PersistentDataType.INTEGER, 0) == 1)
                return (ArmorStand) e;
        }
        return null;
    }

    public static boolean isCustomBlock(Location location) {
        return getCustomBlockAt(location) != null;
    }

    public void dropItem(Location loc){
        if(dropStack == null) return;
        Item i = loc.getWorld().spawn(loc, Item.class, item -> {
            item.setItemStack(dropStack);
        });
    }

    public static Location normalizeLocation(Location l){
        return new Location(l.getWorld(), l.getBlockX() + 0.5, l.getBlockY(), l.getBlockZ() +  0.5, 0, 0);
    }

    public void giveItem(Player p){
        p.getInventory().addItem(invItem);
    }

    public ItemStack getInvItem() {
        return invItem;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ItemStack getHeadItem() {
        return headItem;
    }

    public ItemStack getDropStack() {
        return dropStack;
    }

    public CustomBlock getConvertTo() {
        return convertTo == null ? this : convertTo;
    }
}
