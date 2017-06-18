package com.mmeh.peal.model;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mmeh.peal.R;

import java.util.List;

public class FoodItemListAdapter extends ArrayAdapter<FoodItem>{

    private List<FoodItem> foodItems;

    public FoodItemListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<FoodItem> objects) {
        super(context, resource, objects);
        foodItems = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_food_item, parent, false);
        }

        FoodItem foodItem = foodItems.get(position);

        TextView foodNameTextView = (TextView) convertView.findViewById(R.id.foodNameTextView);
        foodNameTextView.setText(foodItem.getItemName());
        TextView foodCategoryTextView = (TextView) convertView.findViewById(R.id.foodCategoryTextView);
        foodCategoryTextView.setText(foodItem.getItemCategory());
//        TextView foodMeasureTextView = (TextView) convertView.findViewById(R.id.foodMeasureTextView);
//        String measure = (foodItem.getItemMeasure().length() == 0 ? "" : "(" + foodItem.getItemMeasure() + ")");
//        foodMeasureTextView.setText(measure);

        return convertView;
    }
}
