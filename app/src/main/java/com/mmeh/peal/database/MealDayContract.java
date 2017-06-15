package com.mmeh.peal.database;

import android.provider.BaseColumns;

public class MealDayContract {

    private MealDayContract() {}

    public static class MealDayEntry implements BaseColumns {
        public static final String TABLE_NAME = "meal_day";
        public static final String COLUMN_NAME_MEAL_DATE = "meal_date";
    }

}
