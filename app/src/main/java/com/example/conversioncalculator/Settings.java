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

    String currentCalc;
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
        if (payload.hasExtra("currentCalc")) {
            currentCalc = payload.getStringExtra("currentCalc");
        }

        // Set text from intent
        if (payload.hasExtra("fromUnitTextSettings")) {
            initialFromTextUnit = payload.getStringExtra("fromUnitTextSettings");
            fromUnitTextSettings.setText(initialFromTextUnit);
        }
        if (payload.hasExtra("toUnitTextSettings")) {
            initialToTextUnit = payload.getStringExtra("toUnitTextSettings");
            toUnitTextSettings.setText(initialToTextUnit);
        }

        switch (currentCalc) {
            case "volume":
                volumeAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, volumeList);
                fromTextSel = volumeList.indexOf(initialToTextUnit);
                toTextSel = volumeList.indexOf(initialFromTextUnit);

                fromSpinner.setAdapter(volumeAdapter);
                fromSpinner.setSelection(fromTextSel);

                toSpinner.setAdapter(volumeAdapter);
                toSpinner.setSelection(toTextSel);
            case "length":
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
            switchToMain.putExtra("toUnitText", toUnitTextSettings.getText());
            switchToMain.putExtra("fromUnitText", fromUnitTextSettings.getText());
            switchToMain.putExtra("currentCalc", currentCalc);

            setResult(1, switchToMain);
            finish();
        });

    }

}