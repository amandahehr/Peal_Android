package com.mmeh.peal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mmeh.peal.database.DataBaseHelper;
import com.mmeh.peal.list_adapters.FoodItemOnRecipeListAdapter;
import com.mmeh.peal.model.FoodItem;
import com.mmeh.peal.model.FoodRecipe;

import java.util.ArrayList;
import java.util.List;


public class AddFoodRecipe extends AppCompatActivity {

    private static final int FOOD_ITEM_REQUEST = 100;

    public static final String RETURN_FOOD_NAME = "RETURN_FOOD_NAME";
    public static final String RETURN_FOOD_CATEGORY = "RETURN_FOOD_CATEGORY";
    public static final String RETURN_FOOD_MEASURE = "RETURN_FOOD_MEASURE";
    public static final String RETURN_FOOD_NDB = "RETURN_FOOD_NDB";

    private DataBaseHelper myDbHelper;
    private List<FoodItem> foodItemsOnRecipe;
    private FoodItemOnRecipeListAdapter adapter;
    private FoodRecipe foodRecipe;
    private int foodRecipeId;
    private boolean recipeEditable;

    private FloatingActionButton fab;
    private EditText foodRecipeNameEditText;
    private EditText foodRecipeInstructionsEditText;
    private ListView listFoodItemsOnRecipeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initialization of attributes and variables
        Bundle b = getIntent().getExtras();
        foodItemsOnRecipe = new ArrayList<>();
        adapter = new FoodItemOnRecipeListAdapter(
                AddFoodRecipe.this, R.layout.list_food_item_on_recipe, foodItemsOnRecipe
        );
        foodRecipeId = b.getInt(RecipeBook.FOOD_RECIPE_ID);
        recipeEditable = false;
        myDbHelper = new DataBaseHelper(this);

        // associating attributes with components in screen
        fab = (FloatingActionButton) findViewById(R.id.fab);
        foodRecipeNameEditText = (EditText) findViewById(R.id.food_recipe_name_edit_text);
        listFoodItemsOnRecipeListView = (ListView) findViewById(R.id.list_food_items_on_recipe_list_view);
        foodRecipeInstructionsEditText = (EditText) findViewById(R.id.food_recipe_instructions_edit_text);


        listFoodItemsOnRecipeListView.setAdapter(adapter);

        // setting the image of floating button
        if (foodRecipeId == 0) { // new food recipe
            fab.setImageResource(android.R.drawable.ic_menu_save);
            foodRecipe = new FoodRecipe();

        } else { // edit food recipe
            // TODO: Code search for RecipeID and fill foodRecipe
            // TODO: Code fill the objects in the screen with foodRecipe
            fab.setImageResource(android.R.drawable.ic_menu_edit);
            setAllComponentsEditable(false);

        }


        // setting the event listener for the floating button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

    }

    @Override
    protected void onDestroy() {
        myDbHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FOOD_ITEM_REQUEST) {
            if (resultCode == RESULT_OK) {
                FoodItem fi = new FoodItem(
                        data.getStringExtra(RETURN_FOOD_NAME),
                        data.getStringExtra(RETURN_FOOD_CATEGORY),
                        data.getStringExtra(RETURN_FOOD_NDB),
                        data.getStringExtra(RETURN_FOOD_MEASURE)
                );
                foodItemsOnRecipe.add(fi);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void setAllComponentsEditable(boolean b) {
        foodRecipeNameEditText.setEnabled(b);
        listFoodItemsOnRecipeListView.setEnabled(b);
        foodRecipeInstructionsEditText.setEnabled(b);
        recipeEditable = b;
    }

    public void removeItemButtonClickEventHandler(View view) {
        // saving all the quantities
        saveAllQuantitiesInList();

        Button b = (Button) view.findViewById(R.id.remove_item_button);
        FoodItem fi = foodItemsOnRecipe.get(Integer.parseInt(b.getTag().toString()));
        foodItemsOnRecipe.remove(fi);
        adapter.notifyDataSetChanged();
    }

    public void addFoodItemOnRecipeClickEventHandler(View view) {
        // saving all the quantities
        saveAllQuantitiesInList();

        Intent intent = new Intent(view.getContext(), AddFoodItems.class);
        intent.putExtra(AddFoodItems.FROM_WHAT_SCREEN, AddFoodItems.SCREEN_ADD_FOOD_RECIPE);
        startActivityForResult(intent, FOOD_ITEM_REQUEST);
    }

    private void saveAllQuantitiesInList() {
        for (int i = 0; i < listFoodItemsOnRecipeListView.getChildCount(); i++) {
            EditText et = (EditText) listFoodItemsOnRecipeListView.getChildAt(i).findViewById(R.id.quantity_edit_text);
            float qt = Float.parseFloat(et.getText().toString());
            FoodItem fi = foodItemsOnRecipe.get(i);
            fi.setItemQuantity(qt);
        }
    }

    private void saveRecipe() {
        foodRecipe.setRecipeName(foodRecipeNameEditText.getText().toString());
        foodRecipe.setRecipeInstructions(foodRecipeInstructionsEditText.getText().toString());
        ArrayList<FoodItem> foodItems = new ArrayList<>();
        foodItems.addAll(foodItemsOnRecipe);
        foodRecipe.setFoodItems(foodItems);

        myDbHelper.insertNewFoodRecipe(foodRecipe);
    }
}
