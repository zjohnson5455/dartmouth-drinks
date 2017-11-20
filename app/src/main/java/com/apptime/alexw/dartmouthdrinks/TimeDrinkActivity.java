package com.apptime.alexw.dartmouthdrinks;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by zacharyjohnson on 11/19/17.
 */

public class TimeDrinkActivity extends FragmentActivity {

//user enters how long ago they had the drink

    EditText back_time_edit_text;
    Button subtractButton;
    Button addButton;
    Intent result;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_drink);

        //get variables
        back_time_edit_text = findViewById(R.id.time_picker);
        subtractButton = findViewById(R.id.subtract_button);
        addButton = findViewById(R.id.add_button);

        //if you hit subtract button, reduce time since by 5 minutes, but not below 0
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTimeOff = Integer.valueOf(back_time_edit_text.getText().toString());
                if (currentTimeOff > 0){
                    currentTimeOff -= 5;
                    back_time_edit_text.setText(Integer.toString(currentTimeOff));
                }
            }
        });

        //if you hit add button, add time by 5 minutes, but checking that it will still have an effect
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTimeOff = Integer.valueOf(back_time_edit_text.getText().toString());
                if (currentTimeOff < 90){
                    currentTimeOff += 5;
                    back_time_edit_text.setText(Integer.toString(currentTimeOff));
                }
            }
        });

        result  = getIntent();
    }

    //when you hit set, send data back to calling activity
    public void onSetClick(View v) {


        int timeOff = Integer.valueOf(back_time_edit_text.getText().toString());
         result.putExtra("time", timeOff);
         setResult(RESULT_OK, result);
         finish();

    }




}
