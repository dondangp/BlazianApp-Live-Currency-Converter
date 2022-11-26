package com.example.BlazianApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class forgotPin extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpin);

        //vars
        SharedPreferences settings = getSharedPreferences("PIN", Context.MODE_PRIVATE);
        String question = settings.getString("SQ","");
        String answer = settings.getString("SQAnswer","");
        button = findViewById(R.id.button);
        TextView text = findViewById(R.id.userQuestion);


        //initial
        text.setText(question);

        //on button click
        button.setOnClickListener(view -> {
            EditText userAnswer = (EditText) findViewById(R.id.editTextTextPassword3);
            if(userAnswer.getText().toString().equals(answer)){
                Intent intent = new Intent(getApplicationContext(), SetPin.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this,"Wrong answer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}