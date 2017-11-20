package com.apptime.alexw.dartmouthdrinks;

import android.*;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Toast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddActivity extends AppCompatActivity implements LocationListener {

    boolean start;

    Button mResourceButton;
    Button mHistoryButton;
    ImageButton mSettingsImageButton;

    //different types of alcoholic intake
    ImageView mCupImageView;
    ImageView mPongImageView;
    ImageView mCanImageView;
    ImageView mShotImageView;
    ImageView mWineImageView;
    Context mContext;

    //Your BAC
    TextView mBACTextView;

    //Firebase variables to find the currentUser
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseUser;
    User currentTimeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mContext = this;

        mResourceButton = findViewById(R.id.resourceButton);
        mHistoryButton = findViewById(R.id.historyButton);
        mSettingsImageButton = findViewById(R.id.settingsImageButton);
        mCupImageView = findViewById(R.id.cupImageView);
        mPongImageView = findViewById(R.id.pongImageView);
        mCanImageView = findViewById(R.id.canImageView);
        mShotImageView = findViewById(R.id.shotImageView);
        mWineImageView = findViewById(R.id.wineImageView);
        mBACTextView = findViewById(R.id.bacTextView);


        //get the current user
        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //check if a night has been started
        start = getIntent().getBooleanExtra("Start night", false);

        //send users to appropriately titled pages when clicked
        mResourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ResourcesActivity.class);
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

        mCupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BeerActivity.class);
                startActivityForResult(intent, Constants.ADD_DRINK_REQUEST_CODE);
            }
        });

        mPongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PongActivity.class);
                startActivityForResult(intent, Constants.ADD_DRINK_REQUEST_CODE);
            }
        });

        //because there is no second page for this type, put the variables in the intent
        mCanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TimeDrinkActivity.class);
                intent.putExtra("name", "Can of beer");
                intent.putExtra("amount", 12.0);
                intent.putExtra("percent", .045);
                startActivityForResult(intent, Constants.TIME_REQUEST_CODE);
            }
        });

        mShotImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TimeDrinkActivity.class);
                intent.putExtra("name", "Shot");
                intent.putExtra("amount", 1.5);
                intent.putExtra("percent", .4);
                startActivityForResult(intent, Constants.TIME_REQUEST_CODE);
            }
        });

        mWineImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TimeDrinkActivity.class);
                intent.putExtra("name", "Glass of wine");
                intent.putExtra("amount", 5.0);
                intent.putExtra("percent", .12);
                startActivityForResult(intent, Constants.TIME_REQUEST_CODE);
            }
        });



        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //do calculations using currentTimeUser in here
                currentTimeUser = dataSnapshot.getValue(User.class);
                double bac = dataSnapshot.getValue(User.class).getBac();
                setBacColor(bac);
                String bacText = String.format("%.5f", bac);
                mBACTextView.setText(bacText);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(history);
            }
        });
    }

    //when you get back how long ago the drink was, calculate the new BAC
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == Constants.ADD_DRINK_REQUEST_CODE || requestCode == Constants.TIME_REQUEST_CODE)
                && resultCode == RESULT_OK) {

            Date currentTime = new Date();

            //set variables
            String name = data.getStringExtra("name");
            double amount = data.getDoubleExtra("amount", 0.0);
            double percent = data.getDoubleExtra("percent", 0.0);
            double timeSinceDrink = (double)data.getIntExtra("time", 0);
            double prevBac = currentTimeUser.getBac();
            Date drinkTime = new Date(currentTime.getTime() - Formulas.minutesToMilli(timeSinceDrink));

            Drink newDrink = new Drink(name, drinkTime, prevBac, amount, percent);

            //if this is the first drink
            if (prevBac == 0.0) {
                currentTimeUser.setTimeOfLastCalc(currentTime);
            }

            //update
            double timeSinceCalc = Formulas.milliToMinutes(currentTime.getTime() - currentTimeUser.getTimeOfLastCalc().getTime());

            double bac = Formulas.calculateBac(currentTimeUser, newDrink, timeSinceCalc, timeSinceDrink);

            newDrink.setPostBac(bac);
            currentTimeUser.setBac(bac);
            Log.d("LOG", "onActivityResult: " + bac);
            currentTimeUser.setTimeOfLastCalc(currentTime);

            //record the history of the drinks
            ArrayList<OnNight> history = currentTimeUser.getHistory();
            OnNight currentNight;



            if (start) {
                currentNight = new OnNight(currentTime);
                if (currentNight == null) currentNight.setDrinkList(new ArrayList<Drink>());
                start = false;
            }
            else {
                currentNight = history.remove(history.size()-1);
            }

            //add to the history tabs
            currentNight.addDrink(newDrink);
            if (history == null) history = new ArrayList<OnNight>();
            history.add(currentNight);
            currentTimeUser.setHistory(history);

            mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);

        }
    }

    //when you click on your BAC
    public void onBACClick(View v) {
        Intent info = new Intent("INFO");
        startActivity(info);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("CYCLE", "onDestroy");
    }

    //need these methods to implement LocationListener
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    //set what color your BAC is
    public void setBacColor(double bac){
        if (bac < 0.05) mBACTextView.setTextColor(Color.parseColor("#1dff00"));
        else if (bac < 0.1) mBACTextView.setTextColor(Color.parseColor("#a5ff00"));
        else if (bac < 0.15) mBACTextView.setTextColor(Color.parseColor("#d8ff00"));
        else if (bac < 0.2) mBACTextView.setTextColor(Color.parseColor("#ff8300"));
        else if (bac < 0.25) mBACTextView.setTextColor(Color.parseColor("#ff5d00"));
        else if (bac < 0.3) mBACTextView.setTextColor(Color.parseColor("#ff3700"));
        else if (bac < 0.35) mBACTextView.setTextColor(Color.parseColor("#ff3700"));
        else if (bac < 0.4) mBACTextView.setTextColor(Color.parseColor("#630000"));
        else mBACTextView.setTextColor(Color.parseColor("#000000"));
    }
}
