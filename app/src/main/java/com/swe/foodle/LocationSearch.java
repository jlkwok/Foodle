package com.swe.foodle;

import android.content.Context;

/**
 * The location recipes search functionality of the app. Returns recipes based on the cuisine closely associated with the user's location.
 */
public class LocationSearch extends Search {
    // stores the background info of where to display data to the user
    private Context context;
    // stores the cuisine type to search for
    private CuisineType cuisineType;
    // stores the only instance of location search
    private static LocationSearch instance;

    /**
     * @return the cuisine type to search for
     */
    public CuisineType getCuisineType() {
        return cuisineType;
    }

    /**
     * Sets the cuisine type to be the one specified.
     * @param cuisineType
     */
    public void setCuisineType(CuisineType cuisineType){
        this.cuisineType = cuisineType;
    }

    /**
     * @return the context (the background info to display info to)
     */
    public Context getContext() {
        return context;
    }

    /**
     * @param context the background info to display info to
     * @return the single instance of LocationSearch
     */
    public static LocationSearch getInstance(Context context){
        if (instance == null){
            instance = new LocationSearch(context);
        }
        return instance;
    }

    /**
     * Creates a LocationSearch with a specified context.
     * @note default cuisine type is American
     * @param context the background info to display info to
     */
    private LocationSearch(Context context){
        this.context = context;
        this.cuisineType = CuisineType.American;
    }

    /**
     * Sends the customized api request with cuisine specified.
     * @param callback the object that will retrieve the server result
     * @return String the request sent to the api
     */
    public String sendRequest(final ServerCallback callback){
        String cuisine = splitCamelCase(getCuisineType().name());
        String url = String.format("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=%s", cuisine.toLowerCase());
        return super.sendRequest(getContext(), url, callback);
    }

    /**
     * Splits the given string based on capital letters.
     * @param s the camel-case string to be split
     * @return the split string
     */
    private String splitCamelCase(String s) {
        // Ex: CamelCase -> Camel Case
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
