package com.apptime.alexw.dartmouthdrinks;

import android.app.Activity;
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

public class SignInActivity extends AppCompatActivity {

    //handles sign in to firebase

    final static String TAG = "SignInActivity";
    EditText mEmailEditText;
    EditText mPasswordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        mEmailEditText = findViewById(R.id.sign_in_email);
        mPasswordEditText = findViewById(R.id.sign_in_password);

        //if there's no user yet, get one
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent welcome = new Intent(getApplicationContext(), WelcomeActivity.class);
            startActivity(welcome);
            finish();
        }


    }

    //if you click onSignIn, match email and password to firebase database
    public void onSignInClick(View v) {

        //if there is a username and password entered

        if (mEmailEditText.getText().toString().length() != 0 && mPasswordEditText.getText().toString().length() != 0) {

            mAuth.signInWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                Intent add = new Intent(getApplicationContext(), WelcomeActivity.class);
                                startActivity(add);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        else if (mEmailEditText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "Add an email!", Toast.LENGTH_SHORT).show();
        }
        else if (mPasswordEditText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "Add a password!", Toast.LENGTH_SHORT).show();
        }


    }
    //if you click create an account, bring them to the appropriate page

    public void onCreateClick(View v) {
        Intent create = new Intent(getApplicationContext(), CreateAcctActivity.class);
        startActivity(create);
        finish();
    }

    //if you click on clear, clear the input
    public void signInOnClearClick(View v) {
        mEmailEditText.setText("");
        mPasswordEditText.setText("");
    }
}
