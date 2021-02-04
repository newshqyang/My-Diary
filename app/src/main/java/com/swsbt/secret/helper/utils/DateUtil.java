package com.swsbt.secret.helper.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    /**
     * 获取年月
     */
    public static String ym(Calendar calendar) {
        return "-  " + calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月  -";
    }

    /**
     * 获取时间
     * @param calendar
     * @return
     */
    public static String time(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取月份英文缩写
     * @param calendar
     * @return 英文缩写
     */
    public static String monAbb(Calendar calendar) {
        return MonthTranslate(calendar.get(Calendar.MONTH) + 1);
    }

    public static String MonthTranslate(int month_number){
        //数字月份转英文缩写
        String translate_result = "";
        switch (month_number){
            case 1:
                translate_result = "Jan";
                break;
            case 2:
                translate_result = "Feb";
                break;
            case 3:
                translate_result = "Mar";
                break;
            case 4:
                translate_result = "Apr";
                break;
            case 5:
                translate_result = "May";
                break;
            case 6:
                translate_result = "Jun";
                break;
            case 7:
                translate_result = "Jul";
                break;
            case 8:
                translate_result = "Aug";
                break;
            case 9:
                translate_result = "Sep";
                break;
            case 10:
                translate_result = "Oct";
                break;
            case 11:
                translate_result = "Nov";
                break;
            case 12:
                translate_result = "Dec";
                break;
        }
        return translate_result;
    }

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
