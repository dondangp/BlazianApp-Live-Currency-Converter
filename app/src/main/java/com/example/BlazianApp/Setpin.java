package com.example.BlazianApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Setpin extends AppCompatActivity {

    EditText editTextTextPassword1, editTextTextPassword2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpin);

        editTextTextPassword1 = (EditText) findViewById(R.id.editTextTextPassword1);
        editTextTextPassword2 = (EditText) findViewById(R.id.editTextTextPassword2);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String pin1 = editTextTextPassword1.getText().toString();
                String pin2 = editTextTextPassword2.getText().toString();

                if(pin1.equals("") || pin2.equals(""))
                {
                    Toast.makeText(Setpin.this,"No pin entered", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pin1.equals(pin2))
                    {
                        SharedPreferences settings = getSharedPreferences("PRESS",0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("Pin", pin1);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), Transaction_RecyclerViewAdapter.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Setpin.this,"Pin doesn't match. please try again",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}