package com.swe.foodle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the methods of Ingredient.
 */
public class IngredientTest {
    // stores an ingredient with quantity = 0
    private Ingredient zeroIngredient;
    // stores an ingredient with quantity = 1
    private Ingredient oneIngredient;
    // stores an ingredient with quantity = 2
    private Ingredient twoIngredient;

    /**
     * Sets up Ingredient test by initializing Ingredient test objects.
     */
    @Before
    public void setUp(){
        zeroIngredient = new Ingredient("carrot");
        oneIngredient = new Ingredient("carrot", 30);
        twoIngredient = new Ingredient("carrot", 30, 2);
    }

    /**
     * Tests that the ingredient's name is correctly returned.
     */
    @Test
    public void getName() {
        assertEquals("carrot", oneIngredient.getName());
    }

    /**
     * Tests that the ingredient's number of calories is correctly returned.
     */
    @Test
    public void getCalories() {
        assertTrue(30 == oneIngredient.getCalories());
        assertTrue(0 == zeroIngredient.getCalories());
    }

    /**
     * Tests that the ingredient's quantity is correctly returned.
     */
    @Test
    public void getQuantity() {
        assertEquals(2, twoIngredient.getQuantity());
        assertEquals(1, oneIngredient.getQuantity());
        assertTrue(1 == zeroIngredient.getQuantity());
    }

    /**
     * Tests that the ingredient's quantity can be set to a different amount.
     */
    @Test
    public void setQuantity() {
        oneIngredient.setQuantity(300);
        assertEquals(300, oneIngredient.getQuantity());
        twoIngredient.setQuantity(3);
        assertEquals(3, twoIngredient.getQuantity());
    }

    /**
     * Tests that an ingredient is equal to an exact same ingredient it's compared with.
     */
    @Test
    public void equals(){
        // ingredients are the same
        Ingredient sameIngredient = new Ingredient("carrot");
        assertTrue(sameIngredient.equals(zeroIngredient));

        // ingredients are not the same
        Ingredient differentIngredient = new Ingredient("banana");
        assertFalse(zeroIngredient.equals(differentIngredient));

        // ingredient is compared with a nonIngredient
        Integer nonIngredient = new Integer(2);
        assertFalse(zeroIngredient.equals(nonIngredient));
    }
}