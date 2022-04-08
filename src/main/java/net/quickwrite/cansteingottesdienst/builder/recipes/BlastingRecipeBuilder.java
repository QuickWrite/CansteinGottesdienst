package net.quickwrite.cansteingottesdienst.builder.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.java.JavaPlugin;

public class BlastingRecipeBuilder extends FurnaceRecipeBuilder {

    public BlastingRecipeBuilder(JavaPlugin main, String name, ItemStack output) {
        super(main, name, output);
        experience = 1;
        cookingTime = 100;
    }

    @Override
    public Recipe build() {
        if (can_input1 != null){
            return new BlastingRecipe(key, output, can_input1, experience, cookingTime);
        }
        return new BlastingRecipe(key, output, can_input2, experience, cookingTime);
    }
}
