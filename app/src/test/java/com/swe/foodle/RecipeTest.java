package com.swe.foodle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests methods in Recipe.
 */
public class RecipeTest {
    // stores the recipe used for testing getters
    private Recipe recipe;

    /**
     * Sets up variables used for each test.
     */
    @Before
    public void setUp(){
        String name = "tiramisu";
        String link = "abc";
        String picture = "def";
        recipe = new Recipe(name, link, picture);
    }

    /**
     * Tests that the correct recipe name is returned.
     */
    @Test
    public void getName() {
        assertEquals("tiramisu", recipe.getName());
    }

    /**
     * Tests that the correct recipe link is returned.
     */
    @Test
    public void getLink() {
        assertEquals("abc", recipe.getLink());
    }

    /**
     * Tests that the correct recipe picture is returned.
     */
    @Test
    public void getPicture() {
        assertEquals("def", recipe.getPicture());
    }

    /**
     * Tests that two Recipes will be marked the same.
     */
    @Test
    public void equals(){
        Recipe equalRecipe = new Recipe("tiramisu", "abc", "def");
        assertTrue(recipe.equals(equalRecipe));
        Recipe unequalRecipe = new Recipe("tiramisu", "ghi", "xyz");
        assertFalse(recipe.equals(unequalRecipe));

        // makes sure a Recipe compared to a nonRecipe is unequal
        assertFalse(recipe.equals("hello"));
    }
}