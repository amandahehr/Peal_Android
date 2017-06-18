package com.mmeh.peal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;

public class AddFoodItems extends AppCompatActivity {

    private final String TAG_SEARCH_MEASURE = "USDAQuery-SearchMeasure";
    private final String TAG_SEARCH_NAME = "USDAQuery-SearchName";

    private List<FoodItem> foodItems;
    private RequestQueue queue;

    private ListView foodItemsListView;

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


        foodItems = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        foodItemsListView = (ListView) findViewById(R.id.foodItemsListView);
    }

    public void btnSearchClickEventHandler(View view) {
        TextView txtSearch = (TextView) findViewById(R.id.txtSearch);

        foodItems.clear();
        foodItemsListView.setAdapter(null);

        // cancelling all requests about this search if in queue
        queue.cancelAll(TAG_SEARCH_NAME);

        // first StringRequest: getting items searched
        StringRequest stringRequest = searchNameStringRequest(txtSearch.getText().toString());
        stringRequest.setTag(TAG_SEARCH_NAME);

        // executing the request (adding to queue)
        queue.add(stringRequest);

    }

    private StringRequest searchNameStringRequest(String nameSearch) {
        final String API = "&api_key=HRjlHt125eNuO6a6xE9KqrVKEpjHuAIBZrZhQFBZ";
        final String NAME_SEARCH = "&q=";
        final String DATA_SOURCE = "&ds=Standard Reference";
        final String FOOD_GROUP = "&fg=";
        final String SORT = "&sort=r";
        final String MAX_ROWS = "&max=25";
        final String BEGINNING_ROW = "&offset=0";
        final String URL_PREFIX = "https://api.nal.usda.gov/ndb/search/?format=json";

        String url = URL_PREFIX + API + NAME_SEARCH + nameSearch + DATA_SOURCE + FOOD_GROUP + SORT + MAX_ROWS + BEGINNING_ROW;

        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response).getJSONObject("list");
                            int maxItems = result.getInt("end");
                            JSONArray resultList = result.getJSONArray("item");

                            for (int i = 0; i < maxItems; i++) {
                                FoodItem fi = new FoodItem(
                                        0,
                                        resultList.getJSONObject(i).getString("name").trim(),
                                        "",
                                        resultList.getJSONObject(i).getString("group").trim(),
                                        resultList.getJSONObject(i).getString("ndbno").trim(),
                                        ""
                                );
                                foodItems.add(fi);
                            }

                            showListOfItems();

                        } catch (JSONException e) {
                            Toast.makeText(AddFoodItems.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddFoodItems.this, "Food source is not responding (USDA API)", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showListOfItems() {
        FoodItemListAdapter adapter = new FoodItemListAdapter(
                AddFoodItems.this, R.layout.list_food_item, foodItems
        );

        foodItemsListView.setAdapter(adapter);

        foodItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent;


                // cancelling all requests about this search if in queue
                queue.cancelAll(TAG_SEARCH_MEASURE);
                // StringRequest: getting measure of the items
                // on the return of the request, go back to the caller Activity
                StringRequest stringRequest = searchItemMeasureStringRequest(foodItems.get(position).getItemNDB(), position);
                stringRequest.setTag(TAG_SEARCH_MEASURE);

                // executing the request (adding to queue)
                queue.add(stringRequest);
            }
        });
    }

    private StringRequest searchItemMeasureStringRequest(String ndbno, final int index) {

        //  StringRequest: getting measure of the items
        final String API = "&api_key=HRjlHt125eNuO6a6xE9KqrVKEpjHuAIBZrZhQFBZ";
        final String NUTRIENTS = "&nutrients=208";
        final String NDBNO = "&ndbno=" + ndbno;
        final String URL_PREFIX = "https://api.nal.usda.gov/ndb/nutrients/?format=json";

        String url = URL_PREFIX + API + NUTRIENTS + NDBNO;

        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject result = new JSONObject(response).
                                    getJSONObject("report").
                                    getJSONArray("foods").
                                    getJSONObject(0);
                            String measure = result.getString("measure").toLowerCase() +
                                    " / " + String.valueOf(result.getLong("weight")) +
                                    "g";
                            foodItems.get(index).setItemMeasure(measure);

                        } catch (JSONException e) {
                            Toast.makeText(AddFoodItems.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        finishMyActivity(index);

                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddFoodItems.this, "Food source is not responding (USDA API). Result is not complete.", Toast.LENGTH_LONG).show();
                        finishMyActivity(index);
                    }
                }); // Response.ErrorListener()
    }

    private void finishMyActivity(int index) {
        Intent data = new Intent();
        data.putExtra(MealView.RETURN_MESSAGE, foodItems.get(index).toString());
        setResult(RESULT_OK, data);
        finish();
    }
}
