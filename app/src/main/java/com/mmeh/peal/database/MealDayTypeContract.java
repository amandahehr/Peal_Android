package com.mmeh.peal.database;

import android.provider.BaseColumns;

public class MealDayTypeContract {

    private MealDayTypeContract() {}

    public static class MealDayTypeEntry implements BaseColumns {
        public static final String TABLE_NAME = "meal_day_type";
        public static final String COLUMN_NAME_MEAL_DAY_TYPE = "meal_day_type";
        public static final String COLUMN_NAME_MEAL_DAY_ID = "meal_day_id";
    }

}
