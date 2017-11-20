package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PongActivity extends AppCompatActivity {

    //user selects how much of the game of pong he drank

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

        //find corresponding buttons
        mFullShrubImageView = findViewById(R.id.shrubFullImageView);
        mHalfShrubImageView = findViewById(R.id.shrubHalfImageView);
        mThirdShrubImageView = findViewById(R.id.shrubThirdImageView);

        mFullTreeImageView = findViewById(R.id.treeFullImageView);
        mHalfTreeImageView = findViewById(R.id.treeHalfImageView);
        mThirdTreeImageView = findViewById(R.id.treeThirdImageView);

        final Intent intent = new Intent(mContext, TimeDrinkActivity.class);
        intent.putExtra("percent", .045);

        //add listners for the different buttons and put the name and amount into the intent. wait for result from TimeDrink
        mFullTreeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Full Tree Added", Toast.LENGTH_SHORT).show();
                intent.putExtra("name", "Full tree");
                intent.putExtra("amount", 60.0);
                startActivityForResult(intent, Constants.TIME_REQUEST_CODE);
            }
        });

        mHalfTreeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Half Tree Added", Toast.LENGTH_SHORT).show();
                intent.putExtra("name", "Half Tree");
                intent.putExtra("amount", 30.0);
                startActivityForResult(intent, Constants.TIME_REQUEST_CODE);
            }
        });

        mThirdTreeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Third Tree Added", Toast.LENGTH_SHORT).show();
                intent.putExtra("name", "One Third of a Tree");
                intent.putExtra("amount", 20.0);
                startActivityForResult(intent, Constants.TIME_REQUEST_CODE);
            }
        });

        mFullShrubImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Full Shrub Added", Toast.LENGTH_SHORT).show();
                intent.putExtra("name", "Full Shrub");
                intent.putExtra("amount", 36.0);
                startActivityForResult(intent, Constants.TIME_REQUEST_CODE);
            }
        });

        mHalfShrubImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Half Shrub Added", Toast.LENGTH_SHORT).show();
                intent.putExtra("name", "Half Shrub");
                intent.putExtra("amount", 18.0);
                startActivityForResult(intent, Constants.TIME_REQUEST_CODE);
            }
        });

        mThirdShrubImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Third Shrub Added", Toast.LENGTH_SHORT).show();
                intent.putExtra("name","One Third of a Shrub");
                intent.putExtra("amount", 12.0);
                startActivityForResult(intent, Constants.TIME_REQUEST_CODE);
            }
        });

    }

    //when you get the result back from TimeDrink, pass on result back to Add
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.TIME_REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
