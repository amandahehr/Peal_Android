package com.mmeh.peal.list_adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mmeh.peal.R;
import com.mmeh.peal.model.FoodRecipe;

import java.util.List;

public class FoodRecipeOnMealListAdapter extends ArrayAdapter<FoodRecipe> {

    private List<FoodRecipe> foodRecipes;

    public FoodRecipeOnMealListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<FoodRecipe> objects) {
        super(context, resource, objects);
        foodRecipes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_food_recipe_on_meal, parent, false);
        }

        FoodRecipe fr = foodRecipes.get(position);

        TextView recipeNameTextView = (TextView) convertView.findViewById(R.id.recipe_name_text_view);
        recipeNameTextView.setText(fr.getRecipeName());
        TextView recipePortionTextView = (TextView) convertView.findViewById(R.id.recipe_portion_text_view);
        recipePortionTextView.setText("Servings: " + fr.getRecipeServingSize());
        TextView quantityTextView = (TextView) convertView.findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(fr.getRecipeQuantity()));
        Button btnRemove = (Button) convertView.findViewById(R.id.remove_item_button);
        btnRemove.setTag(String.valueOf(position));

        return convertView;
    }
}
