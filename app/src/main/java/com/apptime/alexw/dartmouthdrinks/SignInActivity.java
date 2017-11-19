package com.apptime.alexw.dartmouthdrinks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class SignInActivity extends Activity {

    final static String TAG = "SignInActivity";
    EditText mEmailEditText;
    EditText mPasswordEditText;
    private FirebaseAuth mAuth;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        mEmailEditText = findViewById(R.id.sign_in_email);
        mPasswordEditText = findViewById(R.id.sign_in_password);

        if (mAuth.getCurrentUser() != null){
            Intent add = new Intent(getApplicationContext(), AddActivity.class);
            startActivity(add);
            finish();
        }



    }

    public void onSignInClick(View v) {

        mAuth.signInWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent add = new Intent(getApplicationContext(), AddActivity.class);
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

    public void onCreateClick(View v) {
        Intent create = new Intent("CREATE");
        startActivity(create);
        finish();
    }

    public void signInOnClearClick(View v) {
        mEmailEditText.setText("");
        mPasswordEditText.setText("");
    }
}
