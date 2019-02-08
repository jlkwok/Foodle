package com.swe.foodle;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The search by keyword functionality of the app. Returns recipes based on the recipe name.
 */
public class KeywordSearch extends Search {
    // stores the recipe name to search for
    private String recipeName;
    // stores the background info of where to display data to the user
    private Context context;

    /**
     * Creates a default KeywordSearch.
     */
    public KeywordSearch() {
        recipeName = " ";
    }

    /**
     * Creates a KeywordSearch with a context and recipe name.
     * @param context the background info to display info to
     * @param recipeName the recipe name to search for
     */
    public KeywordSearch(Context context, String recipeName) {
        this.context = context;
        this.recipeName = recipeName;
    }

    /**
     * @return the recipe name
     */
    public String getRecipeName() {
        return recipeName;
    }

    /**
     * Sends the customized api request with recipe name specified.
     * @param callback the customized server call request
     * @return the result of the request
     */
    public String sendRequest(final ServerCallback callback) {
        String initialUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=";
        String url = String.format("%s%s", initialUrl, getRecipeName().replace(' ', '+').toLowerCase());

        return super.sendRequest(context, url, callback);
    }
}
