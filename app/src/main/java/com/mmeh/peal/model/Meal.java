package com.mmeh.peal.model;

import java.util.ArrayList;

public class Meal{

    private String mealType;
    private ArrayList<FoodRecipe> foodRecipes;
    private ArrayList<FoodItem> foodItems;

    public Meal() {
        this.mealType = "";
        foodRecipes = new ArrayList<>();
        foodItems = new ArrayList<>();
    }

    public Meal(String mealType) {
        this.mealType = mealType;
        foodRecipes = new ArrayList<>();
        foodItems = new ArrayList<>();
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
        getFoodRecipes().add(newRecipe);
    }

    public void addFoodItem(FoodItem newItem) {
        getFoodItems().add(newItem);
    }

}
