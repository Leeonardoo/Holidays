package com.leonardo.holidaysapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CountryYearSelection extends AppCompatActivity {
    MaterialButton button;
    String country;
    int year;
    TextInputEditText textYear;
    AutoCompleteTextView autoCompleteTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoCompleteTextView = findViewById(R.id.outlined_countries);
        textYear = findViewById(R.id.textInputYear);
        button = findViewById(R.id.button);

        String[] countriesAbrv = getResources().getStringArray(R.array.countries_abrv_array);
        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.dropdown_menu_item, countries);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                int pos = -1;

                for (int i = 0; i < countries.length; i++) {
                    if (countries[i].equals(selection)) {
                        pos = i;
                        break;
                    }
                }
                System.out.println(countriesAbrv[pos]);
                country = countriesAbrv[pos];
            }
        });


        button.setOnClickListener(v -> {
            year = Integer.parseInt(textYear.getText().toString());

            if (country == null) {
                showAlertDialog();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), ShowHolidays.class);
                intent.putExtra("country", country);
                intent.putExtra("year", year);
                startActivity(intent);
            }
        });
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        textYear.setText(df.format(d));
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Espera aí!");
        builder.setMessage("Você precisa escolher um país!");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(true);
        builder.show();
    }
}
