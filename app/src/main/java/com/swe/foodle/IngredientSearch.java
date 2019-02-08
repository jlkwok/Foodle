package com.swe.foodle;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The search by ingredient functionality of the app. Returns recipes based on the ingredients provided.
 */
public class IngredientSearch extends Search {
    // stores the ingredients for search
    private List<Ingredient> ingredientsList;
    // stores the context used for app display
    private Context context;
    // ID of recipes returned from search
    private List<String> recipeIds;

    /**
     * Creates an IngredientSearch with a context and list of ingredients.
     * @param context The context of the view of the search
     * @param ingredientsList  The list of ingredients to search
     */
    public IngredientSearch(Context context, List<Ingredient> ingredientsList){
        super();
        this.ingredientsList = ingredientsList;
        this.context = context;
        recipeIds = new ArrayList<>();
    }

    /**
     * Creates a list of ids from a JSON object containing a JSONarray of IDs.
     * @param ids A json object containing a json array of the ids
     * @throws JSONException error reading data from JSON data
     */
    public void setRecipeIds(JSONObject ids) throws JSONException {
        JSONArray idArray = ids.getJSONArray("ids");
        for(int i = 0; i < idArray.length(); i++){
            recipeIds.add(idArray.getJSONObject(i).getString("id"));
        }
        System.out.println(recipeIds);
    }

    /**
     * Sends a request to return a list of recipes, id only
     * @param callback the object that will retrieve the server result
     * @return String the request sent to the api
     */
    public String sendRequest(final ServerCallback callback){
        String initUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=";

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < ingredientsList.size(); i++){
            sb.append(ingredientsList.get(i).getName());
            sb.append(",");
        }
        String ingredientUrl = null;
        try {
            ingredientUrl = URLEncoder.encode(sb.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String endUrl = "&limitLicense=false&number=20&ranking=1";
        String url = String.format("%s%s%s", initUrl, ingredientUrl, endUrl);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Response: " + response);

                try {
                    callback.onSuccess(new JSONObject("{ \"ids\":" + response + "}"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR: " + error);
                if (error != null && error.networkResponse != null && error.networkResponse.statusCode == 400){
                    Toast.makeText(context, "No recipes found.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("X-Mashape-Key", "EjhrHcodDhmshsWxOp0kGjh8wVxTp1EyLFljsnsSdOHeA1AiNn");
                headers.put("accept", "application/json");

                return headers;
            }
        };
        // prevents sending the request twice
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        System.out.println("Request:" + request);
        System.out.println("Instance: " + MySingleton.getInstance(context));
        MySingleton.getInstance(context).getRequestQueue().add(request);
        return request.toString();
    }

    /**
     * Sends an api request to get recipes with all information from recipe ids received from sendRequest
     * @param callback the customized server call request
     * @return the result of the request
     */
    public String getRecipes(final ServerCallback callback){
        String initUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/informationBulk?ids=";

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < recipeIds.size(); i++){
            sb.append(recipeIds.get(i));
            sb.append(",");
        }
        String ingredientUrl = null;
        try {
            ingredientUrl = URLEncoder.encode(sb.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String endUrl = "&includeNutrition=false";
        String url = String.format("%s%s%s", initUrl, ingredientUrl, endUrl);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Response: " + response);

                try {
                    setResult(callback.onSuccess(new JSONObject("{ \"recipes\":" + response + "}")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR: " + error);
                if (error != null && error.networkResponse != null && error.networkResponse.statusCode == 400){
                    Toast.makeText(context, "No recipes found.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("X-Mashape-Key", "EjhrHcodDhmshsWxOp0kGjh8wVxTp1EyLFljsnsSdOHeA1AiNn");
                headers.put("accept", "application/json");

                return headers;
            }
        };
        // prevents sending the request twice
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        System.out.println("Request:" + request);
        System.out.println("Instance: " + MySingleton.getInstance(context));
        MySingleton.getInstance(context).getRequestQueue().add(request);
        return request.toString();
    }

}
