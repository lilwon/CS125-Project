package com.example.cs125_project;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mgr;

    public NotificationHelper(Context base) {
        super(base);
        // only works for Oreo and above
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        // Set only one channel to max importance
        NotificationChannel channel  = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if ( mgr == null ) {
            // no notifications
            mgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mgr;
    }

    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Time to sleep!").setContentText("This is a daily reminder to sleep")
                .setSmallIcon(R.drawable.ic_sleeping);
    }
}
