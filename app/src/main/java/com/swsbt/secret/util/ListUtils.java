package com.swsbt.secret.util;

import java.util.List;

public class ListUtils {

    /*
    判断字符串是否存在列表中
     */
    public static boolean isExisted(List<String> list, String string) {
        for (String s : list) {
            if (s.equals(string)) {
                return true;
            }
        }
        return false;
    }

    /*
    获取最后一个单元
     */
    public static Object getLastObject(List<?> list) {
        return list.get(list.size() - 1);
    }

    /*
    给一个position，删掉position后面的单元
     */
    public static void removeLastByPosition(List<?> list, int position) {
        for (int i = (list.size() - position - 1);i > 0;i--) {
            list.remove(list.size() - 1);
        }
    }

}
