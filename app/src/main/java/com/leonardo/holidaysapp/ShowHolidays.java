package com.leonardo.holidaysapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leonardo.holidaysapp.Holidays.Holidays;
import com.leonardo.holidaysapp.Holidays.HolidaysAPI;
import com.leonardo.holidaysapp.Holidays.HolidaysAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowHolidays extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_holidays);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        loadingText = findViewById(R.id.textLoading);

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
                    Log.e("Erro de servidor: ", "Recebemos código " + response.code() + " API fora do ar ou dados inválidos escolhidos!");
                    showAlertDialog("Resposta incorreta recebida! API fora do ar ou dados inválidos escolhidos. Tente novamente.");
                    return;
                }

                progressBar.setVisibility(View.GONE);
                loadingText.setVisibility(View.GONE);
                Show(response.body());
            }

            @Override
            public void onFailure(Call<List<Holidays>> call, Throwable t) {
                Log.e("Erro de rede: ", "Algum erro ocorreu ao tentar ler os dados do servidor!" + t.getLocalizedMessage());
                showAlertDialog("Ocorreu um erro na conexão com o servidor. Verifique a sua conexão com a internet e tente novamente.");
            }

            private void Show(List<Holidays> response){
                HolidaysAdapter holidaysAdapter = new HolidaysAdapter(response, new HolidaysAdapter.OnItemClickListener(){

                    @Override
                    public void onItemClick(Holidays item) {
                        Intent i = new Intent(getBaseContext(), DetailsActivity.class);

                        i.putExtra("NAME", item.getName());
                        i.putExtra("DATE", item.getDate());
                        i.putExtra("LOCAL_NAME", item.getLocalName());
                        i.putExtra("LAUNCH_YEAR", item.getLaunchYear());
                        i.putExtra("FIXED", item.getFixed());
                        i.putExtra("GLOBAL", item.getGlobal());
                        i.putExtra("COUNTIES", item.getCounties());
                        startActivity(i);
                    }
                });
                recyclerView.setAdapter(holidaysAdapter);
            }
        });
    }

    public void showAlertDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Algo deu errado...!");
        builder.setMessage(msg);
        builder.setPositiveButton("Tentar novamente", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(true);
        builder.show();
    }
}
