package com.swe.foodle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Displays the help page with user app instructions.
 */
public class HelpActivity extends AppCompatActivity {
    /**
     * Creates the help activity display page.
     * @param savedInstanceState the saved state of HelpActivity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
}
