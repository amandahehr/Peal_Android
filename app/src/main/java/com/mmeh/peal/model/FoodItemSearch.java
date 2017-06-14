package com.mmeh.peal.model;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodItemSearch {

    private String lastNameQueried;

    public FoodItemSearch() {
        lastNameQueried = "";
    }

    public String getLastNameQueried() {
        return lastNameQueried;
    }

    public static FoodItem searchById(String itemId) {
        //TODO: code this method - searchById
        FoodItem foodItem = new FoodItem();
        
        return foodItem;
    }

    public static FoodItem searchByNDB(String itemNDB) {
        //TODO: code this method - searchByNDB
        FoodItem foodItem = new FoodItem();

        return foodItem;
    }

    public void searchByName(String nameSearch, final AutoCompleteTextView textView, final Context context) {
        final String API = "&api_key=HRjlHt125eNuO6a6xE9KqrVKEpjHuAIBZrZhQFBZ";
        final String NAME_SEARCH = "&q=";
        final String DATA_SOURCE = "&ds=Standard Reference";
        final String FOOD_GROUP = "&fg=";
        final String SORT = "&sort=r";
        final String MAX_ROWS = "&max=50";
        final String BEGINNING_ROW = "&offset=0";
        final String URL_PREFIX = "https://api.nal.usda.gov/ndb/search/?format=json";

        final String TAG = "USDAQuery";

        String url = URL_PREFIX + API + NAME_SEARCH + nameSearch + DATA_SOURCE + FOOD_GROUP + SORT + MAX_ROWS + BEGINNING_ROW;

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.cancelAll(TAG);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] dropList;
                        try {
                            JSONObject result = new JSONObject(response).getJSONObject("list");
                            int maxItems = result.getInt("end");
                            JSONArray resultList = result.getJSONArray("item");

                            // TODO: filter this list. Apparently it is showing non necessary groups.
                            dropList = new String[maxItems];
                            for (int i = 0; i < maxItems; i++) {
                                dropList[i] = resultList.getJSONObject(i).getString("name");
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context , android.R.layout.simple_dropdown_item_1line, dropList);
                            textView.setAdapter(adapter);
                            if (!textView.isPopupShowing()) {
                                textView.showDropDown();
                            }

                            lastNameQueried = result.getString("q");

                        } catch (JSONException e) {
                            textView.setAdapter(null);
                            lastNameQueried = "";
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: if the user keeps typing this message will also keep showing
                        Toast.makeText(context, "Food source is not responding (USDA API)", Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setTag(TAG);

        // executing the request (adding to queue)
        queue.add(stringRequest);

    }

}
