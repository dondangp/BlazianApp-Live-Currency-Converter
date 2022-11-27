package com.example.BlazianApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences("PIN", Context.MODE_PRIVATE);
        pin = settings.getString("Pin","");

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent;
            if(pin.equals("")) //if it's a new user
            {
                intent = new Intent(getApplicationContext(), SetPin.class);
            }

            else //if pin already set by user
            {
                intent = new Intent(getApplicationContext(), EnterPin.class);
            }
            startActivity(intent);
            finish();
        }, 100);
    }
}