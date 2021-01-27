package com.swsbt.secret.helper.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swsbt.secret.R;
import com.swsbt.secret.model.local.entity.AvgClip;
import com.swsbt.secret.model.local.entity.AvgEditorViewHolder;
import com.swsbt.secret.view.ImageActivity;

import java.io.File;
import java.util.List;

public class AvgEditAdapter extends RecyclerView.Adapter<AvgEditorViewHolder> {

    private Context mContext;
    private List<AvgClip> mAvgClipList;
    private LayoutInflater mLayoutInflater;
    public AvgEditAdapter(Context context, List<AvgClip> avgClipList) {
        mContext = context;
        mAvgClipList = avgClipList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AvgEditorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AvgEditorViewHolder(mLayoutInflater.inflate(R.layout.item_scene, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AvgEditorViewHolder avgEditorViewHolder, int i) {
        final int position = i;
        final AvgClip clip = mAvgClipList.get(i);
        System.out.println("日志：显示了哦");
        avgEditorViewHolder.imgName.setText(new File(clip.getImgPath()).getName());
        avgEditorViewHolder.imgName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, avgEditorViewHolder.imgName);
                popupMenu.getMenu().add(clip.getImgPath());
                popupMenu.show();
            }
        });
        avgEditorViewHolder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ImageActivity.class)
                        .putExtra(ImageActivity.IMAGE_PATH, clip.getImgPath()));
            }
        });
        avgEditorViewHolder.editWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        avgEditorViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAvgClipList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAvgClipList.size();
    }
}
