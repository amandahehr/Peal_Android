package com.mmeh.peal.list_adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mmeh.peal.R;
import com.mmeh.peal.model.FoodItem;

import java.util.List;

public class FoodItemOnRecipeListAdapter extends ArrayAdapter<FoodItem> {
    private List<FoodItem> foodItems;

    public FoodItemOnRecipeListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<FoodItem> objects) {
        super(context, resource, objects);
        foodItems = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_food_item_on_recipe, parent, false);
        }

        FoodItem foodItem = foodItems.get(position);

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.rgb(249, 236, 223));
        } else {
            convertView.setBackgroundColor(Color.rgb(234, 219, 206));
        }

        TextView foodNameTextView = (TextView) convertView.findViewById(R.id.food_name_text_view);
        foodNameTextView.setText(foodItem.getItemName());
        TextView foodMeasureTextView = (TextView) convertView.findViewById(R.id.food_measure_text_view);
        foodMeasureTextView.setText(foodItem.getItemMeasure());
        TextView quantityTextView = (TextView) convertView.findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(foodItem.getItemQuantity()));
        Button btnRemove = (Button) convertView.findViewById(R.id.remove_item_button);
        btnRemove.setTag(String.valueOf(position));

        return convertView;
    }
}
