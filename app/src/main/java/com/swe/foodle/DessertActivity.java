package com.swe.foodle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.List;

/**
 * Displays dessert-related recipes.
 */
public class DessertActivity extends AppCompatActivity {

    /**
     * Creates the dessert activity display page.
     * @param savedInstanceState the saved state of DessertActivity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealcategorization);
        setUpSearch();
    }

    /**
     * Configures the search for dessert recipes.
     */
    private void setUpSearch(){
        final MealCategorySearch search = MealCategorySearch.getInstance(this);
        search.setMealCategoryType(MealCategoryType.Dessert);
        final Context context = this;
        search.sendRequest(new ServerCallback() {
            @Override
            public JSONObject onSuccess(JSONObject result) {
                search.setResult(result);
                System.out.println("Search Results: " + search.getResult());

                List<Recipe> recipes = search.parseResults(search.getResult());
                ListView listView = findViewById(R.id.recipeListView);
                search.displayRecipes(recipes, listView, context);
                return result;
            }
        });
    }
}
