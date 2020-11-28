package com.swsbt.secret.model.entity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.swsbt.secret.R;

public class DiaryViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewDateline;
    public TextView textViewMonth;
    public TextView textViewDay;
    public TextView textViewTime;
    public TextView articleTitle;
    public DiaryViewHolder(View itemView){
        super(itemView);
        textViewDateline = itemView.findViewById(R.id.dateline);
        textViewMonth = itemView.findViewById(R.id.month);
        textViewDay = itemView.findViewById(R.id.day);
        textViewTime = itemView.findViewById(R.id.time);
        articleTitle = itemView.findViewById(R.id.title);
    }
}