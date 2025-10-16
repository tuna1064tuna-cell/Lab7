
package com.example.a1150070049_lap7;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationHelper {
    public static final String CHANNEL_ID = "download_channel";
    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Download Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);
        }
    }

    public Notification buildProgress(String title, int progress, boolean indeterminate) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setOnlyAlertOnce(true)
                .setProgress(100, progress, indeterminate)
                .setOngoing(true)
                .addAction(0, "Pause", Utils.broadcastIntent(context, "ACTION_PAUSE"))
                .addAction(0, "Resume", Utils.broadcastIntent(context, "ACTION_RESUME"))
                .addAction(0, "Cancel", Utils.broadcastIntent(context, "ACTION_CANCEL"))
                .build();
    }

    public Notification buildCompleted(String title) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setContentText("Hoàn tất tải xuống")
                .setAutoCancel(true)
                .build();
    }
}
