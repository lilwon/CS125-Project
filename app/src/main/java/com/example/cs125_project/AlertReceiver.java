package com.example.cs125_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    // Called when Alarm is set
    @Override
    public void onReceive(Context context, Intent intent) {
        // Show notifications.
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder notif = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, notif.build());
    }
}
