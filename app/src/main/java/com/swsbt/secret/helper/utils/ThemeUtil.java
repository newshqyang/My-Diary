package com.swsbt.secret.helper.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

public class ThemeUtil {
    public static void setBarColor(Activity activity) {
        //修改状态栏、导航栏颜色
        activity.getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
//        String str2 = SystemUtil.getOsType(activity,"ro.build.version.emui");
//        if (!str2.contains("EmotionUI")) {
//            activity.getWindow().setNavigationBarColor(Color.parseColor("#ffffff"));
//        }
        activity.getWindow().setNavigationBarColor(Color.parseColor("#ffffff"));
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
