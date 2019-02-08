package com.swe.foodle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The CalorieCounting page of the app. Sums up the total number of calories of the ingredients added.
 */
public class CalorieCountActivity extends AppCompatActivity {
    // stores the items to be added to the list view
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    // stores the converted list items to list view items
    CalorieCountAdapter adapter;
    // stores the CalorieCount object used for keeping track of calories
    static CalorieCount calorieCount = CalorieCount.getInstance();
    // stores the total calorie text view
    static TextView totalCaloriesTextView;

    /**
     * Creates the display and sets up the functionality of the CalorieCounting activity page.
     * @param savedInstanceState the saved state of CalorieCount
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caloriecount);

        adapter = new CalorieCountAdapter(this, ingredients);
        ListView foodListView = findViewById(R.id.ingredient_listview);
        foodListView.setAdapter(adapter);

        calorieCount.setIngredients(ingredients);
        calorieCount.getIngredients().clear();
        calorieCount.setTotalCalories(0.0);

        totalCaloriesTextView = findViewById(R.id.totalCalories);
        String totalCals = "Total Calories: 0.00";
        totalCaloriesTextView.setText(totalCals);
    }

    /**
     * Updates the total calories display based on new information.
     */
    public static void updateTotalCaloriesText(){
        // display the new total calorie count rounded to two decimal places
        String totalCalories = String.format("Total Calories: %.2f", calorieCount.getTotalCalories());
        totalCaloriesTextView.setText(totalCalories);
    }

    /**
     * Adds items to the ingredient list view.
     * @param v the view to be modified with additional items
     */
    public void addItems(View v){
        TextView textView = findViewById(R.id.foodItemName);
        String foodItemName = textView.getText().toString();
        textView.setText(null);

        // make sure some ingredient has been entered
        if (!foodItemName.isEmpty()){
            Ingredient proposedIngredient = new Ingredient(foodItemName, 0, 0);
            // increase the quantity of repeated ingredient in the list by 1
            if (calorieCount.getIngredients().contains(proposedIngredient)){
                proposedIngredient = ingredients.get(ingredients.indexOf(proposedIngredient));
                int quantity = proposedIngredient.getQuantity();
                calorieCount.changeIngredientQuantity(proposedIngredient, ++quantity);
                adapter.notifyDataSetChanged();
                updateTotalCaloriesText();
            }
            else{
                addIngredientToListItems(foodItemName);
            }
        }
        else{
            Toast.makeText(this, "Please enter ingredient.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Adds the ingredient specified by the user to the ingredients list view and recomputes the
     * total calorie information.
     * @param query the user query containing the ingredient to be searched for
     */
    public void addIngredientToListItems(final String query){
        String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/parseIngredients?includeNutrition=true";
        final Context context = this;
        final ServerCallback callback = new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                try {
                    String name = result.getString("name");
                    JSONObject nutrition = result.getJSONObject("nutrition");
                    JSONArray nutrients = nutrition.getJSONArray("nutrients");
                    double calories = (Double)nutrients.getJSONObject(0).get("amount");
                    Ingredient ingredient = new Ingredient(name, calories, 1);
                    calorieCount.addIngredient(ingredient);
                    adapter.notifyDataSetChanged();
                    updateTotalCaloriesText();

                    return result;
                }
                catch(Exception e){
                    Toast.makeText(context, "No ingredient information found.", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                System.out.println("Response: " + response);
                JSONObject r = null;

                JsonObject g  = (new JsonParser()).parse(response.substring(1, response.length()-1)).getAsJsonObject();
                try {
                    r = new JSONObject(g.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callback.onSuccess(r);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR: " + error);
                if (error != null && error.networkResponse != null && error.networkResponse.statusCode == 400){
                    Toast.makeText(context, "No ingredient information found.", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("X-Mashape-Key", "EjhrHcodDhmshsWxOp0kGjh8wVxTp1EyLFljsnsSdOHeA1AiNn");
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError{
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("ingredientList", query);
                params.put("servings", "1");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };

        // prevents sending the request twice
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        System.out.println("Request:" + request);
        try {
            System.out.println("Headers: " + request.getHeaders());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        MySingleton.getInstance(this).getRequestQueue().add(request);
    }
}
