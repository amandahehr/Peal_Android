package com.mmeh.peal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmeh.peal.database.DataBaseHelper;
import com.mmeh.peal.database.FoodRecipeContract;
import com.mmeh.peal.model.FoodRecipe;
import com.mmeh.peal.list_adapters.FoodRecipeListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipeBook extends AppCompatActivity {

    private static final int NEW_FOOD_RECIPE_REQUEST = 100;
    private static final int EDIT_FOOD_RECIPE_REQUEST = 200;
    public static final String FOOD_RECIPE_ID = "FOOD_RECIPE_ID";

    DataBaseHelper myDbHelper;
    List<FoodRecipe> foodRecipes;
    Map<FoodRecipe, Integer> recipesId;
    FoodRecipeListAdapter adapter;

    ListView recipesListView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent();
            int itemId = item.getItemId();
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    intent = new Intent(findViewById(itemId).getContext(), WeekCalendar.class);
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
        recipesId = new HashMap<>();
        adapter = new FoodRecipeListAdapter(
                RecipeBook.this, R.layout.list_food_recipe, foodRecipes
        );
        recipesListView.setAdapter(adapter);

        loadAllRecipes();
        adapter.notifyDataSetChanged();

        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), AddFoodRecipe.class);
                intent.putExtra(FOOD_RECIPE_ID, recipesId.get(foodRecipes.get(position)));
                startActivityForResult(intent, NEW_FOOD_RECIPE_REQUEST);
            }
        });

    }

    @Override
    protected void onDestroy() {
        myDbHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case NEW_FOOD_RECIPE_REQUEST:
                loadAllRecipes();
                adapter.notifyDataSetChanged();
                break;
            case EDIT_FOOD_RECIPE_REQUEST:
                if (resultCode == RESULT_OK) {
                    loadAllRecipes();
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
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
        recipesId.clear();

        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(FoodRecipeContract.FoodRecipeEntry._ID));
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow(FoodRecipeContract.FoodRecipeEntry.COLUMN_NAME_NAME));

            FoodRecipe fr = new FoodRecipe(itemName, null, "");
            foodRecipes.add(fr);
            recipesId.put(fr, itemId);
        }
        cursor.close();
    }

    public void addRecipeButtonClickEventHandler(View view) {
        Intent intent = new Intent(this, AddFoodRecipe.class);
        intent.putExtra(FOOD_RECIPE_ID, 0);
        startActivityForResult(intent, NEW_FOOD_RECIPE_REQUEST);
    }
}
