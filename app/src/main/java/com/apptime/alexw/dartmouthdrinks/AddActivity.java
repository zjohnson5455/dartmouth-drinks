package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AddActivity extends AppCompatActivity {

    Button mResourceButton;
    ImageView mCupImageView;
    ImageView mPongImageView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mContext = this;

        mResourceButton = findViewById(R.id.resourceButton);
        mCupImageView = findViewById(R.id.cupImageView);
        mPongImageView = findViewById(R.id.pongImageView);


        mResourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ResourcesActivity.class);
                startActivity(intent);
            }
        });

        mCupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BeerActivity.class);
                startActivityForResult(intent, Constants.ADD_DRINK_RESULT_CODE);
            }
        });

        mPongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PongActivity.class);
                startActivity(intent);
            }
        });


    }

    public void onBACClick(View v) {
        Intent info = new Intent("INFO");
        startActivity(info);
    }
}
