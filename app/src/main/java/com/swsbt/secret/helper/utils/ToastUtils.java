package com.swsbt.secret.helper.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtils {

    private static Context mContext;
    public static void setContext(Context context) {
        mContext = context;
    }

    public static void toastS(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public static void toastL(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    public static void tsInThread(String text) {
        Looper.prepare();
        toastS(text);
        Looper.loop();
    }

}
