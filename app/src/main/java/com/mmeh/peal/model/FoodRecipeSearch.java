package com.mmeh.peal.model;

public class FoodRecipeSearch {

    public static FoodRecipe searchById(String recipeId) {
        // TODO: code this method - searchById
        FoodRecipe foodRecipe = new FoodRecipe();

        return foodRecipe;
    }

    public static FoodRecipe[] searchByName(String recipeName) {
        // TODO: code this method - searchByName
        FoodRecipe[] foodRecipes = new FoodRecipe[1];

        return foodRecipes;
    }

    public static FoodRecipe[] searchByIngredients(String... ingredients) {
        // TODO: code this method - searchByIngredients
        for (String ingredient : ingredients) {

        }
        FoodRecipe[] foodRecipes = new FoodRecipe[1];
        return foodRecipes;
    }
}
