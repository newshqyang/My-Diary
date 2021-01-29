package com.swsbt.secret.helper.utils;

public class PathUtils {

    public static String getLast(String path) {
        System.out.println("日志：path " + path);
        return path.split("/")[path.split("/").length - 1];
    }

    /*
    去掉路径中最后一个文件或者文件夹
     */
    public static String removeLast(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        System.out.println("日志：" + path);
        String[] fileList = path.split("/");
        System.out.println("日志：" + fileList.length);
        StringBuilder temp = new StringBuilder();
        for (int i = 0;i < fileList.length - 1;i++) {
            temp.append("/").append(fileList[i]);
        }
        return temp.toString();
    }

}
