package com.apptime.alexw.dartmouthdrinks;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.apptime.alexw.dartmouthdrinks.SignInActivity.TAG;

/**
 * Created by zacharyjohnson on 11/19/17.
 */

public class ForegroundService extends Service {

    double BAC;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseUser;
    User currentTimeUser;
    Context context;
    Notification notification;

    // constant
    public static final long NOTIFY_INTERVAL = 2000; // 1 second
    private static final int NOTIF_ID=1;

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentTimeUser = dataSnapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        Log.d("SERVVY", "Received Start Foreground Intent");
    }


    //runs when service is started up
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("SERVVY", "Received Start Foreground Intent");

        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);

        showNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }




    private void showNotification(){

        Log.d("SERVVY", "Tried to show notification");

        Intent mainIntent = new Intent(this, AddActivity.class);

        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, Constants.ADD_DRINK_REQUEST_CODE, mainIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("You're having a night out!")
                .setContentText("Your BAC is at " + BAC)
                .setSmallIcon(R.drawable.cup)
                .setContentIntent(mainPendingIntent)
                .setOngoing(true);
        notification = builder.build();
        startForeground(1, notification);
    }

    //not a bound service so just return null
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    private Notification getMyActivityNotification(String text){
        // The PendingIntent to launch our activity if the user selects
        // this notification
        CharSequence title = "You're having a night out!";
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, new Intent(this, AddActivity.class), 0);

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.cup)
                .setContentIntent(contentIntent)
                .setOngoing(true);
        notification = builder.build();
        return notification;
    }

    /**
     * This is the method that can be called to update the Notification
     */
    private void updateNotification() {
        String text = "Your BAC is " + BAC;

        Notification notification = getMyActivityNotification(text);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIF_ID, notification);
    }

    public void BACUpdate(){
        if (currentTimeUser != null) {
            BAC = currentTimeUser.getBac();
            Date date = new Date();
            double timeDiff = Formulas.milliToMinutes(date.getTime() - currentTimeUser.getTimeOfLastCalc().getTime());
            double newBAC = Formulas.bacMetabolism(BAC, timeDiff);
            User user = currentTimeUser;
            user.setBac(newBAC);
            user.setTimeOfLastCalc(date);
            mDatabase.child("users").child(currentUser.getUid()).setValue(user);
            BAC = user.getBac();

        }
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    BACUpdate();
                    updateNotification();
                }

            });
        }

    }

}
