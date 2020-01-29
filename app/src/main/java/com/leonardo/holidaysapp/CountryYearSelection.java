package com.leonardo.holidaysapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.sephiroth.android.library.numberpicker.NumberPicker;

public class CountryYearSelection extends AppCompatActivity {
    EditText countryText;
    MaterialButton button;
    String country;
    int year;
    NumberPicker materialNumberPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        materialNumberPicker = findViewById(R.id.numberPicker);
        countryText = findViewById(R.id.editText2);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = countryText.getText().toString();
                year = materialNumberPicker.getProgress();
                Intent intent = new Intent(getApplicationContext(), ShowHolidays.class);
                intent.putExtra("country", country);
                intent.putExtra("year", year);
                startActivity(intent);
            }
        });
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        materialNumberPicker.setProgress(Integer.parseInt(df.format(d)));
    }
}
