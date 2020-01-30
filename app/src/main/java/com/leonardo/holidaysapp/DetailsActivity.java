package com.leonardo.holidaysapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView txtName, txtLocalName, txtDate, txtSince, txtSinceDetails, txtCounties, txtIncludedCounties;
    CheckBox checkFixed, checkGlobal;
    String localName, name, date, launchYear;
    boolean fixed, global;
    String[] counties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtDate = findViewById(R.id.txtDateDetails);
        txtLocalName = findViewById(R.id.txtLocalNameDetails);
        txtName = findViewById(R.id.txtNameDetails);
        checkFixed = findViewById(R.id.checkFixed);
        checkGlobal = findViewById(R.id.checkGlobal);
        txtSince = findViewById(R.id.txtSince);
        txtSinceDetails = findViewById(R.id.txtSinceDetails);
        txtCounties = findViewById(R.id.txtCounties);
        txtIncludedCounties = findViewById(R.id.txtIncludedCounties);

        Intent i = getIntent();
        name = i.getStringExtra("NAME");
        localName = i.getStringExtra("LOCAL_NAME");
        date = i.getStringExtra("DATE");
        launchYear = i.getStringExtra("LAUNCH_YEAR");
        fixed = i.getBooleanExtra("FIXED", fixed);
        global = i.getBooleanExtra("FIXED", global);
        counties = i.getStringArrayExtra("COUNTIES");

        txtName.setText(name);
        txtLocalName.setText(localName);
        txtDate.setText(date);
        checkFixed.setChecked(fixed);
        checkGlobal.setChecked(global);
        if(launchYear != null){
            txtSince.setVisibility(View.VISIBLE);
            txtSinceDetails.setVisibility(View.VISIBLE);
            txtSinceDetails.setText(launchYear);
        }

        if(counties != null){
            txtIncludedCounties.setVisibility(View.VISIBLE);
            txtCounties.setVisibility(View.VISIBLE);
            for (String county : counties) {
                txtCounties.append(county + "\n\n");
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
