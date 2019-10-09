package com.example.conversioncalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public Button clearBtn;
    public Button calculateBtn;
    public Button modeBtn;


    public EditText fromValue;
    public EditText toValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gets Edit Text
        fromValue = (EditText) findViewById(R.id.FromValue);
        toValue = (EditText) findViewById(R.id.toValue);

        //Gets Buttons
        clearBtn = (Button) findViewById(R.id.Clear);
        calculateBtn = (Button) findViewById(R.id.Calculate);
        modeBtn = (Button) findViewById(R.id.Mode);


        //Sets Clear
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the first EditText empty
                fromValue.setText("");

                // Clear the second EditText
                toValue.getText().clear();
            }
        });


        //Calcuate
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //Changes Mode
        modeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

}
