package com.apptime.alexw.dartmouthdrinks;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

//graphs the drink history

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseUser;
    User currentTimeUser;

    GraphView graph;
    TextView startTimeText;
    TextView endTimeText;

    List<Drink> drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graph = (GraphView) findViewById(R.id.graph);
        startTimeText = findViewById(R.id.start_time);
        endTimeText = findViewById(R.id.end_time);

        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

       //watch fields and then use data to make a graph
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentTimeUser = dataSnapshot.getValue(User.class);

                int pos = getIntent().getIntExtra("ID", 0);

                drinks = currentTimeUser.getHistory().get(pos).getDrinkList();


                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                });

                //sort the drinks by what time they were had (could be out of order)
                Collections.sort(drinks, new Comparator<Drink>() {
                    public int compare(Drink drink1, Drink drink2) {
                        if (drink1.getTime().before(drink2.getTime())) return -1;
                        if (drink1.getTime().after(drink2.getTime())) return 1;
                        return 0;
                    }});

                Calendar start = Calendar.getInstance();
                start.setTime(drinks.get(0).getTime());
                startTimeText.setText("Start: " + start.get(Calendar.HOUR_OF_DAY) + ":" + start.get(Calendar.MINUTE));



                //loop through the drinkList and add all relevant data
                for (Drink drink:drinks) {
                    series.appendData(new DataPoint(new Date(drink.getTime().getTime()), drink.getPrevBac()), false, 100);
                    Long time = drink.getTime().getTime() + 1000; //This is weird, but having 2 identical times meant the vertical lines did not match up, so I bumped the second time on a second to allow vertical lines
                    series.appendData(new DataPoint(new Date(time), drink.getPostBac()), false, 100);
                }

                if (pos == currentTimeUser.getHistory().size()-1 && isMyServiceRunning(ForegroundService.class)) {
                    Calendar endTime = Calendar.getInstance();
                    endTimeText.setText("End:" +endTime.get(Calendar.HOUR_OF_DAY) + ":" + endTime.get(Calendar.MINUTE));
                    series.appendData(new DataPoint(new Date(), currentTimeUser.getBac()), false, 100);
                }
                else endTimeText.setText("End: 8:00");

                //add data to the graph
                graph.addSeries(series);
                graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                graph.getViewport().setScalable(true);
                graph.getViewport().setScrollable(true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
