package com.leonardo.holidaysapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowHolidays extends AppCompatActivity {

    TextView yearText;
    TextView countryText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_holidays);

        yearText = findViewById(R.id.textView);
        countryText = findViewById(R.id.textView2);

        Intent intent = getIntent();
        String country = intent.getStringExtra("country");
        int year = intent.getIntExtra("year", 0);
        yearText.setText(Integer.toString(year));
        countryText.setText(country);

    }
}
