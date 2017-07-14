package com.mmeh.peal.model;

import java.util.ArrayList;

public class FoodRecipe {

    private String recipeName;
    private ArrayList<FoodItem> foodItems;
    private String recipeInstructions;
    private float recipeServingSize;
    private float recipeQuantity;


    public FoodRecipe() {
        recipeName = "";
        recipeInstructions = "";
        recipeServingSize = (float) 1;
        this.foodItems = new ArrayList<>();
    }

    public FoodRecipe(String recipeName, ArrayList<FoodItem> foodItems, String recipeInstructions) {
        this.recipeName = recipeName;
        this.foodItems = foodItems;
        this.recipeInstructions = recipeInstructions;
        this.recipeServingSize = (float) 1;
    }

    public FoodRecipe(String recipeName, String recipeInstructions, float recipeServingSize) {
        this.recipeName = recipeName;
        this.foodItems = new ArrayList<>();
        this.recipeInstructions = recipeInstructions;
        this.recipeServingSize = recipeServingSize;
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

    public float getRecipeServingSize() {
        return recipeServingSize;
    }

    public void setRecipeServingSize(float recipeServingSize) {
        this.recipeServingSize = recipeServingSize;
    }

    public void addFoodItem(FoodItem newFoodItem) {
        getFoodItems().add(newFoodItem);
    }

    public boolean hasFoodItem(FoodItem foodItem) {
        return (getFoodItems().indexOf(foodItem) == -1 ? false : true);
    }

    public float getRecipeQuantity() {
        return recipeQuantity;
    }

    public void setRecipeQuantity(float recipeQuantity) {
        this.recipeQuantity = recipeQuantity;
    }

    @Override
    public String toString() {
        return "FoodRecipe{" +
                "recipeName='" + recipeName + '\'' +
                ", foodItems=" + foodItems +
                ", recipeInstructions='" + recipeInstructions + '\'' +
                ", recipeServingSize=" + recipeServingSize +
                ", recipeQuantity=" + recipeQuantity +
                '}';
    }

}
