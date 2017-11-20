package com.apptime.alexw.dartmouthdrinks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.Geofence;

/**
 * Created by zacharyjohnson on 11/17/17.
 */

public class ResourcesActivity extends AppCompatActivity {

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
    }
    //to test the service
    public void onRightsClick(View v) {
        Intent service = new Intent();
        Log.d("SERVVY", "Reached OnCLick");
        service.setClass(getApplicationContext(), ForegroundService.class);
        startService(service);
    }

    public void onEmergencyClicked(View v) {
        Intent service = new Intent();
        Log.d("SERVVY", "Reached OnCLick");
        service.setClass(getApplicationContext(), ForegroundService.class);
        stopService(service);
    }

    //to test the geofence
    public void onResourcesClick(View v) {
        Intent test = new Intent();
        test.setClass(getApplicationContext(), GeoFencey.class);
        startActivity(test);
    }
}
