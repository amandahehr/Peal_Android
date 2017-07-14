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
import android.widget.EditText;
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

    // requests
    private static final int NEW_FOOD_RECIPE_REQUEST = 100;
    private static final int EDIT_FOOD_RECIPE_REQUEST = 200;
    private static final int SINGLE_ITEM_REQUEST = 300;

    // return

    // in arguments
    public static final String IN_FROM_WHAT_SCREEN = "IN_FROM_WHAT_SCREEN";

    // screens that call this activity
    public static final int SCREEN_MEAL_VIEW = 1;

    DataBaseHelper myDbHelper;
    List<FoodRecipe> foodRecipes;
    Map<FoodRecipe, Integer> recipesId;
    FoodRecipeListAdapter adapter;
    int fromWhatScreen;
    int chosenItem;

    ListView recipesListView;
    EditText searchRecipeEditText;


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
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
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        recipesListView = (ListView) findViewById(R.id.recipes_list_view);
        searchRecipeEditText = (EditText) findViewById(R.id.search_recipe_edit_text);

        Bundle bundle = getIntent().getExtras();
        try {
            fromWhatScreen = bundle.getInt(IN_FROM_WHAT_SCREEN);
        } catch (Exception e) {
            fromWhatScreen = 0;
        }
        myDbHelper = new DataBaseHelper(this);
        foodRecipes = new ArrayList<>();
        recipesId = new HashMap<>();
        adapter = new FoodRecipeListAdapter(
                RecipeBook.this, R.layout.list_food_recipe, foodRecipes
        );
        recipesListView.setAdapter(adapter);
        chosenItem = 0;

        loadAllRecipes();
        adapter.notifyDataSetChanged();

        recipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // if you came from the MealView then you want to add a recipe, not just looking/creating one
                if (fromWhatScreen == SCREEN_MEAL_VIEW) {
                    Intent intent = new Intent(view.getContext(), SingleItem.class);
                    intent.putExtra(SingleItem.FROM_WHAT_SCREEN, SingleItem.SCREEN_RECIPE_BOOK);
                    intent.putExtra(SingleItem.ITEM_NAME, foodRecipes.get(position).getRecipeName());
                    intent.putExtra(SingleItem.ITEM_MEASURE, foodRecipes.get(position).getRecipeServingSize());
                    intent.putExtra(SingleItem.ITEM_QUANTITY, foodRecipes.get(position).getRecipeQuantity());
                    startActivityForResult(intent, SINGLE_ITEM_REQUEST);

                } else {
                    Intent intent = new Intent(view.getContext(), AddFoodRecipe.class);
                    intent.putExtra(AddFoodRecipe.FOOD_RECIPE_ID, recipesId.get(foodRecipes.get(position)));
                    chosenItem = position;
                    startActivityForResult(intent, NEW_FOOD_RECIPE_REQUEST);
                }
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
            case SINGLE_ITEM_REQUEST:
                returnToMealView();
                break;
            default:
                break;
        }
    }

    private void returnToMealView() {
        Intent data = new Intent();
//        data.putExtra(RETURN_RECIPE_ID, foodItems.get(index).getItemName());
//        data.putExtra(RETURN_RECIPE_NAME, foodItems.get(index).getItemName());
//        data.putExtra(RETURN_RECIPE_INSTRUCTIONS, foodItems.get(index).getItemCategory());
        setResult(RESULT_OK, data);
        finish();
    }

    // TODO: UPGRADE: create this logic inside DataBaseHelper and make this method call it
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
        intent.putExtra(AddFoodRecipe.FOOD_RECIPE_ID, 0);
        startActivityForResult(intent, NEW_FOOD_RECIPE_REQUEST);
    }

    public void searchRecipeButtonClickEventHandler(View view) {
        if (searchRecipeEditText.getText().toString().trim().equals("")) {
            loadAllRecipes();
            adapter.notifyDataSetChanged();
        }

        String s = searchRecipeEditText.getText().toString();
        foodRecipes.clear();
        foodRecipes.addAll(myDbHelper.getRecipeByName(s));
        adapter.notifyDataSetChanged();
    }
}
