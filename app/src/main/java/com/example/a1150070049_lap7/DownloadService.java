
package com.example.a1150070049_lap7;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends Service {

    public static boolean isPaused = false;
    public static boolean isCancelled = false;
    private NotificationHelper helper;
    private NotificationManager manager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra("url");
        helper = new NotificationHelper(this);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        new Thread(() -> downloadFile(url)).start();
        return START_NOT_STICKY;
    }

    private void downloadFile(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();
            InputStream input = new BufferedInputStream(connection.getInputStream());
            String fileName = urlStr.substring(urlStr.lastIndexOf('/') + 1);
            java.io.File outFile = new java.io.File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            FileOutputStream output = new FileOutputStream(outFile);

            byte[] data = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                if (isCancelled) {
                    connection.disconnect();
                    stopSelf();
                    return;
                }
                while (isPaused) {
                    Thread.sleep(500);
                }
                total += count;
                output.write(data, 0, count);
                int progress = (int) (total * 100 / fileLength);
                Notification n = helper.buildProgress("Đang tải: " + fileName, progress, false);
                startForeground(1, n);
            }

            output.flush();
            output.close();
            input.close();
            connection.disconnect();
            manager.notify(2, helper.buildCompleted("Tải xong: " + fileName));
            stopForeground(true);
            stopSelf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
