package net.quickwrite.cansteingottesdienst.builder.recipes;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class SmithingRecipeBuilder extends RecipeBuilder {
    private RecipeChoice base, addition;

    public SmithingRecipeBuilder(JavaPlugin main, String name, ItemStack output) {
        super(main, name, output);
    }

    public SmithingRecipeBuilder setBase(RecipeChoice base) {
        this.base = base;
        return this;
    }

    public SmithingRecipeBuilder setAddition(RecipeChoice addition) {
        this.addition = addition;
        return this;
    }

    @Override
    public Recipe build() {
        return new SmithingRecipe(key, output, base, addition);
    }
}
