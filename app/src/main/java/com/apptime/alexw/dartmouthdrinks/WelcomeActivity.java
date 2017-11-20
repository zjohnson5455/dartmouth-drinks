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

/**
 * Created by zacharyjohnson on 11/19/17.
 */

public class WelcomeActivity extends AppCompatActivity {

    Button mResourceButton;
    ImageButton mSettingsImageButton;
    Button startNightButton;
    Button mHistoryButton;
    Context mContext;

    public void onCreate(Bundle savedInstanceState) {

        if (isMyServiceRunning(ForegroundService.class)) {
            Intent skip = new Intent();
            skip.setClass(getApplicationContext(), AddActivity.class);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mResourceButton = findViewById(R.id.resourceButton);
        mHistoryButton = findViewById(R.id.historyButton);
        mSettingsImageButton = findViewById(R.id.settingsImageButton);
        startNightButton = findViewById(R.id.start_night_button);
        mContext = this;

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



        startNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissions()) {
                    Intent intent = new Intent(mContext, AddActivity.class);
                    intent.putExtra("Start night", true);
                    startActivity(intent);
                    Intent service = new Intent();
                    service.setClass(getApplicationContext(), ForegroundService.class);
                    startService(service);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Location permission needed to proceed", Toast.LENGTH_SHORT).show();
                    requestPermissions();
                }
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

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                Constants.PERMISSIONS_REQUEST_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(mContext, AddActivity.class);
            intent.putExtra("Start night", true);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Cannot proceed without location permission", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
