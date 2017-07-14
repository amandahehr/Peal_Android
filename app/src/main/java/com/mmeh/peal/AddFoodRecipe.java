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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mmeh.peal.database.DataBaseHelper;
import com.mmeh.peal.list_adapters.FoodItemOnRecipeListAdapter;
import com.mmeh.peal.model.FoodItem;
import com.mmeh.peal.model.FoodRecipe;

import java.util.ArrayList;
import java.util.List;


public class AddFoodRecipe extends AppCompatActivity {

    // requests
    private static final int FOOD_ITEM_REQUEST = 100;
    private static final int SINGLE_ITEM_REQUEST = 200;

    // in arguments
    public static final String FOOD_RECIPE_ID = "FOOD_RECIPE_ID";

    // returns
    public static final String RETURN_FOOD_NAME = "RETURN_FOOD_NAME";
    public static final String RETURN_FOOD_CATEGORY = "RETURN_FOOD_CATEGORY";
    public static final String RETURN_FOOD_MEASURE = "RETURN_FOOD_MEASURE";
    public static final String RETURN_FOOD_NDB = "RETURN_FOOD_NDB";
    public static final String RETURN_FOOD_QUANTITY = "RETURN_FOOD_QUANTITY";

    private DataBaseHelper myDbHelper;
    private List<FoodItem> foodItemsOnRecipe;
    private FoodItemOnRecipeListAdapter adapter;
    private FoodRecipe foodRecipe;
    private int foodRecipeId;
    private boolean recipeEditable;
    private int itemIndexEditQuantity;

    private FloatingActionButton fab;
    private EditText foodRecipeNameEditText;
    private EditText foodRecipeInstructionsEditText;
    private ListView listFoodItemsOnRecipeListView;
    private Button addFoodItemOnRecipeButton;
    private Button deleteRecipeButton;

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
        setContentView(R.layout.activity_add_food_recipe);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        setSupportActionBar(toolbar);


        // initialization of attributes and variables
        Bundle b = getIntent().getExtras();
        foodItemsOnRecipe = new ArrayList<>();
        adapter = new FoodItemOnRecipeListAdapter(
                AddFoodRecipe.this, R.layout.list_food_item_on_recipe, foodItemsOnRecipe
        );
        foodRecipeId = b.getInt(FOOD_RECIPE_ID);
        recipeEditable = false;
        itemIndexEditQuantity = 0;
        myDbHelper = new DataBaseHelper(this);

        // associating attributes with components in screen
        fab = (FloatingActionButton) findViewById(R.id.fab);
        foodRecipeNameEditText = (EditText) findViewById(R.id.food_recipe_name_edit_text);
        listFoodItemsOnRecipeListView = (ListView) findViewById(R.id.list_food_items_on_recipe_list_view);
        foodRecipeInstructionsEditText = (EditText) findViewById(R.id.food_recipe_instructions_edit_text);
        addFoodItemOnRecipeButton = (Button) findViewById(R.id.add_food_item_on_recipe_button);
        deleteRecipeButton = (Button) findViewById(R.id.delete_recipe_button);

        listFoodItemsOnRecipeListView.setAdapter(adapter);

        // setting the image of floating button
        if (foodRecipeId == 0) { // new food recipe
            fab.setImageResource(android.R.drawable.ic_menu_save);
            foodRecipe = new FoodRecipe();

        } else { // edit food recipe
            // TODO: Code search for RecipeID and fill foodRecipe
            // TODO: Code fill the objects in the screen with foodRecipe
            foodRecipe = myDbHelper.getFoodRecipeById(foodRecipeId);

            foodRecipeNameEditText.setText(foodRecipe.getRecipeName());
            foodRecipeInstructionsEditText.setText(foodRecipe.getRecipeInstructions());
            foodItemsOnRecipe.addAll(foodRecipe.getFoodItems());
            adapter.notifyDataSetChanged();

            fab.setImageResource(android.R.drawable.ic_menu_edit);
            setAllComponentsEditable(false);

        }


