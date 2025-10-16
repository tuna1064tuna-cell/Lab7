
package com.example.a1150070049_lap7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) return;

        if (action.equals("ACTION_PAUSE")) {
            DownloadService.isPaused = true;
        } else if (action.equals("ACTION_RESUME")) {
            DownloadService.isPaused = false;
        } else if (action.equals("ACTION_CANCEL")) {
            DownloadService.isCancelled = true;
        }
    }
}
