package com.mmeh.peal.database;

import android.provider.BaseColumns;

public final class FoodRecipeContract {

    private FoodRecipeContract() {}

    public static class FoodRecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "food_recipe";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_INSTRUCTIONS = "instructions";
        public static final String COLUMN_NAME_SERVING_SIZE = "serving_size";
    }

}
