package com.example.BlazianApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Enterpin extends AppCompatActivity {

    EditText editTextTextPassword3;
    Button button;

    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpin);

        SharedPreferences settings = getSharedPreferences("PRESS",0);
        pin = settings.getString("pin","");

        editTextTextPassword3 = (EditText) findViewById(R.id.editTextTextPassword3);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String text = editTextTextPassword3.getText().toString();

                if(text.equals(pin))
                {
                    Intent intent = new Intent(getApplicationContext(), Transaction_RecyclerViewAdapter.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(Enterpin.this,"Wrong pin. Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}