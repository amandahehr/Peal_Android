package com.mmeh.peal.list_adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mmeh.peal.R;
import com.mmeh.peal.model.FoodRecipe;

import java.util.List;

public class FoodRecipeListAdapter extends ArrayAdapter<FoodRecipe> {

    private List<FoodRecipe> foodRecipes;

    public FoodRecipeListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<FoodRecipe> objects) {
        super(context, resource, objects);
        foodRecipes = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_food_recipe, parent, false);
        }

        FoodRecipe foodRecipe = foodRecipes.get(position);

        // letter will only show when it's the first recipe name with a different starting letter
        String letter;
        if (position == 0) { // if it's first then always show first letter
            letter = foodRecipe.getRecipeName().substring(0, 1);
        } else {
            if (foodRecipes.get(position-1).getRecipeName().substring(0, 1).equals(foodRecipe.getRecipeName().substring(0, 1))) {
                letter = " ";
            } else { // if first letter of current is different than first letter of last then show first letter of current
                letter = foodRecipe.getRecipeName().substring(0, 1);
            }
        }


        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.rgb(249, 236, 223));
        } else {
            convertView.setBackgroundColor(Color.rgb(234, 219, 206));
        }

        TextView letterTextView = (TextView) convertView.findViewById(R.id.letter_text_view);
        letterTextView.setText(letter);
        TextView recipeNameTextView = (TextView) convertView.findViewById(R.id.recipe_name_text_view);
        recipeNameTextView.setText(foodRecipe.getRecipeName());



        return convertView;
    }

}
