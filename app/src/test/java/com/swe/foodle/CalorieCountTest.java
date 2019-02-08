package com.swe.foodle;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests the methods of CalorieCount.
 */
public class CalorieCountTest {
    private CalorieCount calorieCount;

    /**
     * Sets up the test of CalorieCount.
     */
    @Before
    public void setUp(){
        calorieCount = CalorieCount.getInstance();
        calorieCount.setTotalCalories(0.0);
    }

    /**
     * Tests that the total number of calories is correctly returned.
     */
    @Test
    public void getSetTotalCalories() {
        calorieCount.setTotalCalories(35);
        assertTrue(35 == calorieCount.getTotalCalories());
    }

    /**
     * Tests that the correct list of ingredients set is correctly returned.
     */
    @Test
    public void getSetIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        calorieCount.setIngredients(ingredients);
        assertTrue(calorieCount.getIngredients().equals(ingredients));
    }

    /**
     * Tests that the ingredient is added to the list correctly.
     */
    @Test
    public void addIngredient() {
        // ingredient is not in the ingredients list
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient ingredient = new Ingredient("carrot", 50);
        calorieCount.setIngredients(ingredients);
        assertTrue(calorieCount.addIngredient(ingredient));
        assertTrue(!calorieCount.getIngredients().isEmpty());

        // ingredient has already been added
        Ingredient repeatedIngredient = new Ingredient("carrot", 50);
        assertFalse(calorieCount.addIngredient(repeatedIngredient));
    }

    /**
     * Tests that the ingredient is removed from the list correctly.
     */
    @Test
    public void removeIngredient() {
        // remove ingredient from the contained list
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        calorieCount.setIngredients(ingredients);
        Ingredient ingredient = new Ingredient("carrot", 50);
        calorieCount.addIngredient(ingredient);
        assertTrue(calorieCount.removeIngredient(ingredient));
        assertTrue(calorieCount.getIngredients().isEmpty());

        assertTrue(calorieCount.getTotalCalories() == 0.0);
        // ingredient to be removed is not in the list
        assertFalse(calorieCount.removeIngredient(ingredient));
        assertTrue(calorieCount.getTotalCalories() == 0.0);
    }

    /**
     * Tests that an ingredient's quantity is changed to the amount specified and
     * that the total number of calories reflects the change.
     */
    @Test
    public void changeIngredientQuantity(){
        // remove ingredient from the contained list
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        calorieCount.setIngredients(ingredients);
        Ingredient ingredient = new Ingredient("carrot", 50);
        calorieCount.addIngredient(ingredient);
        calorieCount.changeIngredientQuantity(ingredient, 3);
        assertTrue(ingredient.getQuantity() == 3);
        assertTrue(calorieCount.getTotalCalories() == 150);
    }

    /**
     * Tests that the total number of calories is computed correctly.
     */
    @Test
    public void calculateTotalCalories() {
        // form list of ingredients to sum the total calories of
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient carrot = new Ingredient("carrot", 50);
        calorieCount.setIngredients(ingredients);
        calorieCount.addIngredient(carrot);
        Ingredient banana = new Ingredient("banana", 80);
        calorieCount.addIngredient(banana);
        calorieCount.calculateTotalCalories(ingredients);
        assertTrue(130.0 == calorieCount.getTotalCalories());
    }
}