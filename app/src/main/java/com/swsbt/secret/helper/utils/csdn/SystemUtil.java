package com.swsbt.secret.helper.utils.csdn;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SystemUtil {
    public static String getOsType(Context context, String str) {
        if (str == null || str.trim().equals("")) {
            return "";
        }
        List<String> info = getOsInfoByCommand(context, new String[]{"/system/bin/sh", "-c", "getprop " + str});
        if (info == null || info.size() <= 0) {
            return "fail";
        }
        return (String) info.get(0);
    }
    public static ArrayList<String> getOsInfoByCommand(Context context, String[] commandArr) {
        ArrayList<String> arrayList = new ArrayList();

        BufferedReader bufferedReader1 = null;
        BufferedReader bufferedReader2 = null;
        try {
            Process exec = Runtime.getRuntime().exec(commandArr);
            bufferedReader1 = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while (true) {
                String readLine = bufferedReader1.readLine();
                if (readLine == null) {
                    break;
                }
                arrayList.add(readLine);
            }
            bufferedReader2 = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
            while (true) {
                String readLine2 = bufferedReader2.readLine();
                if (readLine2 == null) {
                    break;
                }
                arrayList.add(readLine2);
            }

        } catch (Throwable th) {

        }finally {
            if (bufferedReader1 != null) {
                try {
                    bufferedReader1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufferedReader2 == null) {
                try {
                    bufferedReader2.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return arrayList;
    }
}
