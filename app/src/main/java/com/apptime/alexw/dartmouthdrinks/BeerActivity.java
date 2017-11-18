package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class BeerActivity extends AppCompatActivity {

    ImageView mFullBeer;
    ImageView mHalFBeer;
    ImageView mQuarterBeer;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        mContext = this;

        mFullBeer = findViewById(R.id.fullImageView);
        mHalFBeer = findViewById(R.id.halfImageView);
        mQuarterBeer = findViewById(R.id.quarterImageView);

        mFullBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Full Beer Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
            }
        });

        mHalFBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Half Beer Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
            }
        });

        mQuarterBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Quarter Beer Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, AddActivity.class);
                startActivity(intent);
            }
        });


    }
}
