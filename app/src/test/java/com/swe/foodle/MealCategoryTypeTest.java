package com.swe.foodle;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the enums of MealCategoryType
 */
public class MealCategoryTypeTest {
    // stores the meal categories Foodle supports
    private List<String> mealCategoryTypes = new ArrayList<String>();
    // adds meal categories Foodle supports
    @Before
    public void setUp() throws Exception {
        mealCategoryTypes.add("Breakfast");
        mealCategoryTypes.add("Lunch");
        mealCategoryTypes.add("Dinner");
        mealCategoryTypes.add("Dessert");
    }

    /**
     * Ensures that Foodle supports meal categories of breakfast, lunch, dinner, and dessert.
     */
    @Test
    public void checkMealCategories(){
        int index = 0;
        for(MealCategoryType mealCategoryType : MealCategoryType.values()){
            assertEquals(mealCategoryTypes.get(index), mealCategoryType.name());
            index++;
        }
    }
}