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

import com.leonardo.holidaysapp.Holidays.Holiday;
import com.leonardo.holidaysapp.Holidays.HolidaysAPI;
import com.leonardo.holidaysapp.Holidays.HolidaysAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowHolidaysActivity extends AppCompatActivity {

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
        String country = intent.getStringExtra("COUNTRY");
        int year = intent.getIntExtra("YEAR", 0);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Inicia a conexão com a API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://date.nager.at/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HolidaysAPI holidaysAPI = retrofit.create(HolidaysAPI.class);
        Call<List<Holiday>> call = holidaysAPI.getHolidays(year, country);
        call.enqueue(new Callback<List<Holiday>>() {
            @Override
            public void onResponse(Call<List<Holiday>> call, Response<List<Holiday>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Erro de servidor: ", "Recebemos código " + response.code() + " API fora do ar ou dados inválidos escolhidos!");
                    showAlertDialog(getString(R.string.alert_unexpected_response));
                    return;
                }

                progressBar.setVisibility(View.GONE);
                loadingText.setVisibility(View.GONE);
                Show(response.body());
            }

            @Override
            public void onFailure(Call<List<Holiday>> call, Throwable t) {
                Log.e("Erro de rede: ", "Algum erro ocorreu ao tentar ler os dados do servidor!" + t.getLocalizedMessage());
                showAlertDialog(getString(R.string.alert_connection_error));
            }

            //Exibe resposta do servidor no recyclerView
            private void Show(List<Holiday> response) {
                HolidaysAdapter holidaysAdapter = new HolidaysAdapter(response, item -> {
                    Intent i = new Intent(getBaseContext(), DetailsActivity.class);

                    //Mais dados do servidor
                    i.putExtra("NAME", item.getName());
                    i.putExtra("DATE", item.getDate());
                    i.putExtra("LOCAL_NAME", item.getLocalName());
                    i.putExtra("LAUNCH_YEAR", item.getLaunchYear());
                    i.putExtra("FIXED", item.getFixed());
                    i.putExtra("GLOBAL", item.getGlobal());
                    i.putExtra("COUNTIES", item.getCounties());
                    startActivity(i);
                });
                recyclerView.setAdapter(holidaysAdapter);
            }
        });
    }

    public void showAlertDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_something_wrong);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.alert_try_again, (dialog, which) -> dialog.dismiss());
        builder.setCancelable(true);
        builder.show();
    }
}
