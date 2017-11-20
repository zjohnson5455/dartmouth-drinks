package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.List;

public class AddActivity extends AppCompatActivity {

    boolean start;

    Button mResourceButton;
    Button mHistoryButton;
    ImageButton mSettingsImageButton;
    ImageView mCupImageView;
    ImageView mPongImageView;
    ImageView mCanImageView;
    ImageView mShotImageView;
    ImageView mWineImageView;
    Context mContext;
    TextView mBACTextView;

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


        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        start = getIntent().getBooleanExtra("Start night", false);

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
                intent.putExtra("amount", 5);
                intent.putExtra("percent", .12);
                startActivityForResult(intent, Constants.TIME_REQUEST_CODE);
            }
        });


        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentTimeUser = dataSnapshot.getValue(User.class);

                double bac = dataSnapshot.getValue(User.class).getBac();

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
                Toast.makeText(getApplicationContext(), String.valueOf(new Date().getTime()), Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == Constants.ADD_DRINK_REQUEST_CODE || requestCode == Constants.TIME_REQUEST_CODE)
                && resultCode == RESULT_OK) {

            Date currentTime = new Date();

            String name = data.getStringExtra("name");
            double amount = data.getDoubleExtra("amount", 0.0);
            double percent = data.getDoubleExtra("percent", 0.0);
            double timeSinceDrink = (double)data.getIntExtra("time", 0);
            double prevBac = currentTimeUser.getBac();
            Date drinkTime = new Date(currentTime.getTime() - Formulas.minutesToMilli(timeSinceDrink));

            Drink newDrink = new Drink(name, drinkTime, prevBac, amount, percent);

            if (prevBac == 0.0) {
                currentTimeUser.setTimeOfLastCalc(currentTime);
            }

            double timeSinceCalc = Formulas.milliToMinutes(currentTime.getTime() - currentTimeUser.getTimeOfLastCalc().getTime());

            double bac = Formulas.calculateBac(currentTimeUser, newDrink, timeSinceCalc, timeSinceDrink);

            currentTimeUser.setBac(bac);
            currentTimeUser.setTimeOfLastCalc(currentTime);

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

            currentNight.addDrink(newDrink);
            if (history == null) history = new ArrayList<OnNight>();
            history.add(currentNight);
            currentTimeUser.setHistory(history);

            mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);

        }
    }

    public void onBACClick(View v) {
        Intent info = new Intent("INFO");
        startActivity(info);
    }


}
