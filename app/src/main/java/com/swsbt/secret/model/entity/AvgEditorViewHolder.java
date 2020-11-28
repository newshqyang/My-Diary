package com.swsbt.secret.model.entity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swsbt.secret.R;

public class AvgEditorViewHolder extends RecyclerView.ViewHolder {
    public TextView imgName;
    public TextView preview;
    public TextView editWord;
    public TextView delete;
    public AvgEditorViewHolder(@NonNull View itemView) {
        super(itemView);
        imgName = itemView.findViewById(R.id.image_name);
        preview = itemView.findViewById(R.id.preview);
        editWord = itemView.findViewById(R.id.edit_word);
        delete = itemView.findViewById(R.id.delete);
    }
}
