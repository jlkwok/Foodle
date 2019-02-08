package com.swe.foodle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the methods in UserLocation.
 */
public class UserLocationTest {
    // stores the single instance of UserLocation
    private UserLocation userLocation = UserLocation.getInstance();

    /**
     * Tests that only one instance of UserLocation is ever returned.
     */
    @Test
    public void getInstance() {
        assertEquals(userLocation, UserLocation.getInstance());
    }

    /**
     * Tests that the country code set is the one returned.
     */
    @Test
    public void getSetCountryCode() {
        String countryCode = "US";
        userLocation.setCountryCode(countryCode);
        assertEquals(countryCode, userLocation.getCountryCode());
    }

    /**
     * Tests that the correct cuisine type code set is returned.
     * Default cuisine type is defined as Italian.
     */
    @Test
    public void getCuisineType() {
        // default cuisine type
        assertEquals(CuisineType.Italian, userLocation.getCuisineType());

        // US is defined as American cuisine type
        userLocation.setCountryCode("US");
        assertEquals(CuisineType.American, userLocation.getCuisineType());
    }
}