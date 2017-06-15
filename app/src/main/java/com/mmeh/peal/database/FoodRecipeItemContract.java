package com.mmeh.peal.database;

import android.provider.BaseColumns;

public class FoodRecipeItemContract {

    private FoodRecipeItemContract() {}

    public static class FoodRecipeItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "food_recipe_item";
        public static final String COLUMN_NAME_FOOD_RECIPE_ID = "food_recipe_id";
        public static final String COLUMN_NAME_FOOD_ITEM_ID = "food_item_id";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
    }

}
