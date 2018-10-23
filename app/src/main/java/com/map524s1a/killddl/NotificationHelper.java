package com.map524s1a.killddl;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

public class NotificationHelper extends ContextWrapper {
    private static final String CHANNEL_ID = "id";
    private static final String CHANNEL_Name = "name";
    private NotificationManager manager;
    public NotificationHelper(Context base){
        super(base);
        createChannels();
    }

    public void createChannels(){
        //Importance_default
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_Name,NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);

    }

    public NotificationManager getManager(){
        if(manager==null)
        {
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public Notification.Builder getChannelNotification(){
        return new Notification.Builder(getApplicationContext(),CHANNEL_ID )
                .setContentText("This notification is to remind you that you have events scheduled")
                .setContentTitle("Reminder")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true);
    }
}
