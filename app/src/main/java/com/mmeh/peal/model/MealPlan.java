package com.mmeh.peal.model;

import java.util.ArrayList;

public class MealPlan {

    public final String BREAKFAST = "BREAKFAST";
    public final String LUNCH = "LUNCH";
    public final String DINNER = "DINNER";

    private String dayMealPlanId;
    private String dayMealPlanDate;
    private Meal breakfastMeal;
    private Meal lunchMeal;
    private Meal dinnerMeal;
    // TODO: check if there is any other attributes for this class. !ATTENTION! Whenever creating new attributes the constructors and the toString() method must be updated/analysed.


    public MealPlan() {
        // TODO: complete this constructor
    }

    public MealPlan(String dayMealPlanId, String dayMealPlanDate, Meal breakfastMeal, Meal lunchMeal, Meal dinnerMeal) {
        this.dayMealPlanId = dayMealPlanId;
        this.dayMealPlanDate = dayMealPlanDate;
        this.breakfastMeal = breakfastMeal;
        this.lunchMeal = lunchMeal;
        this.dinnerMeal = dinnerMeal;
    }

    public String getDayMealPlanId() {
        return dayMealPlanId;
    }

    public void setDayMealPlanId(String dayMealPlanId) {
        this.dayMealPlanId = dayMealPlanId;
    }

    public String getDayMealPlanDate() {
        return dayMealPlanDate;
    }

    public void setDayMealPlanDate(String dayMealPlanDate) {
        this.dayMealPlanDate = dayMealPlanDate;
    }

    public Meal getBreakfastMeal() {
        return breakfastMeal;
    }

    public void setBreakfastMeal(Meal breakfastMeal) {
        this.breakfastMeal = breakfastMeal;
    }

    public Meal getLunchMeal() {
        return lunchMeal;
    }

    public void setLunchMeal(Meal lunchMeal) {
        this.lunchMeal = lunchMeal;
    }

    public Meal getDinnerMeal() {
        return dinnerMeal;
    }

    public void setDinnerMeal(Meal dinnerMeal) {
        this.dinnerMeal = dinnerMeal;
    }

    public void saveMealPlan() {
        // TODO: code this method - saveMealPlan
    }
}
