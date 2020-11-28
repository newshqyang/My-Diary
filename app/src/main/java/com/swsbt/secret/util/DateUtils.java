package com.swsbt.secret.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    /*
    获取时间，格式：年月日 时分
     */
    public static String getYMDHM(long millisTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisTime);
        String tempDate = String.valueOf(calendar.get(Calendar.YEAR)) + '年' + (calendar.get(Calendar.MONTH) + 1) +
                '月' + calendar.get(Calendar.DAY_OF_MONTH) + '日' + "   " +
                calendar.get(Calendar.HOUR_OF_DAY) + ':' + calendar.get(Calendar.MINUTE);
        return tempDate;
    }

    /*
    将System.currentTimeMillis()获取的毫秒时间转为日期时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String convertYMDatetime(long millisTime) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(millisTime);
    }

    /**
     * 获取当前年月日
     */
    public static String getNowYMD() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "-" +
                (calendar.get(Calendar.MONTH) + 1) + "-" +
                calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日期
     */
    public static String getNowDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "年" +
                (calendar.get(Calendar.MONTH) + 1) + "月" +
                calendar.get(Calendar.DAY_OF_MONTH) + "日" +
                " 星期" + convertWeekCh(calendar.get(Calendar.DAY_OF_WEEK));
    }
    private static String convertWeekCh(int day) {
        switch (day) {
            case 1:
                return "天";
            case 2:
                return "一";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
        }
        return null;
    }

}
