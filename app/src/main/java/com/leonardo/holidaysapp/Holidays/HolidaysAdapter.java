package com.leonardo.holidaysapp.Holidays;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leonardo.holidaysapp.R;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HolidaysAdapter extends RecyclerView.Adapter<HolidaysAdapter.HolidayViewHolder> {
    private List<Holiday> list;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Holiday item);
    }

    public HolidaysAdapter(List<Holiday> list, OnItemClickListener listener) {
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
        Holiday holiday = list.get(position);
        holder.bind(list.get(position), listener);
        holder.localName.setText(holiday.getLocalName());
        holder.date.setText(holiday.getDate());

        //Calculando diferença de dias entre as datas
        long days = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse(holiday.getDate());
            long diff = date.getTime() - Calendar.getInstance().getTimeInMillis();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (days < 31 && days > 0){
            holder.inXDays.setVisibility(View.VISIBLE);
            holder.inXDays.setText("Em " + Long.toString(days) + " dias");
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class HolidayViewHolder extends RecyclerView.ViewHolder {
        TextView localName, date, inXDays;

        HolidayViewHolder(@NonNull View itemView) {
            super(itemView);
            localName = itemView.findViewById(R.id.textTitle);
            date = itemView.findViewById(R.id.textDate);
            inXDays = itemView.findViewById(R.id.textInXDays);
        }

        void bind(final Holiday item, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

}