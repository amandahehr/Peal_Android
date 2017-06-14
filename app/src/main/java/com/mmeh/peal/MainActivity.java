package com.mmeh.peal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.mmeh.peal.database.DataBaseHelper;
import com.mmeh.peal.database.FoodItemContract;
import com.mmeh.peal.model.FoodItemSearch;

import static com.mmeh.peal.database.FoodItemContract.*;

public class MainActivity extends AppCompatActivity {

    TextView txtOne;
    AutoCompleteTextView txtSearch;

    FoodItemSearch foodItemSearch;

    DataBaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        foodItemSearch = new FoodItemSearch();
        final Context thisContext = this;

        txtOne = (TextView) findViewById(R.id.txtOne);
        final Object tag = txtOne.getTag();
        txtOne.setText(tag.toString());
        txtSearch = (AutoCompleteTextView) findViewById(R.id.txtSearch);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("USDAQuery", "lastNameQueried:" + foodItemSearch.getLastNameQueried());
                Log.d("USDAQuery", "???:" + s.toString().startsWith(foodItemSearch.getLastNameQueried()));
                if (! s.toString().startsWith(foodItemSearch.getLastNameQueried()) || (foodItemSearch.getLastNameQueried() == "" && s.length() >= 2) || ! txtSearch.isPopupShowing()) {
                    Log.d("USDAQuery", "executing request");
                    foodItemSearch.searchByName(String.valueOf(txtSearch.getText()), txtSearch, thisContext);
                }
                Log.d("USDAQuery", " ");

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // database test
        myDbHelper = new DataBaseHelper(thisContext);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnOneClickEventHandler(View view) {
        Snackbar.make(view, "btnOne clicked!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        Object tag;
        tag = "button clicked and tag changed!" ;
        txtOne.setTag(tag);
        txtOne.setText(txtOne.getTag().toString());
//        FoodItemSearch.searchByName(String.valueOf(txtSearch.getText()), txtSearch, this);

    }


    public void btnInsertClickEventHandler(View view) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FoodItemEntry.COLUMN_NAME_NAME, String.valueOf(txtSearch.getText()));

        long newRowId = db.insert(FoodItemEntry.TABLE_NAME, null, values);
    }

    public void btnSelectClickEventHandler(View view) {
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] projection = {
                FoodItemEntry._ID,
                FoodItemEntry.COLUMN_NAME_NAME
        };

        String selection = FoodItemEntry.COLUMN_NAME_NAME + " = ?";
        String[] selctionArgs = { " " };

        String sortOrder = FoodItemEntry.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = db.query(
                FoodItemEntry.TABLE_NAME,
                projection,
                null, //selection,
                null, //selectionArgs,
                null,
                null,
                sortOrder
        );

        String result = "";
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FoodItemEntry._ID));
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow(FoodItemEntry.COLUMN_NAME_NAME));
            result += "ID:" + itemId + " Name:" + itemName + "\n";
        }
        cursor.close();

        txtOne.setText(result);
    }

    @Override
    protected void onDestroy() {
        myDbHelper.close();
        super.onDestroy();
    }
}
