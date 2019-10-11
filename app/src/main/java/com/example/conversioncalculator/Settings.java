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

    Spinner fromSpinner;
    Spinner toSpinner;

    FloatingActionButton floatingActionButton;

    TextView fromUnitTextSettings;
    TextView toUnitTextSettings;

    ArrayAdapter<UnitsConverter.LengthUnits> lengthAdapter;
    ArrayAdapter<UnitsConverter.VolumeUnits> volumeAdapter;

    String mode;
    String toSpinnerSelected;
    String fromSpinnerSelected;
    String initialFromTextUnit;
    String initialToTextUnit;
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

        toUnitTextSettings = findViewById(R.id.fromUnitTextSettings);
        fromUnitTextSettings = findViewById(R.id.toUnitTextSettings);

        // Start intent
        Intent payload = getIntent();
        if (payload.hasExtra("mode")) {
            mode = payload.getStringExtra("mode");

        }

        // Set text from intent
        if (payload.hasExtra("fromLabel")) {
            initialFromTextUnit = payload.getStringExtra("fromLabel");
            fromUnitTextSettings.setText(initialFromTextUnit);
        }
        if (payload.hasExtra("toLabel")) {
            initialToTextUnit = payload.getStringExtra("toLabel");
            toUnitTextSettings.setText(initialToTextUnit);
        }


        /****************************GOOD UNTIL HERE*********************************/
        /*
            TODO:
                keyboard disappear on click
                cant do volume
                from changes to?
                to changes from?
                doesnt load in correct units on settings page
         */

        if(mode == "volume") {
            volumeAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, volumeList);
            fromTextSel = volumeList.indexOf(initialToTextUnit);
            toTextSel = volumeList.indexOf(initialFromTextUnit);

            fromSpinner.setAdapter(volumeAdapter);
            fromSpinner.setSelection(fromTextSel);

            toSpinner.setAdapter(volumeAdapter);
            toSpinner.setSelection(toTextSel);
        } else {
            lengthAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, lengthList);
            fromTextSel = lengthList.indexOf(initialToTextUnit);
            toTextSel = lengthList.indexOf(initialFromTextUnit);

            fromSpinner.setAdapter(lengthAdapter);
            fromSpinner.setSelection(fromTextSel);

            toSpinner.setAdapter(lengthAdapter);
            toSpinner.setSelection(toTextSel);
        }

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromSpinnerSelected =  adapterView.getItemAtPosition(i).toString();
                toUnitTextSettings.setText(fromSpinnerSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toSpinnerSelected = adapterView.getItemAtPosition(i).toString();
                fromUnitTextSettings.setText(toSpinnerSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        floatingActionButton.setOnClickListener(e -> {
            Intent switchToMain = new Intent(Settings.this, MainActivity.class);
            switchToMain.putExtra("fromUnitText", fromUnitTextSettings.getText());
            switchToMain.putExtra("toUnitText", toUnitTextSettings.getText());
            switchToMain.putExtra("mode", mode);

            setResult(1, switchToMain);
            finish();
        });

    }

}