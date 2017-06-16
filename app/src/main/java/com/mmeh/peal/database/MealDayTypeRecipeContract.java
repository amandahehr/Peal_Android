package com.mmeh.peal.database;

import android.provider.BaseColumns;

public class MealDayTypeRecipeContract {

    private MealDayTypeRecipeContract() {}

    public static class MealDayTypeRecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "meal_day_type_recipe";
        public static final String COLUMN_NAME_MEAL_DAY_TYPE_ID = "meal_day_type_id";
        public static final String COLUMN_NAME_FOOD_RECIPE_ID = "food_recipe_id";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
    }

}
