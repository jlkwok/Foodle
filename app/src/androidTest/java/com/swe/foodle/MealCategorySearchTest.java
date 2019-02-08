package com.swe.foodle;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests methods in MealCategorySearch.
 */
public class MealCategorySearchTest {
    private Context context = InstrumentationRegistry.getContext();
    private MealCategorySearch mealCategorySearch = MealCategorySearch.getInstance(context);

    /**
     * Tests that the correct meal category type is returned.
     */
    @Test
    public void getSetMealCategoryType() {
        mealCategorySearch.setMealCategoryType(MealCategoryType.Dessert);
        assertEquals(MealCategoryType.Dessert, mealCategorySearch.getMealCategoryType());
    }

    /**
     * Tests that the same context given to create the MealCategorySearch is returned.
     */
    @Test
    public void getContext() {
        assertEquals(context, mealCategorySearch.getContext());
    }

    /**
     * Tests that only one instance of MealCategorySearch is created.
     */
    @Test
    public void getInstance() {
        Context context2 = InstrumentationRegistry.getContext();
        assertEquals(mealCategorySearch, MealCategorySearch.getInstance(context2));
    }
}