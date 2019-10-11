package com.example.conversioncalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.Arrays;


public class Settings extends AppCompatActivity {

    //Spinners
    Spinner fromSpinner;
    Spinner toSpinner;

    //"Save Button"
    FloatingActionButton floatingActionButton;

    //Labels
    TextView fromUnit;
    TextView toUnit;

    //Adapters
    ArrayAdapter<UnitsConverter.LengthUnits> lengthAdapter;
    ArrayAdapter<UnitsConverter.VolumeUnits> volumeAdapter;

    //Various Variables needed
    String mode;
    String toSpinnerSelected;
    String fromSpinnerSelected;
    String initFromUnit;
    String initToUnit;
    int toTextSel;
    int fromTextSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add lists for length and volume
        List<UnitsConverter.LengthUnits> lengthList = Arrays.asList(UnitsConverter.LengthUnits.values());
        List<UnitsConverter.VolumeUnits> volumeList = Arrays.asList(UnitsConverter.VolumeUnits.values());

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        toUnit = findViewById(R.id.toUnit);
        fromUnit = findViewById(R.id.fromUnit);

        //Sets the mode and texts from the intent
        Intent payload = getIntent();
        try{
            mode = payload.getStringExtra("mode");
            initFromUnit = payload.getStringExtra("fromLabel");
            initToUnit = payload.getStringExtra("toLabel");
            fromUnit.setText(initFromUnit);
            toUnit.setText(initToUnit);

        } catch(Exception e) {
            System.out.println("Error " + e.getMessage());
        }


        /****************************GOOD UNTIL HERE*********************************/
        /*
            TODO:
                When App starts its not showing units

             Fixed this morning.
                conversions arent right? but like, they should be so who knows
                cant do volume apparently? mode issue? ive been programming for how long? who knows?
                keyboard disappear on click, can do that but other issues took up all my time
                Doesnt load in correct units on settings page, because why would they?
         */

        if(mode.equals("volume")) {
            volumeAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, volumeList);
            fromTextSel = volumeList.indexOf(UnitsConverter.VolumeUnits.valueOf(initFromUnit));
            toTextSel = volumeList.indexOf(UnitsConverter.VolumeUnits.valueOf(initToUnit));

            fromSpinner.setAdapter(volumeAdapter);
            fromSpinner.setSelection(fromTextSel);

            toSpinner.setAdapter(volumeAdapter);
            toSpinner.setSelection(toTextSel);
        } else {
            lengthAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, lengthList);

            //fromTextSel = lengthList.indexOf(initToUnit);
            fromTextSel = lengthList.indexOf(UnitsConverter.LengthUnits.valueOf(initFromUnit));
            toTextSel = lengthList.indexOf(UnitsConverter.LengthUnits.valueOf(initToUnit));

            fromSpinner.setAdapter(lengthAdapter);
            fromSpinner.setSelection(fromTextSel);

            toSpinner.setAdapter(lengthAdapter);
            toSpinner.setSelection(toTextSel);
        }

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromSpinnerSelected =  adapterView.getItemAtPosition(i).toString();
                fromUnit.setText(fromSpinnerSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toSpinnerSelected = adapterView.getItemAtPosition(i).toString();
                toUnit.setText(toSpinnerSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        floatingActionButton.setOnClickListener(e -> {
            Intent switchToMain = new Intent(Settings.this, MainActivity.class);
            switchToMain.putExtra("fromUnit", fromUnit.getText());
            switchToMain.putExtra("toUnit", toUnit.getText());
            switchToMain.putExtra("mode", mode);

            setResult(1, switchToMain);
            finish();
        });

    }

}