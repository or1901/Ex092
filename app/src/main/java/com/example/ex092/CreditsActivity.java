package com.example.ex092;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author Ori Roitzaid <or1901 @ bs.amalnet.k12.il>
 * @version	1
 * @since 22/9/2023
 * Credits activity:
 * Displays the credits of the application.
 */
public class CreditsActivity extends AppCompatActivity {
    Intent gi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        gi = getIntent();
    }

    /**
     * This function moves the user back to the results activity.
     * <p>
     *
     * @param view The view object of the clicked button.
     */
    public void backToResults(View view) {
        finish();
    }
}