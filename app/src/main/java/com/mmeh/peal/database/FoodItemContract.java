package com.mmeh.peal.database;

import android.provider.BaseColumns;

public final class FoodItemContract {

    private FoodItemContract() {}

    public static class FoodItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "food_item";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_MEASURE = "measure";
        public static final String COLUMN_NAME_NDB = "ndb";
    }

}
