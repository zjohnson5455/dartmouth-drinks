package com.apptime.alexw.dartmouthdrinks;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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


    EditText back_time_edit_text;
    Intent result;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_drink);

        result  = getIntent();
    }

    public void onSetClick(View v) {

        back_time_edit_text = findViewById(R.id.time_picker);
        int timeOff = Integer.valueOf(back_time_edit_text.getText().toString());
//        Calendar calendar = Calendar.getInstance();
//
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int min = calendar.get(Calendar.MINUTE);
//
//
//        //TODO do something with these new times and fix midnight problem
//        if (time_off > 60) {
//            int sub_hours = time_off/60;
//            int subtract = sub_hours * 60 ;
//            int min_off = time_off - subtract;
//            int new_hour = hour - sub_hours;
//            int new_min = min - min_off;
         result.putExtra("Time off", timeOff);
         setResult(RESULT_OK, result);
         finish();

//        }
//        else {
//            int small_min = min - time_off;
//        }
//
    }




}
