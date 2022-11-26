package com.example.BlazianApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SetPin extends AppCompatActivity {

    EditText editTextTextPassword1, editTextTextPassword2, SQ_answer;
    Spinner SQ_question;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpin);

        editTextTextPassword1 = findViewById(R.id.editTextTextPassword1);
        editTextTextPassword2 = findViewById(R.id.editTextTextPassword2);
        SQ_question = findViewById(R.id.userSpinner);
        SQ_answer = findViewById(R.id.editTextTextSQAnswer);
        button = findViewById(R.id.button);
        RecordFragment recordFragment = MainActivity.getInstance().recordFragment;
        button.setOnClickListener(view -> {
            String pin1 = editTextTextPassword1.getText().toString();
            String pin2 = editTextTextPassword2.getText().toString();
            String question = SQ_question.getSelectedItem().toString();
            String answer = SQ_answer.getText().toString();

            if(pin1.equals("") || pin2.equals(""))
            {
                Toast.makeText(SetPin.this,"No pin entered", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(pin1.equals(pin2))
                {
                    if(answer.equals("")){
                        Toast.makeText(SetPin.this,"Provide an answer.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    SharedPreferences settings = getSharedPreferences("PIN", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("SQ", question);
                    editor.putString("SQAnswer", answer);
                    editor.putString("Pin", pin1);
                    editor.apply();

                    startFragment(recordFragment);
                    finish();
                    }
                }
                else
                {
                    Toast.makeText(SetPin.this,"Pin doesn't match. please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startFragment(RecordFragment recordFragment){
        MainActivity.getInstance().replaceFragment(recordFragment, "fragRecord");
    }
}