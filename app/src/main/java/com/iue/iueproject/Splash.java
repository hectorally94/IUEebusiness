package com.iue.iueproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int SPLASH_TIME_OUT = 5000;
        setContentView(R.layout.activity_splash);

        // now we create the handler : what will count the time in my device
        //post delayed checks for a timing before executing a function
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //dictate where to move next
                Intent intent = new Intent(Splash.this, Aftersplash.class);
                startActivity(intent);
            }

        },SPLASH_TIME_OUT);
    }
}