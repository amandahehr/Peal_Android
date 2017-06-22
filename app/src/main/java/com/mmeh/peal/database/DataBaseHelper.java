package com.mmeh.peal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mmeh.peal.model.FoodItem;
import com.mmeh.peal.model.FoodRecipe;

import static com.mmeh.peal.database.FoodItemContract.*;
import static com.mmeh.peal.database.FoodRecipeContract.*;
import static com.mmeh.peal.database.FoodRecipeItemContract.*;
import static com.mmeh.peal.database.MealDayContract.*;
import static com.mmeh.peal.database.MealDayTypeContract.*;
import static com.mmeh.peal.database.MealDayTypeItemContract.*;
import static com.mmeh.peal.database.MealDayTypeRecipeContract.*;

public class DataBaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Peal.db";

    private static final String SQL_CREATE_FOOD_ITEM =
            "CREATE TABLE " + FoodItemEntry.TABLE_NAME + " (" +
                    FoodItemEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodItemEntry.COLUMN_NAME_NAME + " TEXT," +
                    FoodItemEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FoodItemEntry.COLUMN_NAME_CATEGORY + " TEXT," +
                    FoodItemEntry.COLUMN_NAME_MEASURE + " TEXT," +
                    FoodItemEntry.COLUMN_NAME_NDB + " TEXT);";
    private static final String SQL_CREATE_FOOD_RECIPE =
            "CREATE TABLE " + FoodRecipeEntry.TABLE_NAME + " (" +
                    FoodRecipeEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodRecipeEntry.COLUMN_NAME_NAME + " TEXT," +
                    FoodRecipeEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FoodRecipeEntry.COLUMN_NAME_INSTRUCTIONS + " TEXT); ";
    private static final String SQL_CREATE_FOOD_RECIPE_ITEM =
            "CREATE TABLE " + FoodRecipeItemEntry.TABLE_NAME + " (" +
                    FoodRecipeItemEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodRecipeItemEntry.COLUMN_NAME_FOOD_RECIPE_ID + " INTEGER," +
                    FoodRecipeItemEntry.COLUMN_NAME_FOOD_ITEM_ID + " INTEGER," +
                    FoodRecipeItemEntry.COLUMN_NAME_QUANTITY + " NUMERIC," +
                    "FOREIGN KEY (" + FoodRecipeItemEntry.COLUMN_NAME_FOOD_RECIPE_ID + ") " +
                    "REFERENCES " + FoodRecipeEntry.TABLE_NAME + "(" + FoodRecipeEntry._ID + ")," +
                    "FOREIGN KEY (" + FoodRecipeItemEntry.COLUMN_NAME_FOOD_ITEM_ID + ") " +
                    "REFERENCES " + FoodItemEntry.TABLE_NAME + "(" + FoodItemEntry._ID + ")); ";
    private static final String SQL_CREATE_MEAL_DAY =
            "CREATE TABLE " + MealDayEntry.TABLE_NAME + " (" +
                    MealDayEntry._ID + " INTEGER PRIMARY KEY," +
                    MealDayEntry.COLUMN_NAME_MEAL_DATE + " TEXT); ";
    private static final String SQL_CREATE_MEAL_DAY_TYPE =
            "CREATE TABLE " + MealDayTypeEntry.TABLE_NAME + " (" +
                    MealDayTypeEntry._ID + " INTEGER PRIMARY KEY," +
                    MealDayTypeEntry.COLUMN_NAME_MEAL_DAY_TYPE + " TEXT," +
                    MealDayTypeEntry.COLUMN_NAME_MEAL_DAY_ID + " INTEGER," +
                    "FOREIGN KEY (" + MealDayTypeEntry.COLUMN_NAME_MEAL_DAY_ID + ") " +
                    "REFERENCES " + MealDayEntry.TABLE_NAME + "(" + MealDayEntry._ID + ")); ";
    private static final String SQL_CREATE_MEAL_DAY_TYPE_ITEM =
            "CREATE TABLE " + MealDayTypeItemEntry.TABLE_NAME + " (" +
                    MealDayTypeItemEntry._ID + " INTEGER PRIMARY KEY," +
                    MealDayTypeItemEntry.COLUMN_NAME_MEAL_DAY_TYPE_ID + " INTEGER," +
                    MealDayTypeItemEntry.COLUMN_NAME_FOOD_ITEM_ID + " INTEGER," +
                    MealDayTypeItemEntry.COLUMN_NAME_QUANTITY + " NUMERIC," +
                    "FOREIGN KEY (" + MealDayTypeItemEntry.COLUMN_NAME_MEAL_DAY_TYPE_ID + ") " +
                    "REFERENCES " + MealDayTypeEntry.TABLE_NAME + "(" + MealDayTypeEntry._ID + ")," +
                    "FOREIGN KEY (" + MealDayTypeItemEntry.COLUMN_NAME_FOOD_ITEM_ID + ") " +
                    "REFERENCES " + FoodItemEntry.TABLE_NAME + "(" + FoodItemEntry._ID + ")); ";
    private static final String SQL_CREATE_MEAL_DAY_TYPE_RECIPE =
            "CREATE TABLE " + MealDayTypeRecipeEntry.TABLE_NAME + " (" +
                    MealDayTypeRecipeEntry._ID + " INTEGER PRIMARY KEY," +
                    MealDayTypeRecipeEntry.COLUMN_NAME_MEAL_DAY_TYPE_ID + " INTEGER," +
                    MealDayTypeRecipeEntry.COLUMN_NAME_FOOD_RECIPE_ID + " INTEGER," +
                    MealDayTypeRecipeEntry.COLUMN_NAME_QUANTITY + " NUMERIC," +
                    "FOREIGN KEY (" + MealDayTypeRecipeEntry.COLUMN_NAME_MEAL_DAY_TYPE_ID + ") " +
                    "REFERENCES " + MealDayTypeEntry.TABLE_NAME + "(" + MealDayTypeEntry._ID + ")," +
                    "FOREIGN KEY (" + MealDayTypeRecipeEntry.COLUMN_NAME_FOOD_RECIPE_ID + ") " +
                    "REFERENCES " + FoodRecipeEntry.TABLE_NAME + "(" + FoodRecipeEntry._ID + ")); ";

    private static final String SQL_DELETE_FOOD_ITEM =
            "DROP TABLE IF EXISTS " + FoodItemEntry.TABLE_NAME + "; ";
    private static final String SQL_DELETE_FOOD_RECIPE =
            "DROP TABLE IF EXISTS " + FoodRecipeEntry.TABLE_NAME + "; ";
    private static final String SQL_DELETE_FOOD_RECIPE_ITEM =
            "DROP TABLE IF EXISTS " + FoodRecipeItemEntry.TABLE_NAME + "; ";
    private static final String SQL_DELETE_MEAL_DAY =
            "DROP TABLE IF EXISTS " + MealDayEntry.TABLE_NAME + "; ";
    private static final String SQL_DELETE_MEAL_DAY_TYPE =
            "DROP TABLE IF EXISTS " + MealDayTypeEntry.TABLE_NAME + "; ";
    private static final String SQL_DELETE_MEAL_DAY_TYPE_ITEM =
            "DROP TABLE IF EXISTS " + MealDayTypeItemEntry.TABLE_NAME + "; ";
    private static final String SQL_DELETE_MEAL_DAY_TYPE_RECIPE =
            "DROP TABLE IF EXISTS " + MealDayTypeRecipeEntry.TABLE_NAME + "; ";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FOOD_ITEM);
        db.execSQL(SQL_CREATE_FOOD_RECIPE);
        db.execSQL(SQL_CREATE_FOOD_RECIPE_ITEM);
        db.execSQL(SQL_CREATE_MEAL_DAY);
        db.execSQL(SQL_CREATE_MEAL_DAY_TYPE);
        db.execSQL(SQL_CREATE_MEAL_DAY_TYPE_ITEM);
        db.execSQL(SQL_CREATE_MEAL_DAY_TYPE_RECIPE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: for now onUpgrade is not doing anything. See for a solution
//        db.execSQL(SQL_DELETE_FOOD_RECIPE_ITEM);
//        db.execSQL(SQL_DELETE_FOOD_RECIPE);
//        db.execSQL(SQL_DELETE_MEAL_DAY_TYPE_ITEM);
//        db.execSQL(SQL_DELETE_MEAL_DAY_TYPE_RECIPE);
//        db.execSQL(SQL_DELETE_MEAL_DAY_TYPE);
//        db.execSQL(SQL_DELETE_MEAL_DAY);
//        db.execSQL(SQL_DELETE_FOOD_ITEM);
//        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public long insertNewFoodItem(FoodItem newFoodItem) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FoodItemEntry.COLUMN_NAME_NAME, newFoodItem.getItemName());
        values.put(FoodItemEntry.COLUMN_NAME_CATEGORY, newFoodItem.getItemCategory());
        values.put(FoodItemEntry.COLUMN_NAME_MEASURE, newFoodItem.getItemMeasure());
        values.put(FoodItemEntry.COLUMN_NAME_NDB, newFoodItem.getItemNDB());

        long newFoodItemId = db.insert(FoodItemEntry.TABLE_NAME, null, values);

        return newFoodItemId;
    }

    public long insertNewFoodRecipe(FoodRecipe newFoodRecipe) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FoodRecipeEntry.COLUMN_NAME_NAME, newFoodRecipe.getRecipeName());
        values.put(FoodRecipeEntry.COLUMN_NAME_INSTRUCTIONS, newFoodRecipe.getRecipeInstructions());

        long newRecipeId = db.insert(FoodRecipeEntry.TABLE_NAME, null, values);

        for (int i = 0; i < newFoodRecipe.getFoodItems().size(); i++) {
            String currentNDB = newFoodRecipe.getFoodItems().get(i).getItemNDB();
            float quantity = newFoodRecipe.getFoodItems().get(i).getItemQuantity();

            long foodItemId = getFoodItemIdByNDB(currentNDB);
            if (foodItemId == 0) { // not found, create new
                foodItemId = insertNewFoodItem(newFoodRecipe.getFoodItems().get(i));
            }

            insertNewFoodRecipeItem(newRecipeId, foodItemId, quantity);
        }

        return newRecipeId;
    }
    
    public long insertNewFoodRecipeItem(long foodRecipeId, long foodItemId, float quantity) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FoodRecipeItemEntry.COLUMN_NAME_FOOD_RECIPE_ID, foodRecipeId);
        values.put(FoodRecipeItemEntry.COLUMN_NAME_FOOD_ITEM_ID, foodItemId);
        values.put(FoodRecipeItemEntry.COLUMN_NAME_QUANTITY, quantity);

        long newRecipeItemId = db.insert(FoodRecipeItemEntry.TABLE_NAME, null, values);

        return newRecipeItemId;
    }

    public long getFoodItemIdByNDB(String ndb) {
        SQLiteDatabase db = getReadableDatabase();
        long itemId = 0;

        String[] projection = {
                FoodItemEntry._ID,
        };

        String selection = FoodItemEntry.COLUMN_NAME_NDB + " = ?";
        String[] selectionArgs = { ndb };
        
        String sortOrder = FoodItemEntry._ID + " ASC";

        Cursor cursor = db.query(
                FoodItemEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FoodItemEntry._ID));
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow(FoodItemEntry.COLUMN_NAME_NAME));
        }
        cursor.close();

        return itemId;
    }



}
