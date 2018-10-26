package com.map524s1a.killddl;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class NotificationHelper extends ContextWrapper {
    private static final String CHANNEL_ID = "id";
    private static final String CHANNEL_Name = "name";
    private NotificationManager manager;
    private String message;
    private String title;


    public NotificationHelper(Context base){
        super(base);
        createChannels();
        message = "";
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

    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
    public NotificationManager getManager(){
        if(manager==null)
        {
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

        public Notification.Builder getChannelNotification(){



        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return new Notification.Builder(getApplicationContext(),CHANNEL_ID )
                .setContentText(message)
                .setContentTitle("Reminder")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true);
    }
}
