package com.example.conversioncalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    //Buttons
    public Button clearBtn;
    public Button calculateBtn;
    public Button modeBtn;

    //Text Fields
    public EditText fromValue;
    public EditText toValue;

    //Labels
    public TextView toLabel;
    public TextView fromLabel;


    //Units
    public UnitsConverter.LengthUnits fromLength = UnitsConverter.LengthUnits.Yards;
    public UnitsConverter.LengthUnits toLength = UnitsConverter.LengthUnits.Meters;

    public UnitsConverter.VolumeUnits fromVol = UnitsConverter.VolumeUnits.Liters;
    public UnitsConverter.VolumeUnits toVol = UnitsConverter.VolumeUnits.Gallons;

    //Mode
    public String mode = "length";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets labels
        fromLabel = findViewById(R.id.fromLabel);
        toLabel = findViewById(R.id.toLabel);

        //From Value Text Box
        fromValue = findViewById(R.id.FromValue);
        fromValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    toValue.setText("");
                    fromValue.setText("");
                }
            }
        });

        //To Value Text Box
        toValue = findViewById(R.id.toValue);
        toValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    toValue.getText().clear();
                    fromValue.getText().clear();
                }
            }
        });

        //Gets Buttons
        clearBtn = findViewById(R.id.Clear);
        calculateBtn = findViewById(R.id.Calculate);
        modeBtn = findViewById(R.id.Mode);

        //Sets Clear
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromValue.getText().clear();
                toValue.getText().clear();
            }
        });


        Intent payload = getIntent();
        try{
            fromLabel.setText(payload.getStringExtra("fromUnit"));
            toLabel.setText(payload.getStringExtra("toUnit"));
        } catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }


        //Calculate
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode == "length"){
                    if(!fromValue.getText().toString().equals("")){
                        Double fromVal = Double.parseDouble(fromValue.getText().toString());
                        Double conv = UnitsConverter.convert(fromVal, fromLength, toLength);
                        toValue.setText(conv.toString());
                    } else if (!toValue.getText().toString().equals("")){
                        Double toVal = Double.parseDouble(toValue.getText().toString());
                        Double conv = UnitsConverter.convert(toVal, toLength, fromLength);
                        fromValue.setText(conv.toString());
                    } else {
                        fromValue.setText("0");
                        toValue.setText("0");

                    }
                } else {
                    if(!fromValue.getText().toString().equals("")){
                        Double fromVal = Double.parseDouble(fromValue.getText().toString());
                        Double conv = UnitsConverter.convert(fromVal, fromVol, toVol);
                        toValue.setText(conv.toString());
                    } else if (!toValue.getText().toString().equals("")){
                        Double toVal = Double.parseDouble(toValue.getText().toString());
                        Double conv = UnitsConverter.convert(toVal, toVol, fromVol);
                        fromValue.setText(conv.toString());
                    } else {
                        fromValue.setText("0");
                        toValue.setText("0");
                    }
                }
            }
        });


        //Changes Mode
        modeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode == "length"){
                    fromLabel.setText(fromVol.toString());
                    toLabel.setText(toVol.toString());
                    mode = "volume";
                } else {
                    fromLabel.setText(fromLength.toString());
                    toLabel.setText(toLength.toString());
                    mode = "length";
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem settingsItem) {
        switch (settingsItem.getItemId()) {
            case R.id.settings:
                Intent switchToSettings = new Intent(MainActivity.this, Settings.class);
                switchToSettings.putExtra("fromLabel", fromLabel.getText());
                switchToSettings.putExtra("toLabel", toLabel.getText());
                switchToSettings.putExtra("mode", mode);

                startActivityForResult(switchToSettings, 1);
        }
        return super.onOptionsItemSelected(settingsItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            fromLabel.setText(data.getStringExtra("fromUnit"));
            toLabel.setText(data.getStringExtra("toUnit"));
        }

    }
}
