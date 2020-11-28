package com.swsbt.secret.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class AndroidConfigUtils {

    /*
    获取int
     */
    public static Integer getIntDefaultZero(Context context, String key) {
        return getPreferences(context).getInt(key, 0);
    }

    /*
    保存int
     */
    public static void saveInt(Context context, String key, int value) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /*
    保存String
     */
    public static void saveString(Context context, String key, String string) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(key, string);
        editor.commit();
    }

    /*
    判断是否登录
     */
    public static boolean getBooleanValueDefaultFalse(Context context, String key) {
        return getPreferences(context).getBoolean(key, false);
    }

    /*
    保存Boolean
     */
    public static void saveBoolean(Context context, String key, Boolean value) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("PREES", Context.MODE_PRIVATE);
    }

    public static String getStringDefaultNull(Context context, String key) {
        return getPreferences(context).getString(key, null);
    }
}
