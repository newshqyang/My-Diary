package com.swsbt.secret.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.swsbt.secret.R;
import com.swsbt.secret.util.UUtils;

public class AvgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        UUtils.fullScreen(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UUtils.exitFullScreen(this);
    }
}
