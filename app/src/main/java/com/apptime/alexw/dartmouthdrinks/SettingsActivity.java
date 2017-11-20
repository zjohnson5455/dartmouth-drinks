package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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



        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentTimeUser = dataSnapshot.getValue(User.class);
                mNameTextView.setText(currentTimeUser.getName().toString());
                mEmailTextView.setText(currentUser.getEmail().toString());
                mWeightTextView.setText(String.valueOf(currentTimeUser.getWeight()) + " lbs");
                if (currentTimeUser.isMale()) mSexTextView.setText("Male");
                else mSexTextView.setText("Female");

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
}
