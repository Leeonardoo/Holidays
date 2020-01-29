package com.leonardo.holidaysapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView txtName, txtLocalName, txtDate;
    String name;
    String date;
    String localName;
    boolean fixed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtDate = findViewById(R.id.textDateDetails);
        txtLocalName = findViewById(R.id.textLocalNameDetails);
        txtName = findViewById(R.id.textNameDetails);

        Intent i = getIntent();
        name = i.getStringExtra("NAME");
        localName = i.getStringExtra("LOCAL_NAME");
        date = i.getStringExtra("DATE");

        txtName.setText(name);
        txtLocalName.setText(localName);
        txtDate.setText(date);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
