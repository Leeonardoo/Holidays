package com.leonardo.holidaysapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HolidaysAdapter extends RecyclerView.Adapter<HolidaysAdapter.HolidayViewHolder> {
    private List<Holidays> list;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Holidays item);
    }

    public HolidaysAdapter(List<Holidays> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HolidayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holidays_list, parent, false);
        return new HolidayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayViewHolder holder, int position) {
        Holidays holidays = list.get(position);
        holder.bind(list.get(position), listener);
        holder.localName.setText(holidays.getLocalName());
        holder.date.setText(holidays.getDate());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolidayViewHolder extends RecyclerView.ViewHolder {
        TextView localName, date;

        public HolidayViewHolder(@NonNull View itemView) {
            super(itemView);
            localName = itemView.findViewById(R.id.textTitle);
            date = itemView.findViewById(R.id.textDate);
        }

        public void bind(final Holidays item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}