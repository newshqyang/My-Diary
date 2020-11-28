package com.swsbt.secret.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class NetUtils {

    //获取内网IP地址
    public static String getLocalIP(Context context) {
        @SuppressLint("WifiManagerPotentialLeak")
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
    }
    public static String formatIpAddress(int ipAddress) {
        return (ipAddress & 0xFF ) + "." +
                ((ipAddress >> 8 ) & 0xFF) + "." +
                ((ipAddress >> 16 ) & 0xFF) + "." +
                ( ipAddress >> 24 & 0xFF) ;
    }
    private static byte[] readBytes(InputStream is){
        try {
            byte[] buffer = new byte[1024];
            int len = -1 ;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((len = is.read(buffer)) != -1){
                baos.write(buffer, 0, len);
            }
            baos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
    public static String readString(InputStream is){
        return new String(readBytes(is));
    }

}
