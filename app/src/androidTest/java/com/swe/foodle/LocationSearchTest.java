package com.swe.foodle;

import android.content.Context;
import android.location.Location;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests methods in LocationSearch.
 */
public class LocationSearchTest {
    private Context context = InstrumentationRegistry.getContext();
    private LocationSearch locationSearch = LocationSearch.getInstance(context);
    /**
     * Tests that the correct cuisine type is returned.
     */
    @Test
    public void getSetCuisineType() {
        locationSearch.setCuisineType(CuisineType.Chinese);
        assertEquals(CuisineType.Chinese, locationSearch.getCuisineType());
    }

    /**
     * Tests that the same context given to create the LocationSearch is returned.
     */
    @Test
    public void getContext() {
        assertEquals(context, locationSearch.getContext());
    }

    /**
     * Tests that only one instance of LocationSearch is created.
     */
    @Test
    public void getInstance() {
        Context context2 = InstrumentationRegistry.getContext();
        assertEquals(locationSearch, LocationSearch.getInstance(context2));
    }
}