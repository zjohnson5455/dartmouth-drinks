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
    //gives information about campus resources

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
    }
    //to test the service. Don't need anymore
    public void onRightsClick(View v) {

    }

    //secret way of stopping service. For debugging purposes
    public void onEmergencyClicked(View v) {
        Intent service = new Intent();
        Log.d("SERVVY", "Reached OnCLick");
        service.setClass(getApplicationContext(), ForegroundService.class);
        stopService(service);
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        Intent result = new Intent("FINISH");
        setResult(RESULT_OK, result);
        finish();
    }

}
