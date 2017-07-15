package com.mmeh.peal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmeh.peal.database.DataBaseHelper;
import com.mmeh.peal.list_adapters.FoodItemListAdapter;
import com.mmeh.peal.list_adapters.FoodItemOnRecipeListAdapter;
import com.mmeh.peal.list_adapters.FoodRecipeOnMealListAdapter;
import com.mmeh.peal.model.FoodItem;
import com.mmeh.peal.model.FoodRecipe;
import com.mmeh.peal.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealView extends AppCompatActivity {

    // in arguments
    public static final String IN_MEAL_DAY_TYPE_ID = "IN_MEAL_DAY_TYPE_ID";

    // requests
    private static final int FOOD_ITEM_REQUEST = 100;
    private static final int FOOD_RECIPE_REQUEST = 200;

    // out arguments (return)


    // variables
    private DataBaseHelper myDbHelper;
    private Meal myMeal;
    private FoodItemOnRecipeListAdapter foodItemAdapter;
    private List<FoodItem> foodItems;
    private FoodRecipeOnMealListAdapter foodRecipeAdapter;
    private List<FoodRecipe> foodRecipes;

    // UI
    private ListView foodItemListView, foodRecipeListView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent();
            int itemId = item.getItemId();
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    intent = new Intent(findViewById(itemId).getContext(),  WeekCalendar.class);
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
        setContentView(R.layout.activity_meal_view);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // initialize variables
        Bundle bundle = getIntent().getExtras();
        myDbHelper = new DataBaseHelper(this);
        myMeal = myDbHelper.getMealByMealDayTypeId(bundle.getInt(IN_MEAL_DAY_TYPE_ID));
        foodItems = new ArrayList<>();
        foodItemAdapter = new FoodItemOnRecipeListAdapter(this, R.layout.list_food_item_on_recipe, foodItems);
        foodRecipes = new ArrayList<>();
        foodRecipeAdapter = new FoodRecipeOnMealListAdapter(this, R.layout.list_food_recipe_on_meal, foodRecipes);

        // set UI
        foodItemListView = (ListView) findViewById(R.id.food_item_list_view);
        foodRecipeListView = (ListView) findViewById(R.id.food_recipe_list_view);

        foodItemListView.setAdapter(foodItemAdapter);
        foodRecipeListView.setAdapter(foodRecipeAdapter);

        updateLists();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FOOD_ITEM_REQUEST:
                if (resultCode == RESULT_OK) {
                    FoodItem fi = new FoodItem(
                            data.getStringExtra(AddFoodItems.RETURN_FOOD_NAME),
                            data.getStringExtra(AddFoodItems.RETURN_FOOD_CATEGORY),
                            data.getStringExtra(AddFoodItems.RETURN_FOOD_NDB),
                            data.getStringExtra(AddFoodItems.RETURN_FOOD_MEASURE),
                            data.getFloatExtra(AddFoodItems.RETURN_FOOD_QUANTITY, 0)
                    );
                    myMeal.addFoodItem(fi);
                    updateFoodItemList();
                }
                break;

            case FOOD_RECIPE_REQUEST:
                if (resultCode == RESULT_OK) {
                    FoodRecipe fr = new FoodRecipe();
                    fr.setRecipeName(data.getStringExtra(RecipeBook.RETURN_RECIPE_NAME));
                    fr.setRecipeQuantity(data.getFloatExtra(RecipeBook.RETURN_RECIPE_QUANTITY, 0));
                    fr.setRecipeServingSize(data.getFloatExtra(RecipeBook.RETURN_RECIPE_SERVING_SIZE, 0));
                    myMeal.addFoodRecipe(fr);
                    updateFoodRecipeList();
                }
                break;

            default:
                break;
        }
    }

    public void addFoodItemButtonClickEventHandler(View view) {
        Intent intent = new Intent(view.getContext(), AddFoodItems.class);
        intent.putExtra(AddFoodItems.FROM_WHAT_SCREEN, AddFoodItems.SCREEN_MEAL_VIEW);
        startActivityForResult(intent, FOOD_ITEM_REQUEST);
    }

    public void addFoodRecipeButtonClickEventHandler(View view) {
        Intent intent = new Intent(view.getContext(), RecipeBook.class);
        intent.putExtra(RecipeBook.IN_FROM_WHAT_SCREEN, RecipeBook.SCREEN_MEAL_VIEW);
        startActivityForResult(intent, FOOD_RECIPE_REQUEST);
    }

    public void removeItemButtonClickEventHandler(View view) {
        Button b = (Button) view.findViewById(R.id.remove_item_button);
        myMeal.getFoodItems().remove(Integer.parseInt(b.getTag().toString()));
        updateFoodItemList();
    }

    public void removeRecipeButtonClickEventHandler(View view) {
        Button b = (Button) view.findViewById(R.id.remove_item_button);
        myMeal.getFoodRecipes().remove(Integer.parseInt(b.getTag().toString()));
        updateFoodRecipeList();
    }


    private void updateLists() {
        updateFoodItemList();
        updateFoodRecipeList();
    }

    private void updateFoodItemList() {
        foodItems.clear();
        for (FoodItem fi : myMeal.getFoodItems()) {
            foodItems.add(fi);
        }
        foodItemAdapter.notifyDataSetChanged();
    }

    private void updateFoodRecipeList() {
        foodRecipes.clear();
        for (FoodRecipe fr : myMeal.getFoodRecipes()) {
            foodRecipes.add(fr);
        }
        foodRecipeAdapter.notifyDataSetChanged();
    }

}
