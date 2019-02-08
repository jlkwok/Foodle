package com.swe.foodle;

import android.content.Context;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * Forms and sends the api requests related to the meal categories of breakfast, lunch, dinner, and dessert.
 */
public class MealCategorySearch extends Search {
    // stores the meal category type
    private MealCategoryType mealCategoryType;
    // stores the background info of where to display data to the user
    private Context context;
    // stores the only instance of meal category search
    private static MealCategorySearch instance;

    /**
     * @return the meal category type to search for
     */
    public MealCategoryType getMealCategoryType() {
        return mealCategoryType;
    }

    /**
     * Sets the meal category type to that specified.
     * @param mealCategoryType the meal category type to be specified
     */
    public void setMealCategoryType(MealCategoryType mealCategoryType) {
        this.mealCategoryType = mealCategoryType;
    }

    /**
     * @return the context (the background info to display info to)
     */
    public Context getContext() {
        return context;
    }

    /**
     * Creates a MealCategorySearch with a specified context.
     * @note default meal category type is Breakfast
     * @param context the background info to display info to
     */
    private MealCategorySearch(Context context) {
        this.mealCategoryType = MealCategoryType.Breakfast;
        this.context = context;
    }

    /**
     * @param context the background info to display info to
     * @return the single instance of MealCategorySearch
     */
    public static MealCategorySearch getInstance(Context context){
        if (instance == null){
            instance = new MealCategorySearch(context);
        }
        return instance;
    }

    /**
     * Sends the customized api request with a configured meal category type.
     * @param callback the customized server call request
     * @return the result of the request
     */
    public String sendRequest(final ServerCallback callback){
        String url = String.format("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=%s", getMealCategoryType().name().toLowerCase());
        return super.sendRequest(getContext(), url, callback);
    }
}
