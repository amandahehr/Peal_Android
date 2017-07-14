package com.mmeh.peal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SingleItem extends AppCompatActivity {

    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_MEASURE = "ITEM_MEASURE";
    public static final String ITEM_QUANTITY = "ITEM_QUANTITY";

    // return
    public static final String RETURN_QUANTITY = "RETURN_QUANTITY";

    private int fromWhatScreen;
    public static String FROM_WHAT_SCREEN = "FROM_WHAT_SCREEN";
    public static final int SCREEN_ADD_FOOD_ITEMS = 1;
    public static final int SCREEN_ADD_FOOD_RECIPE = 2;
    public static final int SCREEN_RECIPE_BOOK = 3;

    private TextView itemNameTextView;
    private TextView itemMeasureTextView;
    private EditText quantityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemNameTextView = (TextView) findViewById(R.id.item_name_text_view);
        itemMeasureTextView = (TextView) findViewById(R.id.item_measure_text_view);
        quantityEditText = (EditText) findViewById(R.id.quantity_edit_text);

        Bundle b = getIntent().getExtras();
        try {
            fromWhatScreen = b.getInt(FROM_WHAT_SCREEN);
            itemNameTextView.setText(b.getString(ITEM_NAME));
            itemMeasureTextView.setText(b.getString(ITEM_MEASURE));
        } catch (Exception e) {
            Toast.makeText(this, "Error on get bundle (previous screen)", Toast.LENGTH_LONG).show();
        }

        try {
            quantityEditText.setText(String.valueOf(b.getFloat(ITEM_QUANTITY)));
        } catch (Exception e) {
            quantityEditText.setText("1.0");
        }


    }

    public void confirmButtonClickEventHandler(View view) {
        float q = Float.parseFloat(quantityEditText.getText().toString());
        Intent data = new Intent();
        switch (fromWhatScreen) {
            case SCREEN_ADD_FOOD_ITEMS:
                data.putExtra(AddFoodItems.RETURN_QUANTITY, q);
                break;
            case SCREEN_ADD_FOOD_RECIPE:
                data.putExtra(AddFoodRecipe.RETURN_FOOD_QUANTITY, q);
                break;
            default:
                data.putExtra(AddFoodItems.RETURN_QUANTITY, q);
                break;
        }
        setResult(RESULT_OK, data);
        finish();
    }
}
