package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.content.Intent;
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
                        Settings newSettings = currentTimeUser.getSettings();
                        newSettings.setFriends(b);
                        currentTimeUser.setSettings(newSettings);
                        mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                    }
                });

                organizer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Settings newSettings = currentTimeUser.getSettings();
                        newSettings.setOrganizer(b);
                        currentTimeUser.setSettings(newSettings);
                        mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        bacET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Settings newSettings = currentTimeUser.getSettings();
                if (bacET.getText().toString().length() != 0){
                    newSettings.setThreshhold(Double.valueOf(bacET.getText().toString()));
                    currentTimeUser.setSettings(newSettings);
                    mDatabase.child("users").child(currentUser.getUid()).setValue(currentTimeUser);
                }
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
}
