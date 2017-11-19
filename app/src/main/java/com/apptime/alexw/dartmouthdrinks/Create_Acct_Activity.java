package com.apptime.alexw.dartmouthdrinks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zacharyjohnson on 11/18/17.
 */

public class Create_Acct_Activity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText pw;
    EditText confirm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acct);
        name = findViewById(R.id.create_acct_name);
        email = findViewById(R.id.create_acct_email);
        pw = findViewById(R.id.create_acct_password);
        confirm = findViewById(R.id.reconfirm_password);
    }

    public void createOnClearClick(View v) {
        name.setText("");
        email.setText("");
        pw.setText("");
        confirm.setText("");
    }

    public void onNextClick(View v) {
        Intent next = new Intent("NEXT");
        if (!pw.getText().equals(confirm.getText())){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(next);
        }
    }

    public void onSignInClick(View v) {
        Intent sign = new Intent("SIGN");
        startActivity(sign);
    }
}
