package net.quickwrite.cansteingottesdienst.builder.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.java.JavaPlugin;

public class FurnaceRecipeBuilder extends RecipeBuilder {
    protected RecipeChoice can_input1;
    protected Material can_input2;
    protected float experience;
    protected int cookingTime;

    public FurnaceRecipeBuilder(JavaPlugin main, String name, ItemStack output) {
        super(main, name, output);
        cookingTime = 200;
        experience = 1;
    }

    public FurnaceRecipeBuilder setInput(RecipeChoice input) {
        this.can_input1 = input;
        return this;
    }

    public FurnaceRecipeBuilder setInput(Material input) {
        this.can_input2 = input;
        return this;
    }

    public FurnaceRecipeBuilder setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }

    public FurnaceRecipeBuilder setExperience(float experience) {
        this.experience = experience;
        return this;
    }

    @Override
    public Recipe build() {
        if(can_input1 != null){
            return new FurnaceRecipe(key, output, can_input1, experience, cookingTime);
        }
        return new FurnaceRecipe(key, output, can_input2, experience, cookingTime);
    }
}