        // setting the event listener for the floating button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foodRecipeId == 0) { // new food recipe
                    saveRecipe();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    if (recipeEditable) {
                        // TODO: code delete entire recipe by foodRecipeId and save recipe and finish activity with return
                    } else {
                        fab.setImageResource(android.R.drawable.ic_menu_save);
                        setAllComponentsEditable(true);
                    }
                }
            }
        });

       deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFoodRecipe();
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
            case FOOD_ITEM_REQUEST:
                if (resultCode == RESULT_OK) {
                    FoodItem fi = new FoodItem(
                            data.getStringExtra(RETURN_FOOD_NAME),
                            data.getStringExtra(RETURN_FOOD_CATEGORY),
                            data.getStringExtra(RETURN_FOOD_NDB),
                            data.getStringExtra(RETURN_FOOD_MEASURE),
                            data.getFloatExtra(RETURN_FOOD_QUANTITY, 0)
                    );
                    foodItemsOnRecipe.add(fi);
                    adapter.notifyDataSetChanged();
                }
                break;
            case SINGLE_ITEM_REQUEST:
                if (resultCode == RESULT_OK) {
                    float f = data.getFloatExtra(RETURN_FOOD_QUANTITY, 0);
                    foodItemsOnRecipe.get(itemIndexEditQuantity).setItemQuantity(f);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private void setAllComponentsEditable(boolean b) {
        foodRecipeNameEditText.setEnabled(b);
        listFoodItemsOnRecipeListView.setEnabled(b);
        foodRecipeInstructionsEditText.setEnabled(b);
        addFoodItemOnRecipeButton.setEnabled(b);
        recipeEditable = b;
    }

    public void removeItemButtonClickEventHandler(View view) {
        if (!recipeEditable) return;

        Button b = (Button) view.findViewById(R.id.remove_item_button);
        FoodItem fi = foodItemsOnRecipe.get(Integer.parseInt(b.getTag().toString()));
        foodItemsOnRecipe.remove(fi);
        adapter.notifyDataSetChanged();
    }

    public void editItemQuantityClickEventHandler(View view) {
        if (!recipeEditable) return;

        Button b = (Button) view.findViewById(R.id.remove_item_button);
        FoodItem fi = foodItemsOnRecipe.get(Integer.parseInt(b.getTag().toString()));

        Intent intent = new Intent(AddFoodRecipe.this, SingleItem.class);
        intent.putExtra(SingleItem.FROM_WHAT_SCREEN, SingleItem.SCREEN_ADD_FOOD_RECIPE);
        intent.putExtra(SingleItem.ITEM_NAME, fi.getItemName());
        intent.putExtra(SingleItem.ITEM_MEASURE, fi.getItemMeasure());
        intent.putExtra(SingleItem.ITEM_QUANTITY, fi.getItemQuantity());
        itemIndexEditQuantity = Integer.parseInt(b.getTag().toString());
        startActivityForResult(intent, SINGLE_ITEM_REQUEST);
    }

    public void addFoodItemOnRecipeClickEventHandler(View view) {
        Intent intent = new Intent(view.getContext(), AddFoodItems.class);
        intent.putExtra(AddFoodItems.FROM_WHAT_SCREEN, AddFoodItems.SCREEN_ADD_FOOD_RECIPE);
        startActivityForResult(intent, FOOD_ITEM_REQUEST);
    }

    private void saveRecipe() {
        foodRecipe.setRecipeName(foodRecipeNameEditText.getText().toString());
        foodRecipe.setRecipeInstructions(foodRecipeInstructionsEditText.getText().toString());
        ArrayList<FoodItem> foodItems = new ArrayList<>();
        foodItems.addAll(foodItemsOnRecipe);
        foodRecipe.setFoodItems(foodItems);

        myDbHelper.insertNewFoodRecipe(foodRecipe);
    }

    public void deleteFoodRecipe(){
        Intent intent;
        intent = new Intent(this, RecipeBook.class);
        startActivity(intent);

        String id = Integer.toString(foodRecipeId);
        String[] id2 = {id};
        myDbHelper.deleteRecipe(id);

    }

}
