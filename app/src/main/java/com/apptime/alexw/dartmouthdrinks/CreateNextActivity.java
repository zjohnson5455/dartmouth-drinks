package com.apptime.alexw.dartmouthdrinks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by zacharyjohnson on 11/18/17.
 */

public class CreateNextActivity extends Activity {

    Spinner mSexSpinner;
    EditText mNameEditText;
    EditText mWeightEditText;

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_next);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        mSexSpinner = findViewById(R.id.create_acct_sex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Sex, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSexSpinner.setAdapter(adapter);

        mNameEditText = findViewById(R.id.nameEditText);
        mWeightEditText = findViewById(R.id.weightEditText);


    }

    public void onCreateClick(View v) {
        //TODO fix intents

        if (mNameEditText.getText().toString().length() != 0 && mWeightEditText.getText().toString().length() != 0 && !mSexSpinner.getSelectedItem().toString().equals("---")){
            //do a thing: user initialise

            String UID = currentUser.getUid();
            String name = mNameEditText.getText().toString();
            int weight = Integer.valueOf(mWeightEditText.getText().toString());
            Boolean maleBool = mSexSpinner.getSelectedItem().toString().equals("Male");

            User user = new User(UID, name, weight, maleBool);
            mDatabase.child("users").child(currentUser.getUid()).setValue(user);
            Intent add = new Intent(getApplicationContext(), AddActivity.class);
            startActivity(add);
            finish();
        }
        else if (mNameEditText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "Enter a name, we want to know who you are!", Toast.LENGTH_SHORT).show();
        }
        else if (mWeightEditText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "Enter a weight, we won't tell!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Enter a sex!", Toast.LENGTH_SHORT).show();
        }




    }

}
