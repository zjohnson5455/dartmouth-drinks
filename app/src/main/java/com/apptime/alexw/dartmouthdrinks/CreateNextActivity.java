package com.apptime.alexw.dartmouthdrinks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by zacharyjohnson on 11/18/17.
 */

public class CreateNextActivity extends AppCompatActivity {

    Spinner sex = findViewById(R.id.create_acct_sex);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_next);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Sex, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sex.setAdapter(adapter);
    }

    public void onCreateClick(View v) {
        //TODO fix intents
        Intent create = new Intent("");
        startActivity(create);
    }

    public void onSignInClick(View v) {
        Intent sign = new Intent("SIGN");
        startActivity(sign);
    }

    public void onBackClick(View v) {
        Intent back = new Intent("CREATE");
        startActivity(back);
    }
}
