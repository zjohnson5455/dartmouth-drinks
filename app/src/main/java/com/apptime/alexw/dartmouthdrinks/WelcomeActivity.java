package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by zacharyjohnson on 11/19/17.
 */

public class WelcomeActivity extends AppCompatActivity {

    Button mResourceButton;
    ImageButton mSettingsImageButton;
    Button startNightButton;
    Context mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mResourceButton = findViewById(R.id.resourceButton);
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

        startNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddActivity.class);
                intent.putExtra("Start night", true);
                startActivity(intent);
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
}
