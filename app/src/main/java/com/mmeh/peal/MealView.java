package com.mmeh.peal;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mmeh.peal.database.DataBaseHelper;
import com.mmeh.peal.list_adapters.FoodItemOnRecipeListAdapter;
import com.mmeh.peal.list_adapters.FoodRecipeOnMealListAdapter;
import com.mmeh.peal.model.FoodItem;
import com.mmeh.peal.model.FoodRecipe;
import com.mmeh.peal.model.Meal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealView extends AppCompatActivity {

    // in arguments
    public static final String IN_MEAL_DAY_TYPE_ID = "IN_MEAL_DAY_TYPE_ID";
    public static final String IN_MEAL_TYPE = "IN_MEAL_TYPE";
    public static final String IN_MEAL_DAY_ID = "IN_MEAL_DAY_ID";
    public static final String IN_DATE = "IN_DATE";

    // requests
    private static final int FOOD_ITEM_REQUEST = 100;
    private static final int FOOD_RECIPE_REQUEST = 200;

    // out arguments (return)
    public static final String RETURN_MEAL_DAY_ID = "RETURN_MEAL_DAY_ID";
    public static final String RETURN_MEAL_DAY_TYPE_ID = "RETURN_MEAL_DAY_TYPE_ID";
    public static final String RETURN_MEAL_TYPE = "RETURN_MEAL_TYPE";


    // variables
    private DataBaseHelper myDbHelper;
    private Meal myMeal;
    private FoodItemOnRecipeListAdapter foodItemAdapter;
    private List<FoodItem> foodItems;
    private FoodRecipeOnMealListAdapter foodRecipeAdapter;
    private List<FoodRecipe> foodRecipes;
    private int mealDayId;
    private int mealDayTypeId;
    private String fullDate;
    private String mealType;

    // UI
    private ListView foodItemListView, foodRecipeListView;
    private TextView dateTextView, mealTypeTextView;


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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_view);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // initialize variables
        Bundle bundle = getIntent().getExtras();
        mealDayId = bundle.getInt(IN_MEAL_DAY_ID);
        fullDate = bundle.getString(IN_DATE);
        mealDayTypeId = bundle.getInt(IN_MEAL_DAY_TYPE_ID);
        mealType = bundle.getString(IN_MEAL_TYPE);
        myDbHelper = new DataBaseHelper(this);
        myMeal = myDbHelper.getMealByMealDayTypeId(mealDayTypeId);
        foodItems = new ArrayList<>();
        foodItemAdapter = new FoodItemOnRecipeListAdapter(this, R.layout.list_food_item_on_recipe, foodItems);
        foodRecipes = new ArrayList<>();
        foodRecipeAdapter = new FoodRecipeOnMealListAdapter(this, R.layout.list_food_recipe_on_meal, foodRecipes);

        // set UI
        dateTextView = (TextView) findViewById(R.id.date_text_view);
        mealTypeTextView = (TextView) findViewById(R.id.meal_type_text_view);
        foodItemListView = (ListView) findViewById(R.id.food_item_list_view);
        foodRecipeListView = (ListView) findViewById(R.id.food_recipe_list_view);

        DateFormat dateIn = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateTitleFormat = new SimpleDateFormat("EEEE\nMMMM dd");
        try {
            dateTextView.setText(dateTitleFormat.format(dateIn.parse(fullDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String s = mealType.substring(0, 1).toUpperCase() + mealType.substring(1).toLowerCase();
        mealTypeTextView.setText(s);
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
                    fr.setDbId(data.getIntExtra(RecipeBook.RETURN_RECIPE_ID, 0));
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

    public void confirmButtonClickEventHandler(View view) {
        if (mealDayId == 0) {
            mealDayId = (int) myDbHelper.insertNewMealDay(fullDate);
        }
        if (mealDayTypeId == 0) {
            mealDayTypeId = (int) myDbHelper.insertNewMealDayType(mealDayId, mealType);
        }

        // delete existing food items inside MealDayTypeItem
        myDbHelper.deleteMealDayTypeItemByMealDayTypeId(mealDayTypeId);
        // add food items to MealDayTypeItem
        myDbHelper.insertNewMealDayTypeItem(mealDayTypeId, myMeal.getFoodItems());

        // delete existing food recipes inside MealDayTypeRecipe
        myDbHelper.deleteMealDayTypeRecipeByMealDayTypeId(mealDayTypeId);
        // add food recipes to MealDayTypeRecipe
        myDbHelper.insertNewMealDayTypeRecipe(mealDayTypeId, myMeal.getFoodRecipes());

        Intent data = new Intent();
        data.putExtra(RETURN_MEAL_DAY_ID, mealDayId);
        data.putExtra(RETURN_MEAL_DAY_TYPE_ID, mealDayTypeId);
        data.putExtra(RETURN_MEAL_TYPE, mealType);
        setResult(RESULT_OK, data);
        finish();
    }

    public void cancelButtonClickEventHandler(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
