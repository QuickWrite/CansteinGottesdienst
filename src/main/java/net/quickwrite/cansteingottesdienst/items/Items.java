package net.quickwrite.cansteingottesdienst.items;

import net.quickwrite.cansteingottesdienst.builder.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Items {

    BREAD(new ItemBuilder(Material.BREAD).setDisplayName("§6Brot").build()),
    CELERY(new ItemBuilder(Material.CARROT).setDisplayName("§bSellerie").setCustomModelData(1).build()),
    COOKED_LAMB_GIGOT(new ItemBuilder(Material.COOKED_MUTTON).setDisplayName("§eGekochte Lammkeule").setCustomModelData(1).build()),
    EMPTY_WINE_BOTTLE(new ItemBuilder(Material.BUCKET).setDisplayName("§5Leere Weinflasche").setCustomModelData(1).build()),
    FLOUR(new ItemBuilder(Material.SUGAR).setDisplayName("§6Mehl").setCustomModelData(1).build()),
    GRAPES(new ItemBuilder(Material.SWEET_BERRIES).setDisplayName("§5Trauben").setCustomModelData(1).build()),
    LAMB_GIGOT(new ItemBuilder(Material.MUTTON).setDisplayName("§eLammkeule").setCustomModelData(1).build()),
    OX_TONGUE(new ItemBuilder(Material.DANDELION).setDisplayName("§2Bitterkraut").setCustomModelData(1).build()),
    OX_TONGUE_POWDER(new ItemBuilder(Material.APPLE).setDisplayName("§2Bitterkraut Pulver").setCustomModelData(1).build()),
    WINE(new ItemBuilder(Material.MILK_BUCKET).setDisplayName("§5Wein").setCustomModelData(1).build())
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
