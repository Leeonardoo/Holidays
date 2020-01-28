package com.leonardo.holidaysapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HolidayViewHolder extends RecyclerView.ViewHolder {

    TextView localName, date;

    public HolidayViewHolder(@NonNull View itemView) {
        super(itemView);

        localName = itemView.findViewById(R.id.textTitle);
        date = itemView.findViewById(R.id.textDate);
    }
}
