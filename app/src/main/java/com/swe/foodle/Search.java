package com.swe.foodle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Sends a query based on a customized request and generates the resulting list of recipes.
 */
public abstract class Search {
    // stores the result of the request
    private JSONObject result;
    // stores the recipes returned from the request
    private List<Recipe> recipes;

    /**
     * Sends the customized request to the server with the callback specified.
     * @param callback the object that will retrieve the server result
     */
    public abstract String sendRequest(ServerCallback callback);

    /**
     * @return the recipes of the returned parsed result
     */
    public List<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Sets the recipes of the returned result
     * @param recipes the returned parsed api call result
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * @return the result of the api call
     */
    public JSONObject getResult() {
        return result;
    }

    /**
     * Sets the result of the api call.
     * @param result the return of the api call
     */
    public void setResult(JSONObject result){
        this.result = result;
    }

    /**
     * Sends the api request to the server.
     * @param context the background info to display info to
     * @param url the api url request
     * @param callback the object that will retrieve the server result
     */
    public String sendRequest(final Context context, String url, final ServerCallback callback){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,  null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response: " + response);
                setResult(callback.onSuccess(response));
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
     * Displays the recipes to the user on the starting screen.
     * @param recipes the recipes to be displayed
     * @param listView the list view to display the recipes to
     */
    void displayRecipes(List<Recipe> recipes, ListView listView, final Context context){
        // no recipes were found
        if (recipes == null){
            Toast.makeText(context, "No recipes found.", Toast.LENGTH_SHORT).show();
            return;
        }
        // converts recipes into list items
        ListAdapter listAdapter = new RecipeAdapter(context, recipes);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Recipe recipe = (Recipe) parent.getItemAtPosition(position);
                        // redirects the user via the recipe link
                        Uri uri = Uri.parse(recipe.getLink());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    }
                }
        );
        MainActivity.espressoTestIdlingResource.decrement();
    }

    /**
     * Parses the result of the api call to obtain pertinent recipe information.
     * @param result the resulting JSON object of the api call
     * @return the recipes parsed from the result
     */
    public List<Recipe> parseResults(JSONObject result){
        try {
            JSONArray resultArray = result.getJSONArray("recipes");
            ArrayList<Recipe> recipes = new ArrayList<Recipe>();

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject recipe = resultArray.getJSONObject(i);
                String name = recipe.getString("title");
                String link = recipe.getString("sourceUrl");
                String picture = recipe.getString("image");

                recipes.add(new Recipe(name, link, picture));
            }
            setRecipes(recipes);
            return recipes;
        }
        catch (JSONException e){
            //todo handle error
        }
        return null;
    }

}
