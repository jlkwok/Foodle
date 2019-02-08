package com.swe.foodle;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the enums of CuisineType
 */
public class CuisineTypeTest {
    // stores the cuisine names Foodle supports
    private List<String> cuisineNames = new ArrayList<String>();
    // adds cuisine names that Foodle supports
    @Before
    public void setUp() throws Exception {
        cuisineNames.add("African");
        cuisineNames.add("American");
        cuisineNames.add("Australian");
        cuisineNames.add("British");
        cuisineNames.add("Cajun");
        cuisineNames.add("Caribbean");
        cuisineNames.add("Chinese");
        cuisineNames.add("EasternEuropean");
        cuisineNames.add("French");
        cuisineNames.add("German");
        cuisineNames.add("Greek");
        cuisineNames.add("Indian");
        cuisineNames.add("Irish");
        cuisineNames.add("Italian");
        cuisineNames.add("Japanese");
        cuisineNames.add("Korean");
        cuisineNames.add("LatinAmerican");
        cuisineNames.add("Mexican");
        cuisineNames.add("MiddleEastern");
        cuisineNames.add("Nordic");
        cuisineNames.add("Spanish");
        cuisineNames.add("Thai");
        cuisineNames.add("Vietnamese");
    }

    /**
     * Ensures that Foodle supports cuisine types listed in the test.
     */
    @Test
    public void checkNames(){
        int index = 0;
        for(CuisineType cuisineType : CuisineType.values()){
            assertEquals(cuisineNames.get(index), cuisineType.name());
            index++;
        }
    }
}