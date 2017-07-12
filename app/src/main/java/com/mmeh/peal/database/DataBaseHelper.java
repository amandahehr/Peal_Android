package com.mmeh.peal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mmeh.peal.model.FoodItem;
import com.mmeh.peal.model.FoodRecipe;

import java.util.ArrayList;
import java.util.List;

import static com.mmeh.peal.database.FoodItemContract.*;
import static com.mmeh.peal.database.FoodRecipeContract.*;
import static com.mmeh.peal.database.FoodRecipeItemContract.*;
import static com.mmeh.peal.database.MealDayContract.*;
import static com.mmeh.peal.database.MealDayTypeContract.*;
import static com.mmeh.peal.database.MealDayTypeItemContract.*;
import static com.mmeh.peal.database.MealDayTypeRecipeContract.*;

public class DataBaseHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Peal.db";

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
                    FoodRecipeEntry.COLUMN_NAME_INSTRUCTIONS + " TEXT," +
                    FoodRecipeEntry.COLUMN_NAME_SERVING_SIZE + " NUMERIC); ";
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

        return db.insert(FoodItemEntry.TABLE_NAME, null, values);
    }

    public long insertNewFoodRecipe(FoodRecipe newFoodRecipe) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FoodRecipeEntry.COLUMN_NAME_NAME, newFoodRecipe.getRecipeName());
        values.put(FoodRecipeEntry.COLUMN_NAME_INSTRUCTIONS, newFoodRecipe.getRecipeInstructions());
        values.put(FoodRecipeEntry.COLUMN_NAME_SERVING_SIZE, newFoodRecipe.getRecipeServingSize());

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

        return db.insert(FoodRecipeItemEntry.TABLE_NAME, null, values);
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
        }
        cursor.close();

        return itemId;
    }


    public FoodRecipe getFoodRecipeById(int recipeId) {
        FoodRecipe fr = new FoodRecipe();
        // recipes table
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                FoodRecipeEntry.COLUMN_NAME_NAME,
                FoodRecipeEntry.COLUMN_NAME_INSTRUCTIONS,
                FoodRecipeEntry.COLUMN_NAME_SERVING_SIZE
        };
        String selection = FoodRecipeEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(recipeId)};

        Cursor cursor = db.query(
                FoodRecipeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            fr.setRecipeName(cursor.getString(cursor.getColumnIndexOrThrow(FoodRecipeEntry.COLUMN_NAME_NAME)));
            fr.setRecipeInstructions(cursor.getString(cursor.getColumnIndexOrThrow(FoodRecipeEntry.COLUMN_NAME_INSTRUCTIONS)));
            fr.setRecipeServingSize(cursor.getFloat(cursor.getColumnIndexOrThrow(FoodRecipeEntry.COLUMN_NAME_SERVING_SIZE)));
        }
        cursor.close();

        // recipes items table
        fr.setFoodItems(getFoodRecipeItemsById(recipeId));

        return fr;
    }

    public ArrayList<FoodItem> getFoodRecipeItemsById(int recipeId) {
        ArrayList<FoodItem> foodItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                FoodRecipeItemEntry.COLUMN_NAME_FOOD_ITEM_ID,
                FoodRecipeItemEntry.COLUMN_NAME_QUANTITY
        };
        String selection = FoodRecipeItemEntry.COLUMN_NAME_FOOD_RECIPE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(recipeId)};
        String sortOrder = FoodRecipeItemEntry.COLUMN_NAME_FOOD_ITEM_ID + " ASC";

        Cursor cursor = db.query(
                FoodRecipeItemEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            FoodItem fi = getFoodItemById(cursor.getInt(cursor.getColumnIndexOrThrow(FoodRecipeItemEntry.COLUMN_NAME_FOOD_ITEM_ID)));
            fi.setItemQuantity(cursor.getFloat(cursor.getColumnIndexOrThrow(FoodRecipeItemEntry.COLUMN_NAME_QUANTITY)));
            foodItems.add(fi);
        }
        cursor.close();

        return foodItems;
    }

    public FoodItem getFoodItemById(int itemId) {
        FoodItem foodItem = new FoodItem();

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                FoodItemEntry.COLUMN_NAME_NAME,
                FoodItemEntry.COLUMN_NAME_CATEGORY,
                FoodItemEntry.COLUMN_NAME_NDB,
                FoodItemEntry.COLUMN_NAME_MEASURE
        };

        String selection = FoodItemEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};

        Cursor cursor = db.query(
                FoodItemEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            foodItem.setItemName(cursor.getString(cursor.getColumnIndexOrThrow(FoodItemEntry.COLUMN_NAME_NAME)));
            foodItem.setItemCategory(cursor.getString(cursor.getColumnIndexOrThrow(FoodItemEntry.COLUMN_NAME_CATEGORY)));
            foodItem.setItemNDB(cursor.getString(cursor.getColumnIndexOrThrow(FoodItemEntry.COLUMN_NAME_NDB)));
            foodItem.setItemMeasure(cursor.getString(cursor.getColumnIndexOrThrow(FoodItemEntry.COLUMN_NAME_MEASURE)));
        }
        cursor.close();

        return foodItem;
    }

    public ArrayList<FoodRecipe> getRecipeByName(String recipeName) {
        ArrayList<FoodRecipe> foodRecipes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                FoodRecipeEntry.COLUMN_NAME_NAME
        };
        String selection = FoodRecipeEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = {"%" + recipeName + "%"};

        Cursor cursor = db.query(
                FoodRecipeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            FoodRecipe fr = new FoodRecipe();
            fr.setRecipeName(cursor.getString(cursor.getColumnIndexOrThrow(FoodRecipeEntry.COLUMN_NAME_NAME)));
            foodRecipes.add(fr);
        }
        cursor.close();

        return foodRecipes;
    }
}
