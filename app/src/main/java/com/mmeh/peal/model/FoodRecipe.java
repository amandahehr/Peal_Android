package com.mmeh.peal.model;

import java.util.ArrayList;

public class FoodRecipe {

    private String recipeName;
    private ArrayList<FoodItem> foodItems;
    private String recipeInstructions;
    // TODO: check if there is any other attributes for this class. !ATTENTION! Whenever creating new attributes the constructors and the toString() method must be updated/analysed.


    public FoodRecipe() {
        recipeName = "";
        recipeInstructions = "";
    }

    public FoodRecipe(String recipeName, ArrayList<FoodItem> foodItems, String recipeInstructions) {
        this.recipeName = recipeName;
        this.foodItems = foodItems;
        this.recipeInstructions = recipeInstructions;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public ArrayList<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(ArrayList foodItems) {
        this.foodItems = foodItems;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public void addFoodItem(FoodItem newFoodItem) {
        getFoodItems().add(newFoodItem);
    }

    public boolean hasFoodItem(FoodItem foodItem) {
        return (getFoodItems().indexOf(foodItem) == -1 ? false : true);
    }

    @Override
    public String toString() {
        return "FoodRecipe{" +
                "recipeName='" + recipeName + '\'' +
                ", foodItems=" + foodItems +
                ", recipeInstructions='" + recipeInstructions + '\'' +
                '}';
    }

}
