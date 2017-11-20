package com.apptime.alexw.dartmouthdrinks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by zacharyjohnson on 11/18/17.
 */

public class CreateAcctActivity extends AppCompatActivity {


    final static String TAG = "Create activity";


    //set up the fields as global and get them in onCreate
    EditText mEmailEditText;
    EditText mPasswordEditText;
    EditText mConfirmPasswordEditText;
    private FirebaseAuth mAuth;
    Context mContext;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acct);

        mAuth = FirebaseAuth.getInstance();
        mContext = this;

        mEmailEditText = findViewById(R.id.create_acct_email);
        mPasswordEditText = findViewById(R.id.create_acct_password);
        mConfirmPasswordEditText = findViewById(R.id.reconfirm_password);

        //if there's already a user, then go straight to the WelcomeActivity
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent add = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(add);
            finish();
        }
    }

    //when you click on the clear button
    public void createOnClearClick(View v) {

        mEmailEditText.setText("");
        mPasswordEditText.setText("");
        mConfirmPasswordEditText.setText("");
    }

    //when you move on the next screen of the createAccount
    public void onNextClick(View v) {

        //check password matching
        if (!mPasswordEditText.getText().toString().equals(mConfirmPasswordEditText.getText().toString())){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        //otherwise, if they do match, create a user
        else {
            if (mPasswordEditText.getText().toString().length() < 6)
                Toast.makeText(getApplicationContext(), "Password must be 6 characters long", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(getApplicationContext(), "Almost there...", Toast.LENGTH_SHORT).show();
                mAuth.createUserWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(CreateAcctActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mContext, CreateNextActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(CreateAcctActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }

        }
    }

    //when you click sign in go to the SignInActivity
    public void onSignInClick(View v) {
        Intent intent = new Intent(mContext, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
