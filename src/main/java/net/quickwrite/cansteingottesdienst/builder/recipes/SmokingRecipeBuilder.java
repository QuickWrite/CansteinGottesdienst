package net.quickwrite.cansteingottesdienst.builder.recipes;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class SmokingRecipeBuilder extends FurnaceRecipeBuilder {

    public SmokingRecipeBuilder(JavaPlugin main, String name, ItemStack output) {
        super(main, name, output);
        cookingTime = 100;
        experience = 1;
    }

    @Override
    public Recipe build() {
        if(can_input1 != null){
            return new SmokingRecipe(key, output, can_input1, experience, cookingTime);
        }
        return new SmokingRecipe(key, output, can_input2, experience, cookingTime);
    }
}
