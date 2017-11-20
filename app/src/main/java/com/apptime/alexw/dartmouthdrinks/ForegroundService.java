package com.apptime.alexw.dartmouthdrinks;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by zacharyjohnson on 11/19/17.
 */

public class ForegroundService extends Service {

    double BAC;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("SERVVY", "Received Start Foreground Intent");
    }


    //runs when service is started up
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("SERVVY", "Received Start Foreground Intent");
        showNotification();
        return super.onStartCommand(intent, flags, startId);
    }


    private void showNotification(){

        Log.d("SERVVY", "Tried to show notification");

        Intent mainIntent = new Intent(this, AddActivity.class);
        //this intent sends you back to the main game screen
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, Constants.ADD_DRINK_REQUEST_CODE, mainIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("You're having a night out!")
                .setContentText("Your BAC is at " + BAC)
                .setSmallIcon(R.drawable.cup)
                .setContentIntent(mainPendingIntent)
                .setOngoing(true);
        Notification notification = builder.build();
        startForeground(1, notification);
    }

    //not a bound service so just return null
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }
}
