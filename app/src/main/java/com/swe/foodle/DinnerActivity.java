package com.swe.foodle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.List;

/**
 * Displays dinner-related recipes.
 */
public class DinnerActivity extends AppCompatActivity {
    /**
     * Creates the dinner activity display page.
     * @param savedInstanceState the saved state of DinnerActivity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealcategorization);
        setUpSearch();
    }

    /**
     * Configures the search for dinner recipes.
     */
    private void setUpSearch(){
        final MealCategorySearch search = MealCategorySearch.getInstance(this);
        search.setMealCategoryType(MealCategoryType.Dinner);
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
