package com.mmeh.peal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mmeh.peal.database.DataBaseHelper;
import com.mmeh.peal.database.FoodItemContract;
import com.mmeh.peal.database.FoodRecipeContract;
import com.mmeh.peal.model.FoodRecipe;
import com.mmeh.peal.model.FoodRecipeListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipeBook extends AppCompatActivity {

    DataBaseHelper myDbHelper;
    List<FoodRecipe> foodRecipes;

    ListView recipesListView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent();
            int itemId = item.getItemId();
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    intent = new Intent(findViewById(itemId).getContext(), MonthCalendar.class);
                    break;
                case R.id.navigation_recipes:
                    intent = new Intent(findViewById(itemId).getContext(), RecipeBook.class);
                    break;
                case R.id.navigation_shoppingList:
                    intent = new Intent(findViewById(itemId).getContext(), ShoppingList.class);
                    break;
            }
            startActivity(intent);
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_book);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recipesListView = (ListView) findViewById(R.id.recipes_list_view);

        myDbHelper = new DataBaseHelper(this);
        foodRecipes = new ArrayList<>();
        loadAllRecipes();
        showRecipes();

    }

    @Override
    protected void onDestroy() {
        myDbHelper.close();
        super.onDestroy();
    }

    private void loadAllRecipes() {
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] projection = {
                FoodRecipeContract.FoodRecipeEntry._ID,
                FoodRecipeContract.FoodRecipeEntry.COLUMN_NAME_NAME,
        };

        String sortOrder = FoodRecipeContract.FoodRecipeEntry.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = db.query(
                FoodRecipeContract.FoodRecipeEntry.TABLE_NAME,
                projection,
                null, //selection,
                null, //selectionArgs,
                null,
                null,
                sortOrder
        );

        foodRecipes.clear();

        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(FoodRecipeContract.FoodRecipeEntry._ID));
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow(FoodRecipeContract.FoodRecipeEntry.COLUMN_NAME_NAME));
            foodRecipes.add(new FoodRecipe(itemId, itemName, "", null, ""));
        }
        cursor.close();
    }

    private void showRecipes() {
        FoodRecipeListAdapter adapter = new FoodRecipeListAdapter(
                RecipeBook.this, R.layout.list_food_recipe, foodRecipes
        );

        recipesListView.setAdapter(adapter);

    }
}
