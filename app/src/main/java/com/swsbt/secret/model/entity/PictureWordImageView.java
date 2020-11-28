package com.swsbt.secret.model.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.swsbt.secret.R;

import java.util.List;

public class PictureWordImageView extends ConstraintLayout implements View.OnClickListener {

    private ImageView mPicture;
    private TextView mWord;
    private List<String> mWordList;
    private Bitmap mBitmap;
    private int page = 0;
    public void setData(Bitmap bitmap, List<String> wordList) {
        mWordList = wordList;
        mBitmap = bitmap;
        reset();
    }

    public void reset() {
        page = 0;
        mPicture.setImageBitmap(mBitmap);
        mWord.setText(mWordList.get(page));
    }

    private Context mContext;
    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.picture_word_image_view, this);
        mPicture = findViewById(R.id.picture);
        mWord = findViewById(R.id.word);
        mPicture.setOnClickListener(this);
    }

    public PictureWordImageView(Context context) {
        super(context);
        init(context);
    }
    public PictureWordImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public PictureWordImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.picture) {
            wordVisibilityOperate();
        } else if (v.getId() == R.id.word) {
            nextWord();
        }
    }

    private void nextWord() {
        if ((page + 1) == mWordList.size()) {
            return;
        }
        page++;
        mWord.setText(mWordList.get(page));
    }

    private void wordVisibilityOperate() {
        if (mWord.getVisibility() == VISIBLE) {
            mWord.setVisibility(GONE);
        } else {
            mWord.setVisibility(VISIBLE);
        }
    }
}
