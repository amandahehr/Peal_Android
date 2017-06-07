package com.mmeh.peal.model;

import java.util.ArrayList;

public class Meal {

    private String mealType;
    private ArrayList<FoodRecipe> foodRecipes;
    private ArrayList<FoodItem> foodItems;
    // TODO: check if there is any other attributes for this class. !ATTENTION! Whenever creating new attributes the constructors and the toString() method must be updated/analysed.

    public Meal() {
        // TODO: complete this constructor.

    }

    public Meal(String mealType, ArrayList<FoodRecipe> foodRecipes, ArrayList<FoodItem> foodItems) {
        this.mealType = mealType;
        this.foodRecipes = foodRecipes;
        this.foodItems = foodItems;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public ArrayList<FoodRecipe> getFoodRecipes() {
        return foodRecipes;
    }

    public void setFoodRecipes(ArrayList<FoodRecipe> foodRecipes) {
        this.foodRecipes = foodRecipes;
    }

    public ArrayList<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(ArrayList<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealType='" + mealType + '\'' +
                ", foodRecipes=" + foodRecipes +
                ", foodItems=" + foodItems +
                '}';
    }

    public void addFoodRecipe(FoodRecipe newRecipe) {
        // TODO: code this method - addFoodRecipe
    }

    public void addFoodItem(FoodItem newItem) {
        // TODO: code this method - addFoodItem
    }

    public void removeFoodRecipe(String recipeId) {
        // TODO: code this method - removeFoodRecipe
    }

    public void removeFoodItem(String itemId) {
        // TODO: code this method - removeFoodItem
    }
}
