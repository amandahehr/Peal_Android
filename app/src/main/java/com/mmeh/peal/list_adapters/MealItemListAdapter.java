package com.mmeh.peal.list_adapters;

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

import org.w3c.dom.Text;

import java.util.List;

public class MealItemListAdapter extends ArrayAdapter<String> {

    private List<String> items;

    public MealItemListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_meal, parent, false);
        }

        TextView itemNameTextView = (TextView) convertView.findViewById(R.id.item_name_text_view);
        itemNameTextView.setText(items.get(position));

        return convertView;
    }
}

