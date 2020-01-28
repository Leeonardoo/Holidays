package com.leonardo.holidaysapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HolidaysAdapter extends RecyclerView.Adapter<HolidayViewHolder> {

    private List<Holidays> list;
    private Context context;

    public HolidaysAdapter(List<Holidays> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HolidayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holidays_list, parent, false);

        return new HolidayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayViewHolder holder, int position) {
        Holidays holidays = list.get(position);

        holder.localName.setText(holidays.getLocalName());
        holder.date.setText(holidays.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
