package com.apptime.alexw.dartmouthdrinks;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseUser;
    User currentTimeUser;

    Context mContext;

    Button mSignOutButton;
    TextView mNameTextView;
    TextView mEmailTextView;
    TextView mWeightTextView;
    TextView mSexTextView;

    Switch me;
    Switch friends;
    Switch organizer;

    Settings settings;

    EditText bacET;
    EditText phoneEditText;

    Button bacButton;
    Button phoneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mContext = this;
        mAuth = FirebaseAuth.getInstance();

        mNameTextView = findViewById(R.id.nameTextView);
        mEmailTextView = findViewById(R.id.emailTextView);
        mWeightTextView = findViewById(R.id.weightTextView);
        mSexTextView = findViewById(R.id.sexTextView);

        me = findViewById(R.id.meSwitch);
        friends = findViewById(R.id.friendsSwitch);
        organizer = findViewById(R.id.organizerSwitch);
        bacET = findViewById(R.id.bacEditText);
        phoneEditText = findViewById(R.id.numberEditText);

        bacButton = findViewById(R.id.bacButton);
        phoneButton = findViewById(R.id.numberButton);







        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentTimeUser = dataSnapshot.getValue(User.class);

                settings = currentTimeUser.getSettings();
                if (settings != null) {
                    me.setChecked(settings.getMe());
                    friends.setChecked(settings.getFriends());
                    organizer.setChecked(settings.getOrganizer());
                    bacET.setText(String.valueOf(settings.getThreshhold()));
                }

                phoneEditText.setText(currentTimeUser.getFriendNumber().toString());
                mNameTextView.setText(currentTimeUser.getName().toString());
                mEmailTextView.setText(currentUser.getEmail().toString());
                mWeightTextView.setText(String.valueOf(currentTimeUser.getWeight()) + " lbs");
                if (currentTimeUser.isMale()) mSexTextView.setText("Male");
                else mSexTextView.setText("Female");

                me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Settings newSettings = currentTimeUser.getSettings();
                        newSettings.setMe(b);
                        currentTimeUser.setSettings(newSettings);
                        mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                    }
                });

                friends.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                    Manifest.permission.SEND_SMS)
                                    != PackageManager.PERMISSION_GRANTED) {

                                // if not, request it, tell user it is needed
                                Toast.makeText(getApplicationContext(), "SMS needed to activate option", Toast.LENGTH_SHORT).show();
                                ActivityCompat.requestPermissions(SettingsActivity.this,
                                        new String[]{Manifest.permission.SEND_SMS},
                                        Constants.PERMISSIONS_REQUEST_SMS_FRIEND);
                            }
                            else {
                                Settings newSettings = currentTimeUser.getSettings();
                                newSettings.setFriends(b);
                                currentTimeUser.setSettings(newSettings);
                                mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                            }
                        }
                        else {
                            Settings newSettings = currentTimeUser.getSettings();
                            newSettings.setFriends(b);
                            currentTimeUser.setSettings(newSettings);
                            mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                        }
                    }
                });

                bacButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        double bac = Double.valueOf(bacET.getText().toString());
                        if (currentTimeUser != null){
                            settings = currentTimeUser.getSettings();
                            settings.setThreshhold(bac);
                            currentTimeUser.setSettings(settings);
                            mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                        }
                    }
                });

                phoneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String number = phoneEditText.getText().toString();
                        if (currentTimeUser != null){
                            currentTimeUser.setFriendNumber(number);
                            mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                        }
                    }
                });

                organizer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                    Manifest.permission.SEND_SMS)
                                    != PackageManager.PERMISSION_GRANTED) {

                                // if not, request it, tell user it is needed
                                Toast.makeText(getApplicationContext(), "SMS needed to activate option", Toast.LENGTH_SHORT).show();
                                ActivityCompat.requestPermissions(SettingsActivity.this,
                                        new String[]{Manifest.permission.SEND_SMS},
                                        Constants.PERMISSIONS_REQUEST_SMS_ORG);
                            }
                            else if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                    Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {

                                // if not, request it, tell user it is needed
                                Toast.makeText(getApplicationContext(), "Location needed to activate option", Toast.LENGTH_SHORT).show();
                                ActivityCompat.requestPermissions(SettingsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        Constants.PERMISSIONS_REQUEST_FINE_LOCATION);
                            }
                            else {
                                Settings newSettings = currentTimeUser.getSettings();
                                newSettings.setOrganizer(b);
                                currentTimeUser.setSettings(newSettings);
                                mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                            }
                        }
                        else {
                            Settings newSettings = currentTimeUser.getSettings();
                            newSettings.setOrganizer(b);
                            currentTimeUser.setSettings(newSettings);
                            mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                        }
                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        mSignOutButton = findViewById(R.id.sign_out_button);
        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(mContext, SignInActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }


    public void onExplainClick(View v) {
        Intent explain = new Intent();
        explain.setClass(getApplicationContext(), ExplainActivity.class);
        startActivity(explain);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_SMS_FRIEND: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Settings newSettings = currentTimeUser.getSettings();
                    newSettings.setFriends(true);
                    currentTimeUser.setSettings(newSettings);
                    mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                }
            }
            case Constants.PERMISSIONS_REQUEST_SMS_ORG: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        // if not, request it, tell user it is needed
                        Toast.makeText(getApplicationContext(), "Location needed to activate option", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(SettingsActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                Constants.PERMISSIONS_REQUEST_FINE_LOCATION);
                    }
                    else {
                        Settings newSettings = currentTimeUser.getSettings();
                        newSettings.setOrganizer(true);
                        currentTimeUser.setSettings(newSettings);
                        mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                    }
                }
            }
            case Constants.PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Settings newSettings = currentTimeUser.getSettings();
                    newSettings.setOrganizer(true);
                    currentTimeUser.setSettings(newSettings);
                    mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                }
            }
        }
    }
}