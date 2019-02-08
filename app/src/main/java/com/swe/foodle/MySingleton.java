package com.swe.foodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Creates a single instance of the RequestQueue used to process requests faster.
 */
public class MySingleton {
    // stores the only instance of the MySingleton
    private static MySingleton mInstance;
    // stores the request queue used to process requests
    private RequestQueue mRequestQueue;
    // stores the background info associated with the page requesting info.
    private static Context mCtx;

    /**
     * Creates a MySingleton with a context.
     * @param context the background info to display info to
     */
    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * @param context the background info to display info to
     * @return the single instance of MySingleton
     */
    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    /**
     * @return the request queue utilized to process requests
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
}
