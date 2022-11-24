package com.example.BlazianApp;

import androidx.appcompat.app.AppCompatActivity;

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

        SharedPreferences settings = getSharedPreferences("PRESS",0);
        pin = settings.getString("pin","");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(pin.equals("")) //if it's a new user
                {
                    Intent intent = new Intent(getApplicationContext(), Setpin.class);
                    startActivity(intent);
                    finish();
                }

                else //if pin already set by user
                {
                    Intent intent = new Intent(getApplicationContext(), Enterpin.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}