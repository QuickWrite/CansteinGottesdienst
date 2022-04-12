package net.quickwrite.cansteingottesdienst.items;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Items {

    BREAD(new ItemBuilder(Material.BREAD).setDisplayName("§6Bread").build()),
    ;

    private final ItemStack stack;

    Items(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public String getName(){
        return ChatColor.stripColor(stack.getItemMeta().getDisplayName());
    }

    public boolean matches(String inp){
        return getName().equalsIgnoreCase(inp);
    }
}
