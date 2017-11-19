package com.apptime.alexw.dartmouthdrinks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zacharyjohnson on 11/18/17.
 */

public class SignInActivity extends Activity {

    EditText name = findViewById(R.id.sign_in_name);
    EditText pw = findViewById(R.id.sign_in_password);


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

    }

    public void onSignInClick(View v) {
        Intent add = new Intent(getApplicationContext(), AddActivity.class);
        startActivity(add);
        finish();
    }

    public void onCreateClick(View v) {
        Intent create = new Intent("CREATE");
        startActivity(create);
        finish();
    }

    public void signInOnClearClick(View v) {
        name.setText("");
        pw.setText("");
    }
}
