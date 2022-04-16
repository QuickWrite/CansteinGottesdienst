package net.quickwrite.cansteingottesdienst.builder.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class ShapedRecipeBuilder extends RecipeBuilder {

    private final ShapedRecipe recipe;

    public ShapedRecipeBuilder(JavaPlugin main, String name, ItemStack output) {
        super(main, name, output);
        recipe = new ShapedRecipe(key, output);
    }

    public ShapedRecipeBuilder setGroup(String group){
        recipe.setGroup(group);
        return this;
    }

    public ShapedRecipeBuilder setShape(String... shape){
        if(shape.length > 3) return this;
        recipe.shape(shape);
        return this;
    }

    public ShapedRecipeBuilder addShapedIngredient(char key, Material ing){
        recipe.setIngredient(key, ing);
        return this;
    }

    public ShapedRecipeBuilder addShapedIngredient(char key, RecipeChoice ing){
        recipe.setIngredient(key, ing);
        return this;
    }

    @Override
    public Recipe build() {
        return recipe;
    }
}