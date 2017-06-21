package com.mmeh.peal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.mmeh.peal.list_adapters.FoodItemOnRecipeListAdapter;
import com.mmeh.peal.model.FoodItem;

import java.util.ArrayList;
import java.util.List;


public class AddFoodRecipe extends AppCompatActivity {

    private static final int FOOD_ITEM_REQUEST = 100;

    public static final String RETURN_FOOD_NAME = "RETURN_FOOD_NAME";
    public static final String RETURN_FOOD_CATEGORY = "RETURN_FOOD_CATEGORY";
    public static final String RETURN_FOOD_MEASURE = "RETURN_FOOD_MEASURE";
    public static final String RETURN_FOOD_NDB = "RETURN_FOOD_NDB";

    private List<FoodItem> foodItemsOnRecipe;
    private FoodItemOnRecipeListAdapter adapter;

    private ListView listFoodItemsOnRecipeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        foodItemsOnRecipe = new ArrayList<>();
        adapter = new FoodItemOnRecipeListAdapter(
                AddFoodRecipe.this, R.layout.list_food_item_on_recipe, foodItemsOnRecipe
        );
        listFoodItemsOnRecipeListView = (ListView) findViewById(R.id.list_food_items_on_recipe_list_view);

        Bundle b = getIntent().getExtras();
        final int foodRecipeId = b.getInt(RecipeBook.FOOD_RECIPE_ID);
        if (foodRecipeId == 0) { // new food recipe
            fab.setImageResource(android.R.drawable.ic_menu_save);

        } else { // edit food recipe
            fab.setImageResource(android.R.drawable.ic_menu_edit);
            setAllComponentsEditable(false);
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (foodRecipeId == 0) { // new food recipe

                } else {

                }
            }
        });


//        // for test
//        FoodItem fi = new FoodItem(
//                0,
//                "Name Test",
//                "Description Test",
//                "Category Test",
//                "NDB Test",
//                "Measure Test"
//        );
//        foodItemsOnRecipe.add(fi);
//        listFoodItemsOnRecipeListView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FOOD_ITEM_REQUEST) {
            if (resultCode == RESULT_OK) {
                FoodItem fi = new FoodItem(
                        0,
                        data.getStringExtra(RETURN_FOOD_NAME),
                        "",
                        data.getStringExtra(RETURN_FOOD_CATEGORY),
                        data.getStringExtra(RETURN_FOOD_NDB),
                        data.getStringExtra(RETURN_FOOD_MEASURE)
                );
                foodItemsOnRecipe.add(fi);
                listFoodItemsOnRecipeListView.setAdapter(adapter);
            }
        }
    }

    private void setAllComponentsEditable(boolean b) {

    }

    public void removeItemButtonClickEventHandler(View view) {

    }

    public void addFoodItemOnRecipeClickEventHandler(View view) {
        Intent intent = new Intent(view.getContext(), AddFoodItems.class);
        intent.putExtra(AddFoodItems.FROM_WHAT_SCREEN, AddFoodItems.SCREEN_ADD_FOOD_RECIPE);
        startActivityForResult(intent, FOOD_ITEM_REQUEST);
    }
}
