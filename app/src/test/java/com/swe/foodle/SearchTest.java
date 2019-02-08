package com.swe.foodle;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests the methods of Search.
 */
public class SearchTest {
    // stores a type of search
    private KeywordSearch keywordSearch;
    // stores recipes
    private ArrayList<Recipe> recipes = new ArrayList<>();
    // stores a query's return object
    private JSONObject jsonObject;

    /**
     * Sets up Ingredient test by initializing Ingredient test objects.
     * @throws JSONException exception thrown if invalid json object
     */
    @Before
    public void setUp() throws JSONException {
        keywordSearch = new KeywordSearch();
        String name = "tiramisu";
        String link = "abc";
        String picture = "def";
        Recipe recipe = new Recipe(name, link, picture);
        recipes.add(recipe);
        keywordSearch.setRecipes(recipes);

        jsonObject = new JSONObject();
        jsonObject.put("hello", 1);
        keywordSearch.setResult(jsonObject);
    }

    /**
     * Tests that the search's recipes list is correctly returned.
     */
    @Test
    public void getRecipes() {
        assertEquals(recipes, keywordSearch.getRecipes());
    }

    /**
     * Tests that the setting the search's recipes results in the same recipes returned.
     */
    @Test
    public void setRecipes() {
        String name = "pizza";
        String link = "abc";
        String picture = "def";
        Recipe recipe = new Recipe(name, link, picture);
        ArrayList<Recipe> newRecipes = new ArrayList<>();
        newRecipes.add(recipe);

        keywordSearch.setRecipes(newRecipes);
        assertEquals(newRecipes, keywordSearch.getRecipes());
        assertNotEquals(recipes, keywordSearch.getRecipes());
    }

    /**
     * Tests that the search's json object is correctly returned.
     */
    @Test
    public void getResult() {
        assertEquals(jsonObject, keywordSearch.getResult());
    }

    /**
     * Tests that the search's json object is set and returned correctly.
     * @throws JSONException the exception thrown by incorrectly forming the JSONObject
     */
    @Test
    public void setResult() throws JSONException {
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("bye", 2);
        keywordSearch.setResult(jsonObject2);

        assertEquals(jsonObject2, keywordSearch.getResult());
        assertNotEquals(jsonObject, keywordSearch.getResult());
    }
}