package com.swsbt.secret.helper.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashSet;
import java.util.Set;

public class JSONUtils {

    /*
    set转array
     */
    public static JSONArray fromSet(Set<?> set) {
        JSONArray array = new JSONArray();
        for (Object o : set) {
            array.put(o);
        }
        return array;
    }

    /*
    JSONArray转set
     */
    public static Set<?> toSet(JSONArray array) throws JSONException {
        Set<Object> set = new HashSet<>();
        for (int i = 0;i < array.length();i++) {
            set.add(array.get(i));
        }
        return set;
    }
}
