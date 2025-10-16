
package com.example.a1150070049_lap7;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Utils {
    public static PendingIntent broadcastIntent(Context context, String action) {
        Intent intent = new Intent(context, DownloadReceiver.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, action.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
}
