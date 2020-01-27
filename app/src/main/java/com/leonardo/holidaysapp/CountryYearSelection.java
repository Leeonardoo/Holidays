package com.leonardo.holidaysapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CountryYearSelection extends AppCompatActivity {
    EditText yearText;
    EditText countryText;
    Button button;
    String country;
    int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yearText = findViewById(R.id.editText);
        countryText = findViewById(R.id.editText2);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country = countryText.getText().toString();
                year = Integer.parseInt(yearText.getText().toString());
                Intent intent = new Intent(getApplicationContext(), ShowHolidays.class);
                intent.putExtra("country", country);
                intent.putExtra("year", year);
                startActivity(intent);
            }
        });
    }
}
