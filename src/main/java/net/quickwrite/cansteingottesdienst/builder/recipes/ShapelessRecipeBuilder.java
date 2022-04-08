package net.quickwrite.cansteingottesdienst.builder.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class ShapelessRecipeBuilder extends RecipeBuilder {

    private final ShapelessRecipe recipe;

    public ShapelessRecipeBuilder(JavaPlugin main, String name, ItemStack output) {
        super(main, name, output);
        recipe = new ShapelessRecipe(key, output);
    }

    public ShapelessRecipeBuilder addShapelessIngredient(Material m){
        recipe.addIngredient(m);
        return this;
    }

    public ShapelessRecipeBuilder addShapelessIngredient(Material m, int count){
        recipe.addIngredient(count, m);
        return this;
    }

    public ShapelessRecipeBuilder addShapelessIngredient(RecipeChoice rc){
        recipe.addIngredient(rc);
        return this;
    }

    public ShapelessRecipeBuilder setGroup(String group){
        recipe.setGroup(group);
        return this;
    }

    @Override
    public Recipe build() {
        return recipe;
    }
}
