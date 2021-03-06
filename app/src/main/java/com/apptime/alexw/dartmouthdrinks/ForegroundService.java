package com.apptime.alexw.dartmouthdrinks;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.apptime.alexw.dartmouthdrinks.SignInActivity.TAG;

/**
 * Created by zacharyjohnson on 11/19/17.
 * Creates a continuously updating service with BAC info
 */

public class ForegroundService extends Service implements LocationListener {

    double BAC;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseUser;
    User currentTimeUser;
    Context context;
    Notification notification;
    boolean notified = false;
    boolean texted = false;

    DatabaseReference databaseEvents;
    List<OrganizedEvent> eventsList;
    Map<OrganizedEvent, Boolean> notifiedMap;

    // constant
    public static final long NOTIFY_INTERVAL = 5000;
    private static final int NOTIF_ID=1;

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;
    Settings settings;
    double maxBAC;
    boolean me;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

        eventsList = new ArrayList<>();
        notifiedMap = new HashMap<>();

        //get the settings information
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentTimeUser = dataSnapshot.getValue(User.class);
                settings = dataSnapshot.getValue(User.class).getSettings();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        databaseEvents = Utils.getDatabase().getReference("events");

        //get the events list
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot eventSnapshot:dataSnapshot.getChildren()) {
                    OrganizedEvent event = eventSnapshot.getValue(OrganizedEvent.class);
                    eventsList.add(event);
                    notifiedMap.put(event, false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //runs when service is started up
    public int onStartCommand(Intent intent, int flags, int startId) {

        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        }
        // recreate new
        mTimer = new Timer();
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);

        getLocation();
        showNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        currentTimeUser.setBac(0.0);
        currentTimeUser.setTimeOfLastCalc(new Date());
        mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
        mTimer.cancel();
    }



    //shows initial notification
    private void showNotification(){

        Log.d("SERVVY", "Tried to show notification");

        Intent mainIntent = new Intent(this, AddActivity.class);

        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, Constants.ADD_DRINK_REQUEST_CODE, mainIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        //setting onGoing makes it persist after close
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("You're having a night out!")
                .setContentText("Your BAC is at " + String.format("%.4f",BAC))
                .setSmallIcon(R.drawable.cup)
                .setContentIntent(mainPendingIntent)
                .setOngoing(true);
        notification = builder.build();
        startForeground(Constants.SERVICE_NOTIFICATION_CHANNEL, notification);
    }

    //not a bound service so just return null
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    //build a new notification to replace old
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

    // This is the method that can be called to update the Notification

    private void updateNotification() {
        String text = "Your BAC is " + String.format("%.4f", BAC);

        Notification notification = getMyActivityNotification(text);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(Constants.SERVICE_NOTIFICATION_CHANNEL, notification);
    }

    //updates BAC continuously
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
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            if (8 < hour && hour < 9){
                stopSelf();
            }
        }
        //check if settings exist and then call notify method
        if (settings != null) bacNotify();
    }

    //send a notification if you pass a certain threshold
    public void bacNotify() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(ForegroundService.NOTIFICATION_SERVICE);

        Intent mainIntent = new Intent(this, AddActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, Constants.ADD_DRINK_REQUEST_CODE, mainIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        //if you're within the listed distance and you haven't recently sent a notification

        if (BAC > settings.getThreshhold() && !notified && settings.getMe()) {
            Notification.Builder builder1 = new Notification.Builder(this)
                    .setContentTitle("You're over the limit!")
                    .setContentText("Your BAC is at " + BAC)
                    .setSmallIcon(R.drawable.cup)
                    .setContentIntent(mainPendingIntent)
                    .setOngoing(false);
            Notification noti = builder1.build();

            //if the settings dictate it, send a vibration and a sound with the notification
            noti.defaults |= Notification.DEFAULT_VIBRATE;
            noti.defaults |= Notification.DEFAULT_SOUND;
            notificationManager.notify(Constants.SERVICE_PUSH_NOTIFICATION_CHANNEL, noti);
            notified = true;
        }

        else if (BAC < settings.getThreshhold() && notified == true) {
            notified = false;
            notificationManager.cancel(Constants.SERVICE_PUSH_NOTIFICATION_CHANNEL);
        }

        //send a text if the user wants it
        if (BAC > settings.getThreshhold() && !texted && settings.getFriends()) {
//            Intent textIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
//                    + currentTimeUser.getFriendNumber()));
            String message = getString(R.string.FriendText);
//            textIntent.putExtra("sms_body", message);
//            textIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(textIntent);
            sendSMS(currentTimeUser.getFriendNumber(), message);
            texted = true;
        }

    }

    //updates on interval
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

    //checks permissions and finds the location
    protected void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Criteria criteria = Formulas.getCriteria();
            String provider;
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (locationManager != null) {
                provider = locationManager.getBestProvider(criteria, true);
                Location loc = locationManager.getLastKnownLocation(provider);

                //this line is necessary to make sure you continue to update location after the first request
                locationManager.requestLocationUpdates(provider,0,0,this);
            }
        }
    }

    //when the location changes, go through the events list and send texts to organizers if necessary
    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        if (settings.getOrganizer()) {
            for (OrganizedEvent event : eventsList) {
                if (!notifiedMap.get(event)) {
                    String target = event.testNotify(BAC, lat, lng);
                    if (target != null) {
//                        Intent textIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
//                                + target));
//                        String message = getString(R.string.OrganizerText1);
//                        message += currentTimeUser.getName();
//                        message += getString(R.string.OrganizerText2);
//                        textIntent.putExtra("sms_body", message);
//                        textIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(textIntent);
                        String message = getString(R.string.OrganizerText1);
                        message += " " + currentTimeUser.getName();
                        message += getString(R.string.OrganizerText2);

                        sendSMS(target, message);

                        texted = true;
                        notifiedMap.put(event, true);
                    }
                }
            }
        }
    }

    //send a text
    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    //need these to implement LocationListener
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
