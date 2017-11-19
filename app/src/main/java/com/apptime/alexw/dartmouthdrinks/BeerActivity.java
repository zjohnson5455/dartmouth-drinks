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

        final Intent result = new Intent(this, TimeDrinkActivity.class);
        result.putExtra("pct", .045);

        mFullBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Full Beer Added", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, AddActivity.class);
//                startActivity(intent);
                result.putExtra("amt", 12.0);
                result.putExtra("name", "Full cup of beer");
                startActivityForResult(result, Constants.TIME_REQUEST_CODE);
            }
        });

        mHalFBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Half Beer Added", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, AddActivity.class);
//                startActivity(intent);
                result.putExtra("amt", 6.0);
                result.putExtra("name", "Half cup of beer");
                startActivityForResult(result, Constants.TIME_REQUEST_CODE);
            }
        });

        mQuarterBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: actually make this do something
                Toast.makeText(mContext, "Quarter Beer Added", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext, AddActivity.class);
//                startActivity(intent);
                result.putExtra("amt", 3.0);
                result.putExtra("name", "Quarter cup of beer");
                startActivityForResult(result, Constants.TIME_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constants.TIME_REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

}
