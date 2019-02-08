package com.swe.foodle;

import android.content.Context;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Testing the Singleton class functionality.
 */
@RunWith(AndroidJUnit4.class)
public class MySingletonTest {
    private Context mCtx;
    private MySingleton mInstance;
    private RequestQueue mRequestQueue;
    @Rule
    public ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<>(MainActivity.class);

    /**
     * Sets up the tests of MySingleton.
     */
    @Before
    public void setUp() {
        mCtx = mActivity.getActivity().getApplicationContext();
    }

    /**
     * Tests whether MySingleton returns the same instance each time.
     */
    @Test
    public void getInstance() {
        assertNull(mInstance);
        mInstance = MySingleton.getInstance(mCtx);
        MySingleton secondInstance = MySingleton.getInstance(mCtx);
        // ensures the same object is returned each time
        assertEquals(mInstance, secondInstance);
        assertNotNull(mInstance);
    }

    /**
     * Tests that the same request queue is returned each time.
     */
    @Test
    public void getRequestQueue() {
        assertNull(mRequestQueue);
        mInstance = MySingleton.getInstance(mCtx);
        mRequestQueue = mInstance.getRequestQueue();
        assertNotNull(mRequestQueue);
        RequestQueue requestQueue = mInstance.getRequestQueue();
        // ensures the same object is returned each time
        assertEquals(mRequestQueue, requestQueue);
    }

}
