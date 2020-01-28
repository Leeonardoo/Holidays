package com.leonardo.holidaysapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowHolidays extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_holidays);

        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        String country = intent.getStringExtra("country");
        int year = intent.getIntExtra("year", 0);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://date.nager.at/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HolidaysAPI holidaysAPI = retrofit.create(HolidaysAPI.class);

        Call<List<Holidays>> call = holidaysAPI.getHolidays(year, country);
        call.enqueue(new Callback<List<Holidays>>() {
            @Override
            public void onResponse(Call<List<Holidays>> call, Response<List<Holidays>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code" + response.code());
                    return;
                }

                Show(response.body());
                List<Holidays> holidays = response.body();
                for (Holidays holiday : holidays) {
                    String content = "";
                    content += "DATE: " + holiday.getDate() + "\n";
                    content += "LOCALNAME: " + holiday.getLocalName() + "\n\n";
                }
            }

            @Override
            public void onFailure(Call<List<Holidays>> call, Throwable t) {
                System.out.println("Algum erro ocorreu ao tentar ler os dados do servidor!");
                System.out.println(t.getMessage());
            }

            private void Show(List<Holidays> response){
                HolidaysAdapter holidaysAdapter = new HolidaysAdapter(response,getApplicationContext());
                recyclerView.setAdapter(holidaysAdapter);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
