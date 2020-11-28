package com.swsbt.secret.widget;

/*
 * Created by xiehehe on 2016/10/28.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.swsbt.secret.R;

import java.lang.reflect.Field;

/**
 * User: xiehehe
 * Date: 2016-10-28
 * Time: 22:32
 * FIXME
 */
public class BaseActivity extends AppCompatActivity implements SlidingPaneLayout.PanelSlideListener {

    public final String TAG = this.getClass().getCanonicalName();
    private int value = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
    }

    /**
     * 获取导航栏高度
     * @param context
     * @return
     */
    public int getNavigationBarHeight(Context context) {
        if (!(context instanceof Activity)) {
            return 0;
        }

        int height;
        Display display = ((Activity) context).getWindow().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);

        View decorView = ((Activity) context).getWindow().getDecorView();
        Configuration conf = context.getResources().getConfiguration();
        if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
            View contentView = decorView.findViewById(android.R.id.content);
            height = Math.abs(point.x - contentView.getWidth());
        } else {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            height = Math.abs(rect.bottom - point.y);
        }
        return height;
    }

    /**
     * 初始化滑动返回
     */
    private void initSwipeBackFinish() {
        if (!isSupportSwipeBack()) {
            return;
        }
        SlidingPaneLayout slidingPaneLayout =  new SlidingPaneLayout(this);
        //通过反射改变mOverhangSize的值为0，这个mOverhangSize值为菜单到右边屏幕的最短距离，默认
        //是32dp，现在给它改成0
        try {
            //属性
            Field f_overHang = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            f_overHang.setAccessible(true);
            f_overHang.set(slidingPaneLayout, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        slidingPaneLayout.setPanelSlideListener(this);
        slidingPaneLayout.setSliderFadeColor(getColor(android.R.color.transparent));

        View leftView = new View(this);
        leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        slidingPaneLayout.addView(leftView, 0);

        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundColor(getColor(android.R.color.white));
        decor.removeView(decorChild);
        decor.addView(slidingPaneLayout);
        slidingPaneLayout.addView(decorChild, 1);
        // 防止底部内容被导航栏挡住
        ViewGroup container = findViewById(android.R.id.content);
        container.setPadding(0, 0, 0, getNavigationBarHeight(this));
    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean isSupportSwipeBack() {
        return true;
    }

    @Override
    public void onPanelClosed(View view) {

    }

    @Override
    public void onPanelOpened(View view) {
        finish();
        this.overridePendingTransition(0, R.anim.slide_out_right);
    }

    @Override
    public void onPanelSlide(View view, float v) {

    }
}

