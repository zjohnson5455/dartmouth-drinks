package com.apptime.alexw.dartmouthdrinks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by zacharyjohnson on 11/18/17.
 */

public class Create_Acct_Activity extends Activity{

    EditText name = findViewById(R.id.create_acct_name);
    EditText pw = findViewById(R.id.create_acct_password);
    EditText confirm = findViewById(R.id.reconfirm_password);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acct);
    }

    public void createOnClearClick(View v) {
        name.setText("");
        pw.setText("");
        confirm.setText("");
    }

    public void onNextClick(View v) {
        Intent next = new Intent("NEXT");
        startActivity(next);
    }

    public void onSignInClick(View v) {
        Intent sign = new Intent("SIGN");
        startActivity(sign);
    }
}
