package com.leonardo.holidaysapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SelectionActivity extends AppCompatActivity {
    MaterialButton button;
    String country;
    int year;
    TextInputEditText textYear;
    AutoCompleteTextView autoCompleteTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        autoCompleteTextView = findViewById(R.id.outlined_countries);
        textYear = findViewById(R.id.textInputYear);
        button = findViewById(R.id.readyButton);

        //Lendo países de countries_array.xml e criando um novo adapter
        String[] countriesAbbr = getResources().getStringArray(R.array.countries_abrv_array);
        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), R.layout.dropdown_menu_item, countries);
        autoCompleteTextView.setAdapter(adapter);

        //Listener com um workarround (position != posição original)
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selection = (String) parent.getItemAtPosition(position);
            int pos = -1;

            for (int i = 0; i < countries.length; i++) {
                if (countries[i].equals(selection)) {
                    pos = i;
                    break;
                }
            }
            //Queremos apenas a sigla para a API
            country = countriesAbbr[pos];
        });


        button.setOnClickListener(v -> {
            year = Integer.parseInt(textYear.getText().toString());

            //Não pode pesquisar sem país selecionado
            if (country == null) {
                showAlertDialog();
            } else {
                Intent intent = new Intent(getApplicationContext(), ShowHolidaysActivity.class);
                intent.putExtra("COUNTRY", country);
                intent.putExtra("YEAR", year);
                startActivity(intent);
            }
        });

        //Ano atual como padrão no textYear
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        textYear.setText(df.format(d));
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_wait);
        builder.setMessage(R.string.alert_country);
        builder.setPositiveButton(R.string.alert_ok, (dialog, which) -> dialog.dismiss());
        builder.setCancelable(true);
        builder.show();
    }
}
