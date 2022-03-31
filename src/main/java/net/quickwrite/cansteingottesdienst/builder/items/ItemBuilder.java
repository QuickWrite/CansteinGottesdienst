package net.quickwrite.cansteingottesdienst.builder.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material){
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(Material material, int subID){
        this.itemStack = new ItemStack(material, 1 , (short) subID);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder setCustomModelData(int value){
        ItemMeta meta = itemStack.getItemMeta();
        meta.setCustomModelData(value);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setAmount(int amount){
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder createHeadItem(Player p){
        if(this.itemStack.getType() == Material.PLAYER_HEAD) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwner(p.getName());
            skullMeta.setDisplayName(p.getName());
            itemStack.setItemMeta(skullMeta);
        }
        return this;

    }

    public ItemBuilder setDisplayName(String name){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(name);
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addItemFlags(itemFlags);
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(List<String> list){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setLore(list);
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addEnchant(enchantment, level, true);
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setDamage(int damage){
        ItemMeta meta = this.itemStack.getItemMeta();
        if(meta != null) {
            if (meta instanceof Damageable) {
                ((Damageable) meta).setDamage(damage);
                this.itemStack.setItemMeta(meta);
            }
        }
        return this;
    }

    public ItemBuilder setOwner(String name){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if(itemMeta != null) {
            if (itemMeta instanceof SkullMeta) {
                SkullMeta meta = (SkullMeta) itemMeta;
                meta.setOwner(name);
                this.itemStack.setItemMeta(meta);
            }
        }
        return this;
    }

    public ItemBuilder setColor(Color color){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if(itemMeta != null) {
            if (itemMeta instanceof LeatherArmorMeta) {
                LeatherArmorMeta meta = (LeatherArmorMeta) itemMeta;
                meta.setColor(color);
                this.itemStack.setItemMeta(meta);
            }
        }
        return this;
    }

    public ItemBuilder setUnbreakable(){
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setUnbreakable(true);
        }
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build(){
        return this.itemStack;
    }
}
