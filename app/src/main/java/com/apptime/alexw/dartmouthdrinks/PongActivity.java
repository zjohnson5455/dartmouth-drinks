package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PongActivity extends AppCompatActivity {

    ImageView mFullTreeImageView;
    ImageView mHalfTreeImageView;
    ImageView mThirdTreeImageView;
    ImageView mFullShrubImageView;
    ImageView mHalfShrubImageView;
    ImageView mThirdShrubImageView;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pong);
        mContext = this;

        mFullShrubImageView = findViewById(R.id.shrubFullImageView);
        mHalfShrubImageView = findViewById(R.id.shrubHalfImageView);
        mThirdShrubImageView = findViewById(R.id.shrubThirdImageView);

        mFullTreeImageView = findViewById(R.id.treeFullImageView);
        mHalfTreeImageView = findViewById(R.id.treeHalfImageView);
        mThirdTreeImageView = findViewById(R.id.treeThirdImageView);

        mFullTreeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Full Tree Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
            }
        });

        mHalfTreeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Half Tree Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
            }
        });

        mThirdTreeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Third Tree Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
            }
        });

        mFullShrubImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Full Shrub Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
            }
        });

        mHalfShrubImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Half Shrub Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
            }
        });

        mThirdShrubImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Third Shrub Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
            }
        });

    }
}
