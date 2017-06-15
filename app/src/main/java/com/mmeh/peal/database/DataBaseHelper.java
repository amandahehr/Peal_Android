package com.mmeh.peal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.mmeh.peal.database.FoodItemContract.*;
import static com.mmeh.peal.database.FoodRecipeContract.*;
import static com.mmeh.peal.database.FoodRecipeItemContract.*;
import static com.mmeh.peal.database.MealDayContract.*;
import static com.mmeh.peal.database.MealDayTypeContract.*;

public class DataBaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Peal.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FoodItemEntry.TABLE_NAME + " (" +
                    FoodItemEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodItemEntry.COLUMN_NAME_NAME + " TEXT," +
                    FoodItemEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FoodItemEntry.COLUMN_NAME_CATEGORY + " TEXT," +
                    FoodItemEntry.COLUMN_NAME_MEASURE + " TEXT," +
                    FoodItemEntry.COLUMN_NAME_NDB + " TEXT);" +
            "CREATE TABLE " + FoodRecipeEntry.TABLE_NAME + " (" +
                    FoodRecipeEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodRecipeEntry.COLUMN_NAME_NAME + " TEXT," +
                    FoodRecipeEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FoodRecipeEntry.COLUMN_NAME_INSTRUCTIONS + " TEXT); " +
            "CREATE TABLE " + FoodRecipeItemEntry.TABLE_NAME + " (" +
                    FoodRecipeItemEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodRecipeItemEntry.COLUMN_NAME_FOOD_RECIPE_ID + " INTEGER," +
                    FoodRecipeItemEntry.COLUMN_NAME_FOOD_ITEM_ID + " INTEGER," +
                    FoodRecipeItemEntry.COLUMN_NAME_QUANTITY + "NUMERIC," +
                    "FOREIGN KEY (" + FoodRecipeItemEntry.COLUMN_NAME_FOOD_RECIPE_ID + ") " +
                    "REFERENCES " + FoodRecipeEntry.TABLE_NAME + "(" + FoodRecipeEntry._ID + ")," +
                    "FOREIGN KEY (" + FoodRecipeItemEntry.COLUMN_NAME_FOOD_ITEM_ID + ") " +
                    "REFERENCES " + FoodItemEntry.TABLE_NAME + "(" + FoodItemEntry._ID + ")); " +
            "CREATE TABLE " + MealDayEntry.TABLE_NAME + " (" +
                    MealDayEntry._ID + " INTEGER PRIMARY KEY," +
                    MealDayEntry.COLUMN_NAME_MEAL_DATE + " TEXT); " +
            "CREATE TABLE " + MealDayTypeEntry.TABLE_NAME + " (" +
                    MealDayTypeEntry._ID + " INTEGER PRIMARY KEY," +
                    MealDayTypeEntry.COLUMN_NAME_MEAL_DAY_TYPE + " TEXT," +
                    MealDayTypeEntry.COLUMN_NAME_MEAL_DAY_ID + "INTEGER," +
                    "FOREIGN KEY (" + MealDayTypeEntry.COLUMN_NAME_MEAL_DAY_ID + ") " +
                    "REFERENCES " + MealDayEntry.TABLE_NAME + "(" + MealDayEntry._ID + ")); " +
            ""
            ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FoodItemEntry.TABLE_NAME + "; " +
            "DROP TABLE IF EXISTS " + FoodRecipeEntry.TABLE_NAME + "; " +
            "DROP TABLE IF EXISTS " + FoodRecipeItemEntry.TABLE_NAME + "; " +
            "DROP TABLE IF EXISTS " + MealDayEntry.TABLE_NAME + "; " +
            "DROP TABLE IF EXISTS " + MealDayTypeEntry.TABLE_NAME + "; " +
            ""
            ;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
//        db.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
