package com.apptime.alexw.dartmouthdrinks;

import android.*;
import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by zacharyjohnson on 11/19/17.
 */

public class WelcomeActivity extends AppCompatActivity {

    //welcome user and provide central navigation screen

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseUser;
    User currentTimeUser;

    Settings settings;

    Button mResourceButton;
    ImageButton mSettingsImageButton;
    Button startNightButton;
    Button mHistoryButton;
    Context mContext;

    public void onCreate(Bundle savedInstanceState) {

        if (isMyServiceRunning(ForegroundService.class)) {
            Intent skip = new Intent();
            skip.setClass(getApplicationContext(), AddActivity.class);
            startActivity(skip);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //get components
        mResourceButton = findViewById(R.id.resourceButton);
        mHistoryButton = findViewById(R.id.historyButton);
        mSettingsImageButton = findViewById(R.id.settingsImageButton);
        startNightButton = findViewById(R.id.start_night_button);
        mContext = this;
        mAuth = FirebaseAuth.getInstance();

        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

        //watch for data and get the settings
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentTimeUser = dataSnapshot.getValue(User.class);
                settings = currentTimeUser.getSettings();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        //set listeners and bring user to corresponding pages onClick
        mResourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ResourcesActivity.class);
                startActivity(intent);
            }
        });

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(history);
            }
        });



        //make sure to check permissions to start
        startNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFriendPermissionsToStart();
            }
        });

        mSettingsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    //check if service is running
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    //finalize permissions
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_SMS_FRIEND: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkOrganizerPermissionsToStart();
                }
                else {
                    needPermissions();
                }
            }
            case Constants.PERMISSIONS_REQUEST_SMS_ORG: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        // if not, request it, tell user it is needed
                        Toast.makeText(getApplicationContext(), "Location needed to activate option", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(WelcomeActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                Constants.PERMISSIONS_REQUEST_FINE_LOCATION);
                    }
                    else {
                        sendStartIntent();
                    }
                }
                else {
                    needPermissions();
                }
            }
            case Constants.PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendStartIntent();
                }
                else{
                    needPermissions();
                }
            }
        }
    }

    //check permissions
    public void checkFriendPermissionsToStart() {
        if (settings.getFriends()) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                // if not, request it, tell user it is needed
                Toast.makeText(getApplicationContext(), "SMS needed to proceed", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(WelcomeActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        Constants.PERMISSIONS_REQUEST_SMS_FRIEND);
            }
            else {
                checkOrganizerPermissionsToStart();
            }
        }
        else checkOrganizerPermissionsToStart();
    }

    //check permissions for organizers

    public void checkOrganizerPermissionsToStart() {
        if (settings.getOrganizer()){
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                // if not, request it, tell user it is needed
                Toast.makeText(getApplicationContext(), "Location needed to activate option", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(WelcomeActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        Constants.PERMISSIONS_REQUEST_SMS_ORG);
            }
            else if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // if not, request it, tell user it is needed
                Toast.makeText(getApplicationContext(), "Location needed to activate option", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(WelcomeActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.PERMISSIONS_REQUEST_FINE_LOCATION);
            }
            else sendStartIntent();
        }
        else sendStartIntent();
    }
    //start the add activity and the service
    public void sendStartIntent() {
        Intent intent = new Intent(mContext, AddActivity.class);
        intent.putExtra("Start night", true);
        startActivity(intent);
        Intent service = new Intent();
        service.setClass(getApplicationContext(), ForegroundService.class);
        startService(service);
        finish();
    }

    public void needPermissions(){
        Toast.makeText(getApplicationContext(), "Need permissions to continue", Toast.LENGTH_SHORT).show();
    }
}