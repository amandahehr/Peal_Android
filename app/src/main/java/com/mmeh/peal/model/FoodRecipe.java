package com.mmeh.peal.model;

import java.util.ArrayList;

public class FoodRecipe {

    private int recipeId;
    private String recipeName;
    private String recipeDescription;
    private ArrayList<FoodItem> foodItems;
    private String recipeInstructions;
    // TODO: check if there is any other attributes for this class. !ATTENTION! Whenever creating new attributes the constructors and the toString() method must be updated/analysed.


    public FoodRecipe() {
        // TODO: complete this constructor.

    }

    public FoodRecipe(int recipeId, String recipeName, String recipeDescription, ArrayList<FoodItem> foodItems, String recipeInstructions) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.foodItems = foodItems;
        this.recipeInstructions = recipeInstructions;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public ArrayList<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(ArrayList<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    @Override
    public String toString() {
        return "FoodRecipe{" +
                "recipeId='" + recipeId + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", recipeDescription='" + recipeDescription + '\'' +
                ", foodItems=" + foodItems +
                ", recipeInstructions='" + recipeInstructions + '\'' +
                '}';
    }

    public void addFoodItemById(String foodItemId) {
        // TODO: code this method - addFoodItemById
    }

    public void removeFoodItemById(String foodItemId) {
        // TODO: code this method - removeFoodItemById
    }

    public void saveRecipe() {
        // TODO: code this method - saveRecipe
    }


}
