package net.quickwrite.cansteingottesdienst.blocks;

import net.quickwrite.cansteingottesdienst.CansteinGottesdienst;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public abstract class CustomBlock_copy {

    public static final NamespacedKey BLOCK_KEY = new NamespacedKey(CansteinGottesdienst.getInstance(), "customBlock");

    protected ItemStack headItem, dropStack, invItem;
    protected Material baseBlock;
    protected HashMap<Location, ArmorStand> armorstands;
    protected String identifier;

    public CustomBlock_copy(String identifier, ItemStack headItem, ItemStack dropStack, ItemStack invItem, Material baseBlock) {
        this.identifier = identifier;
        this.headItem = headItem;
        this.dropStack = dropStack;
        this.invItem = invItem;
        this.baseBlock = baseBlock;
        armorstands = new HashMap<>();
    }

    public void fillArmorstands(ConfigurationSection section){
        int size = section.getInt("size");
        for(int i = 0; i < size; i++){
            WorldBlockPair pair = (WorldBlockPair) section.get("blocks." + i);
            if(pair == null) continue;
            if(pair.isValid()){
                armorstands.put(normalizeLocation(pair.getLoc()), pair.getArmorStand());
            }else{
                pair.getLoc().getBlock().setType(Material.AIR);
            }
        }
    }

    public boolean onBlockPlace(Location loc){
        final Location l = normalizeLocation(loc);
        if(!l.getBlock().isEmpty()) return false;
        Location def = l.clone();
        l.add(.5, -1, .5);
        ArmorStand armorStand = l.getWorld().spawn(l, ArmorStand.class);
        armorStand.getEquipment().setItem(EquipmentSlot.HEAD, headItem);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.getPersistentDataContainer().set(BLOCK_KEY, PersistentDataType.INTEGER, 1);


        for(EquipmentSlot e : EquipmentSlot.values()){
            armorStand.addEquipmentLock(e, ArmorStand.LockType.ADDING_OR_CHANGING);
        }
        armorstands.put(def, armorStand);
        new BukkitRunnable() {
            @Override
            public void run() {
                l.add(0, 1, 0).getBlock().setType(baseBlock, false);
            }
        }.runTaskLater(CansteinGottesdienst.getInstance(), 1);
        return true;
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

    public int removeBlocks() {
        if (armorstands.isEmpty())
            return 0;

        int length = armorstands.size();

        for(Location loc : armorstands.keySet()){
            loc.getBlock().setType(Material.AIR);

            ArmorStand armorStand = armorstands.get(loc);

            if (armorStand == null) {
                length--;

                continue;
            }

            armorStand.remove();
        }
        armorstands.clear();

        return length;
    }

    public boolean isCustomBlock(Location location) {
        return armorstands.containsKey(normalizeLocation(location));
    }

    public void dropItem(Location loc){
        Item i = loc.getWorld().spawn(loc, Item.class);
        i.setItemStack(dropStack);
    }

    public Location normalizeLocation(Location l){
        return new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ(), 0, 0);
    }

    public void serialize(FileConfiguration config) {
        config.set("cbs." + identifier + ".size", armorstands.size());
        int i = 0;
        config.set("cbs." + identifier + ".blocks", null);
        for(Location loc : armorstands.keySet()){
            config.set("cbs." + identifier + ".blocks." + i, new WorldBlockPair(loc, armorstands.get(loc).getUniqueId()));
            i++;
        }
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

    public ItemStack getHeadItem() {
        return headItem;
    }

    public ItemStack getDropStack() {
        return dropStack;
    }

    public HashMap<Location, ArmorStand> getArmorstands() {
        return armorstands;
    }
}
