package com.swsbt.secret.model.entity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swsbt.secret.R;
import com.swsbt.secret.adapter.AvgEditAdapter;
import com.swsbt.secret.util.CommonMethods;

import java.util.ArrayList;
import java.util.List;

public class AvgEditorBar extends LinearLayout implements View.OnClickListener {

    private List<AvgClip> mAvgClipList;

    private Activity mActivity;
    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    private LinearLayout mPicture;
    private RecyclerView mSceneRV;
    private AvgEditAdapter mAvgEditAdapter;
    private Context mContext;
    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.avg_editor_bar, this);
        mPicture = findViewById(R.id.add_picture);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        mSceneRV = findViewById(R.id.scene_recyclerView);
        mSceneRV.setLayoutManager(layoutManager);
        mPicture.setOnClickListener(this);
        mAvgClipList = new ArrayList<>();
        mAvgEditAdapter = new AvgEditAdapter(context, mAvgClipList);
        mSceneRV.setAdapter(mAvgEditAdapter);
    }

    public AvgEditorBar(Context context) {
        super(context);
        init(context);
    }
    public AvgEditorBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public AvgEditorBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public AvgEditorBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_picture) {
            selectPicture();
        }
    }

    public List<AvgClip> getAvgClipList() {
        return mAvgClipList;
    }

    public int getClipSize() {
        return mAvgClipList.size();
    }

    /*
    选择图片
     */
    private void selectPicture() {
        //获取读写权限
        CommonMethods.getWriteReadFilePermissions(mActivity);
        //打开图片管理器
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mActivity.startActivityForResult(intent, 1);
    }
    /*
    加入场景
     */
    public void addScene(String imgPath) {
        AvgClip clip = new AvgClip();
        clip.setImgPath(imgPath);
        mAvgClipList.add(clip);
        mAvgEditAdapter.notifyDataSetChanged();
    }
}
