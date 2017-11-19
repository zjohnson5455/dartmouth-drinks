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

        final Intent result = new Intent("DRINK");
        result.putExtra("pct", .045);

        mFullBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Full Beer Added", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, AddActivity.class);
//                startActivity(intent);
                result.putExtra("amt", 12);
                result.putExtra("name", "Full beer");
                setResult(Constants.ADD_DRINK_REQUEST_CODE, result);
                finish();
            }
        });

        mHalFBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Half Beer Added", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, AddActivity.class);
//                startActivity(intent);
                result.putExtra("amt", 6);
                result.putExtra("name", "Half beer");
                setResult(Constants.ADD_DRINK_REQUEST_CODE, result);
                finish();
            }
        });

        mQuarterBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Quarter Beer Added", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, AddActivity.class);
//                startActivity(intent);
                result.putExtra("amt", 3);
                result.putExtra("name", "Quarter beer");
                setResult(Constants.ADD_DRINK_REQUEST_CODE, result);
                finish();
            }
        });


    }
}
