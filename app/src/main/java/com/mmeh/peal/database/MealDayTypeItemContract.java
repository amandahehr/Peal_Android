package com.mmeh.peal.database;

import android.provider.BaseColumns;

public class MealDayTypeItemContract {

    private MealDayTypeItemContract() {}

    public static class MealDayTypeItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "meal_day_type_item";
        public static final String COLUMN_NAME_MEAL_DAY_TYPE_ID = "meal_day_type_id";
        public static final String COLUMN_NAME_FOOD_ITEM_ID = "food_item_id";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
    }

}
