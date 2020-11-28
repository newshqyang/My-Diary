package com.swsbt.secret.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationUtils {

    /*
    生成通知
     */
    public static NotificationCompat.Builder createNotificationBuilder(Context context,
                                                                       String channel_name,
                                                                       String channel_description,
                                                                       String CHANNEL_ID) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channel_name, importance);
            channel.setDescription(channel_description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        return builder;
    }

    /*
    创造通知管理器
     */
    public static NotificationManagerCompat createNotificationManager(Context context) {
        return NotificationManagerCompat.from(context);
    }

}
