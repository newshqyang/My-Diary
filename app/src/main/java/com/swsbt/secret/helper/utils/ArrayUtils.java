package com.swsbt.secret.helper.utils;

public class ArrayUtils {

    /*
    判断字符串是否在数组中，在就返回第一次发现的下标，没有找到就返回-1
     */
    public static int isExisted(Object[] arrays, Object string) {
        for (int i = 0;i < arrays.length;i++) {
            if (arrays[i].equals(string)) {
                return i;
            }
        }
        return -1;
    }

}
