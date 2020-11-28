package com.swsbt.secret.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.swsbt.secret.R;
import com.swsbt.secret.bll.SQLOperate;
import com.swsbt.secret.dal.SQLConstant;
import com.swsbt.secret.model.HomeButtonActivity;
import com.swsbt.secret.model.entity.Diary;
import com.swsbt.secret.util.CommonMethods;
import com.swsbt.secret.util.DateUtils;
import com.swsbt.secret.util.ImageUtils;
import com.swsbt.secret.util.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Set;

public class DiaryActivity extends HomeButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private TextView mTextViewContent;
    private LinearLayout mLinearLayoutRow;
    private int mId;
    private void init() {
        setContentView(R.layout.activity_diary);
        setTitle("2020年2月9日   3:14");
        mId = getIntent().getIntExtra("articleId", 0);
        mTextViewContent = findViewById(R.id.textView_content);
        mLinearLayoutRow = findViewById(R.id.linearLayout_row);

        if (mId > 0) {
            initArticle(mId);
        }
    }

    @SuppressLint("SetTextI18n")
    private void initArticle(int id) {
        final Diary articleItem = SQLOperate.getArticleInfo(this, id);
        setTitle(DateUtils.getYMDHM(Long.parseLong(articleItem.getDate())));
        //日记内容
        String content = CommonMethods.QUERY_DATA(this, SQLConstant.DATABASE_NAME,SQLConstant.TABLE_NAME_ARTICLE, "id=?", "" + articleItem.getId(),"content");
        mTextViewContent.setText(content);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String pictures = CommonMethods.QUERY_DATA(DiaryActivity.this, SQLConstant.DATABASE_NAME,SQLConstant.TABLE_NAME_ARTICLE, "id=?", "" + articleItem.getId(),"pictures");
                        try {
                            Set<String> pictureSet = (Set<String>) JSONUtils.toSet(new JSONArray(pictures));
                            initPictures(pictureSet);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    private void initPictures(Set<String> set) {
        int columnSize = set.size();
        if (columnSize > 3) {
            if (columnSize == 4) {
                columnSize = 2;
            } else  {
                columnSize = 3;
            }
        }
        LinearLayout linearLayout = new LinearLayout(this);
        mLinearLayoutRow.addView(linearLayout);
        int count = 0;
        for (final String path : set) {
            ImageView imageView = new ImageView(this);
            Glide.with(imageView)
                    .load(ImageUtils.cutBitmap(BitmapFactory.decodeFile(path)))
                    .into(imageView);
//            imageView.setImageBitmap(ImageUtils.cutBitmap(BitmapFactory.decodeFile(
//                    AndroidFileUtils.getFileFromUri(this, Uri.fromFile(new File(path)))
//                            .getAbsolutePath())));
            imageView.setBackground(getDrawable(R.drawable.card_bg));
            imageView.setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mLinearLayoutRow.getLayoutParams());
            layoutParams.width = 250 * (4 - columnSize);
            layoutParams.height = layoutParams.width;
            layoutParams.setMargins(5, 5, 5, 5);
            imageView.setLayoutParams(layoutParams);
            imageView.setBackground(getDrawable(R.drawable.card_bg));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AndroidFileUtils.openAndroidFile(DiaryActivity.this, path);
                    startActivity(new Intent(DiaryActivity.this, ImageActivity.class)
                            .putExtra(ImageActivity.IMAGE_PATH, path));
                }
            });
            linearLayout.addView(imageView);
            count++;
            if (count == columnSize) {
                count = 0;
                linearLayout = new LinearLayout(this);
                mLinearLayoutRow.addView(linearLayout);
            }
        }
    }
}
