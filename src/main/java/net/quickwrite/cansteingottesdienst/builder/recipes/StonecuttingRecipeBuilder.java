package net.quickwrite.cansteingottesdienst.builder.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class StonecuttingRecipeBuilder extends RecipeBuilder {
    private RecipeChoice can_input1;
    private Material can_input2;

    public StonecuttingRecipeBuilder(JavaPlugin main, String name, ItemStack output) {
        super(main, name, output);
    }

    public StonecuttingRecipeBuilder addInput(RecipeChoice choice){
        can_input1 = choice;
        return this;
    }

    public StonecuttingRecipeBuilder addInput(Material m){
        can_input2 = m;
        return this;
    }

    @Override
    public Recipe build() {
        if (can_input1 != null) {
            return new StonecuttingRecipe(key, output, can_input1);
        }
        return new StonecuttingRecipe(key, output, can_input2);
    }
}