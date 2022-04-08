package net.quickwrite.cansteingottesdienst.builder.recipes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class RecipeBuilder {

    protected NamespacedKey key;
    protected ItemStack output;

    public RecipeBuilder(JavaPlugin main, String name, ItemStack output){
        key = new NamespacedKey(main, name);
        this.output = output;
    }

    public abstract Recipe build();
}
