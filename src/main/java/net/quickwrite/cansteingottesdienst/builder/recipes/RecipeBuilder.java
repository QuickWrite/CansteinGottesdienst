package net.quickwrite.cansteingottesdienst.builder.recipes;

import org.bukkit.Material;
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



    public static class ShapelessRecipeBuilder extends RecipeBuilder{

        private ShapelessRecipe recipe;

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

    public static class ShapedRecipeBuilder extends RecipeBuilder{

        private ShapedRecipe recipe;

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
            return null;
        }
    }

    public static class BlastingRecipeBuilder extends RecipeBuilder{

        protected RecipeChoice can_input1;
        protected Material can_input2;
        protected float experience;
        protected int cookingTime;

        public BlastingRecipeBuilder(JavaPlugin main, String name, ItemStack output) {
            super(main, name, output);
            experience = 1;
            cookingTime = 100;
        }

        public BlastingRecipeBuilder setInput(RecipeChoice input) {
            this.can_input1 = input;
            return this;
        }
        public BlastingRecipeBuilder setInput(Material input) {
            this.can_input2 = input;
            return this;
        }

        public BlastingRecipeBuilder setCookingTime(int cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }

        public BlastingRecipeBuilder setExperience(float experience) {
            this.experience = experience;
            return this;
        }

        @Override
        public Recipe build() {
            if (can_input1 != null){
                return new BlastingRecipe(key, output, can_input1, experience, cookingTime);
            }
            return new BlastingRecipe(key, output, can_input2, experience, cookingTime);
        }
    }

    public static class CampfireRecipeBuilder extends BlastingRecipeBuilder{

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

    public static class FurnaceRecipeBuilder extends BlastingRecipeBuilder{

        public FurnaceRecipeBuilder(JavaPlugin main, String name, ItemStack output) {
            super(main, name, output);
            cookingTime = 200;
            experience = 1;
        }

        @Override
        public Recipe build() {
            if(can_input1 != null){
                return new FurnaceRecipe(key, output, can_input1, experience, cookingTime);
            }
            return new FurnaceRecipe(key, output, can_input2, experience, cookingTime);
        }
    }

    public static class SmithingRecipeBuilder extends RecipeBuilder{

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

    public static class SmokingRecipeBuilder extends BlastingRecipeBuilder{

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

    public static class StonecuttingRecipeBuilder extends RecipeBuilder{

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
}
