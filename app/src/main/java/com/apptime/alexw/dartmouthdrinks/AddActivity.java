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

public class AddActivity extends AppCompatActivity {

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
    User user;


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

                User user = getUser();
                mBACTextView.setText(String.valueOf(user.getBac()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == Constants.ADD_DRINK_REQUEST_CODE || requestCode == Constants.TIME_REQUEST_CODE)
                && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            double amount = data.getDoubleExtra("amount", 0.0);
            double percent = data.getDoubleExtra("percent", 0.0);
            double alcohol = Formulas.drinkAlcoholContent(amount, percent);
            double prevBac = 0.0; // placeholder
            int timeSinceCalc = 0;
            int timeSinceDrink = data.getIntExtra("time", 0);
            Log.d("BAC", name);
            double bac = Formulas.calculateBac(true, 135, prevBac, alcohol, timeSinceCalc, timeSinceDrink);
            Log.d("BAC", Double.toString(alcohol));
            Log.d("BAC", Double.toString(bac));
            Toast.makeText(getApplicationContext(), Double.toString(bac), Toast.LENGTH_SHORT).show();
        }
    }

    public void onBACClick(View v) {
        Intent info = new Intent("INFO");
        startActivity(info);
    }

    public User getUser(){


        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            User user;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);

                //mDatabase.child("users").child(currentUser.getUid()).setValue(user);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return user;
    }
}
