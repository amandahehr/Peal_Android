package com.mmeh.peal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmeh.peal.database.DataBaseHelper;
import com.mmeh.peal.list_adapters.MealItemListAdapter;
import com.mmeh.peal.model.FoodItem;
import com.mmeh.peal.model.FoodRecipe;
import com.mmeh.peal.model.MealPlan;

import java.util.ArrayList;
import java.util.List;

public class DayView extends AppCompatActivity {

    // in arguments
    public static final String IN_DATE = "IN_DATE";

    // requests
    private static final int MEAL_VIEW_REQUEST = 100;

    private DataBaseHelper myDbHelper;
    private MealPlan myMealPlan;
    private MealItemListAdapter breakfastAdapter, lunchAdapter, dinnerAdapter;
    private List<String> breakfastItems, lunchItems, dinnerItems;
    private int breakfastMealDayTypeId, lunchMealDayTypeId, dinnerMealDayTypeId;

    // UI
    private ListView breakfastListView, lunchListView, dinnerListView;


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
        setContentView(R.layout.activity_day_view);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // initialize variables
        Bundle bundle = getIntent().getExtras();
        String month = bundle.getString("Month");
        String day = bundle.getString("Day");
        String fullDate = bundle.getString(IN_DATE);
        myDbHelper = new DataBaseHelper(this);
        myMealPlan = new MealPlan(fullDate);
        breakfastItems = new ArrayList<>();
        lunchItems = new ArrayList<>();
        dinnerItems = new ArrayList<>();
        breakfastAdapter = new MealItemListAdapter(this, R.layout.list_item_meal, breakfastItems);
        lunchAdapter = new MealItemListAdapter(this, R.layout.list_item_meal, lunchItems);
        dinnerAdapter = new MealItemListAdapter(this, R.layout.list_item_meal, dinnerItems);

        // set UI
        TextView dayText = (TextView) findViewById(R.id.day_text_view);
        breakfastListView = (ListView) findViewById(R.id.breakfast_list_view);
        lunchListView = (ListView) findViewById(R.id.lunch_list_view);
        dinnerListView = (ListView) findViewById(R.id.dinner_list_view);

        dayText.setText(month + " " + day);
        breakfastListView.setAdapter(breakfastAdapter);
        lunchListView.setAdapter(lunchAdapter);
        dinnerListView.setAdapter(dinnerAdapter);

        // find in DB the meal plan for this date and fill the meals stored
        myMealPlan.setBreakfastMeal(myDbHelper.getMealByDateType(fullDate, myMealPlan.BREAKFAST));
        myMealPlan.setLunchMeal(myDbHelper.getMealByDateType(fullDate, myMealPlan.LUNCH));
        myMealPlan.setDinnerMeal(myDbHelper.getMealByDateType(fullDate, myMealPlan.DINNER));

        // fill items and update adapters
        updateAdapters();

        breakfastMealDayTypeId = (int) myDbHelper.getMealDayTypeIdByDateType(fullDate, myMealPlan.BREAKFAST);
        lunchMealDayTypeId = (int) myDbHelper.getMealDayTypeIdByDateType(fullDate, myMealPlan.LUNCH);
        dinnerMealDayTypeId = (int) myDbHelper.getMealDayTypeIdByDateType(fullDate, myMealPlan.DINNER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MEAL_VIEW_REQUEST:
                if (resultCode == RESULT_OK) {

                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        myDbHelper.close();
        super.onDestroy();
    }

    private void updateAdapters() {
        // breakfast
        breakfastItems.clear();
        for (FoodItem fi : myMealPlan.getBreakfastMeal().getFoodItems()) {
            breakfastItems.add(fi.getItemName());
        }
        for (FoodRecipe fr : myMealPlan.getBreakfastMeal().getFoodRecipes()) {
            breakfastItems.add(fr.getRecipeName());
        }
        breakfastAdapter.notifyDataSetChanged();

        // lunch
        lunchItems.clear();
        for (FoodItem fi : myMealPlan.getLunchMeal().getFoodItems()) {
            lunchItems.add(fi.getItemName());
        }
        for (FoodRecipe fr : myMealPlan.getLunchMeal().getFoodRecipes()) {
            lunchItems.add(fr.getRecipeName());
        }
        lunchAdapter.notifyDataSetChanged();

        // lunch
        dinnerItems.clear();
        for (FoodItem fi : myMealPlan.getDinnerMeal().getFoodItems()) {
            dinnerItems.add(fi.getItemName());
        }
        for (FoodRecipe fr : myMealPlan.getDinnerMeal().getFoodRecipes()) {
            dinnerItems.add(fr.getRecipeName());
        }
        dinnerAdapter.notifyDataSetChanged();

    }

    public void viewBreakfastClickEventHandler(View view) {
        Intent intent = new Intent(view.getContext(), MealView.class);
        intent.putExtra(MealView.IN_MEAL_DAY_TYPE_ID, breakfastMealDayTypeId);
        startActivityForResult(intent, MEAL_VIEW_REQUEST);
    }

    public void viewLunchClickEventListener(View view) {
        Intent intent = new Intent(view.getContext(), MealView.class);
        intent.putExtra(MealView.IN_MEAL_DAY_TYPE_ID, lunchMealDayTypeId);
        startActivityForResult(intent, MEAL_VIEW_REQUEST);
    }

    public void viewDinnerClickEventListener(View view) {
        Intent intent = new Intent(view.getContext(), MealView.class);
        intent.putExtra(MealView.IN_MEAL_DAY_TYPE_ID, dinnerMealDayTypeId);
        startActivityForResult(intent, MEAL_VIEW_REQUEST);
    }
}
