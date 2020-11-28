package com.swsbt.secret.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;

public class StringUtil {

    /**
     *  星期，数字转汉字
     * @param num <=9
     * @return
     */
    public static char getChineseOfNum(int num) {
        switch (num) {
            case 0:
                return '〇';
            case 1:
                return '一';
            case 2:
                return '二';
            case 3:
                return '三';
            case 4:
                return '四';
            case 5:
                return '五';
            case 6:
                return '六';
            case 7:
                return '七';
            case 8:
                return '八';
            default:
                return '九';
        }
    }

    //根据文件获取Intent的Type
    public static String getIMMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        String endName = fName.substring(dotIndex);
        if (endName.equals("")) {
            return type;
        }
        if (StringUtil.getTypeByEndName(endName) != null) {
            type = StringUtil.getTypeByEndName(endName);
        }
        return type;
    }
    //根据后缀名，获取Intent的Type
    public static String getTypeByEndName(String endName) {
        for (int i=0;i<MIME_MapTable.length;i++) {
            if (endName.equals(MIME_MapTable[i][0])) {
                return MIME_MapTable[i][1];
            }
        }
        return null;
    }
    private static final String[][] MIME_MapTable={
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-JavaScript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    /*
    获取两个相同字符串中的字符串
     */
    public static String getMatcherString(String text, String rgex) {
        Pattern pattern = Pattern.compile("(?<=" + rgex + ").*?(?=" + rgex + ")");
        Matcher matcher = pattern.matcher(text);
        String result = null;
        while (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }

    //复制到剪贴板
    public static void setClipData(Context context, String str) {
        ClipboardManager mClipboardManager = (ClipboardManager)context.getSystemService(CLIPBOARD_SERVICE);
        ClipData mClipData;
        mClipData = ClipData.newPlainText("label", str);
        mClipboardManager.setPrimaryClip(mClipData);
    }
    //获取剪贴板的内容
    public static String getClipData(Context context) {
        ClipboardManager mClipboardManager = (ClipboardManager)context.getSystemService(CLIPBOARD_SERVICE);
        ClipData mClipData = mClipboardManager.getPrimaryClip();
        if (mClipData == null) {
            return null;
        }
        ClipData.Item mItem = mClipData.getItemAt(0);
        return mItem.getText().toString();
    }

    //颜色：十进制转十六进制
    public static String rgbTrans10to16(int num10) {
        int merchant = num10 / 16;
        int remainder = num10 % 16;
        return ("" + trans10to16In16(merchant) + trans10to16In16(remainder));
    }
    public static String trans10to16In16(int num10) {
        String result = null;
        if (num10 < 10) {
            result = num10 + "";
        } else {
            switch (num10) {
                case 10:
                    result = "A";
                    break;
                case 11:
                    result = "B";
                    break;
                case 12:
                    result = "C";
                    break;
                case 13:
                    result = "D";
                    break;
                case 14:
                    result = "E";
                    break;
                case 15:
                    result = "F";
                    break;
            }
        }
        return result;
    }

    //颜色：十六进制转十进制
    public static int rgbTrans16to10(String num16) {
        int merchant = trans16to10In16(num16.charAt(0)) * 16;
        int remainder = trans16to10In16(num16.charAt(1));
        return (merchant+remainder);
    }
    public static int trans16to10In16(char num16) {
        int result = 0;
        if (num16 >= '0' && num16 <= '9') {
            result = Integer.parseInt("" + num16);
        } else {
            switch (num16) {
                case 'A':
                    result = 10;
                    break;
                case 'B':
                    result = 11;
                    break;
                case 'C':
                    result = 12;
                    break;
                case 'D':
                    result = 13;
                    break;
                case 'E':
                    result = 14;
                    break;
                case 'F':
                    result = 15;
                    break;
            }
        }
        return result;
    }

    public static String tHtmlUrl(String url) {
        return url.replaceAll("\\+","%2B");
    }
}
