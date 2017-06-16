package com.mmeh.peal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mmeh.peal.model.FoodItem;
import com.mmeh.peal.model.FoodItemListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

public class AddFoodItems extends AppCompatActivity {

    private List<FoodItem> foodItems;
    private Context thisContext;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent();
            int itemId = item.getItemId();
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    intent = new Intent(findViewById(itemId).getContext(), MonthCalendar.class);
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
        setContentView(R.layout.activity_add_food_items);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        thisContext = this;
        foodItems = new ArrayList<>();

        // William - for test
        String[] items = {"test one", "test two", "test three"};
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(this,
//                        android.R.layout.simple_list_item_1,
//                        android.R.id.)


    }

    public void btnSearchClickEventHandler(View view) {
//        foodItems = new ArrayList<>();
//        FoodItem newFood1 = new FoodItem(1, "Item 1", "Desc 1", "Cat 1", "NDB 1", "Measure 1");
//        foodItems.add(newFood1);
//        FoodItem newFood2 = new FoodItem(2, "Item 2", "Desc 2", "Cat 2", "NDB 2", "Measure 2");
//        foodItems.add(newFood2);



        TextView txtSearch = (TextView) findViewById(R.id.txtSearch);


        final String API = "&api_key=HRjlHt125eNuO6a6xE9KqrVKEpjHuAIBZrZhQFBZ";
        final String NAME_SEARCH = "&q=";
        final String DATA_SOURCE = "&ds=Standard Reference";
        final String FOOD_GROUP = "&fg=";
        final String SORT = "&sort=r";
        final String MAX_ROWS = "&max=50";
        final String BEGINNING_ROW = "&offset=0";
        final String URL_PREFIX = "https://api.nal.usda.gov/ndb/search/?format=json";

        final String TAG = "USDAQuery";

        String url = URL_PREFIX + API + NAME_SEARCH + txtSearch.getText() + DATA_SOURCE + FOOD_GROUP + SORT + MAX_ROWS + BEGINNING_ROW;

        foodItems.clear();

        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        queue.cancelAll(TAG);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response).getJSONObject("list");
                            int maxItems = result.getInt("end");
                            JSONArray resultList = result.getJSONArray("item");

                            // TODO: filter this list. Apparently it is showing non necessary groups.
                            for (int i = 0; i < maxItems; i++) {
                                FoodItem fi = new FoodItem(
                                        0,
                                        resultList.getJSONObject(i).getString("name"),
                                        resultList.getJSONObject(i).getString("name"),
                                        resultList.getJSONObject(i).getString("group"),
                                        resultList.getJSONObject(i).getString("ndbno"),
                                        ""
                                );
                                foodItems.add(fi);
                            }


                        } catch (JSONException e) {
                        }

                        FoodItemListAdapter adapter = new FoodItemListAdapter(
                                thisContext, R.layout.list_food_item, foodItems
                        );

                        ListView lv = (ListView) findViewById(R.id.listView);
                        lv.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: if the user keeps typing this message will also keep showing
                        Toast.makeText(thisContext, "Food source is not responding (USDA API)", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag(TAG);

        // executing the request (adding to queue)
        queue.add(stringRequest);

    }
}
