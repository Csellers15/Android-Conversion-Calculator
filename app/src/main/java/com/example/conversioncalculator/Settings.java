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
import java.util.ArrayList;
import java.util.Arrays;


public class Settings extends AppCompatActivity {

    Spinner fromSpinner;
    Spinner toSpinner;

    FloatingActionButton floatingActionButton;

    TextView fromUnitTextSettings;
    TextView toUnitTextSettings;

    ArrayList<String> lengthList;
    ArrayList<String> volumeList;
    ArrayAdapter<String> adapter;

    static final String VOLUME = "VOLUME";
    static final String LENGTH = "LENGTH";

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
        lengthList = new ArrayList<>(Arrays.asList("Yards", "Meters", "Miles"));
        volumeList = new ArrayList<>(Arrays.asList("Liters", "Gallons", "Quarts"));

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
            case VOLUME:
                adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, volumeList);
                fromTextSel = volumeList.indexOf(initialToTextUnit);
                toTextSel = volumeList.indexOf(initialFromTextUnit);
                break;
            case LENGTH:
                adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, lengthList);
                fromTextSel = lengthList.indexOf(initialToTextUnit);
                toTextSel = lengthList.indexOf(initialFromTextUnit);
                break;
        }

        fromSpinner.setAdapter(adapter);
        fromSpinner.setSelection(fromTextSel);

        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(toTextSel);

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromSpinnerSelected = (String) adapterView.getItemAtPosition(i);
                toUnitTextSettings.setText(fromSpinnerSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toSpinnerSelected = (String) adapterView.getItemAtPosition(i);
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