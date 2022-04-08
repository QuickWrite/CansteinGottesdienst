package net.quickwrite.cansteingottesdienst.builder.recipes;

import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

public class CampfireRecipeBuilder extends FurnaceRecipeBuilder {

    public CampfireRecipeBuilder(JavaPlugin main, String name, ItemStack output) {
        super(main, name, output);
        cookingTime = 600;
        experience = 1;
    }

    @Override
    public Recipe build() {
        if(can_input1 != null){
            return new CampfireRecipe(key, output, can_input1, experience, cookingTime);
        }
        return new CampfireRecipe(key, output, can_input2, experience, cookingTime);
    }
}
