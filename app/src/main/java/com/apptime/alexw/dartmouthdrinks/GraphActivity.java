package com.apptime.alexw.dartmouthdrinks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GraphActivity extends AppCompatActivity {


    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseUser;
    User currentTimeUser;

    GraphView graph;

    List<Drink> drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graph = (GraphView) findViewById(R.id.graph);

        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentTimeUser = dataSnapshot.getValue(User.class);

                int pos = getIntent().getIntExtra("ID", 0);

                drinks = currentTimeUser.getHistory().get(pos).getDrinkList();

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                });

                Collections.sort(drinks, new Comparator<Drink>() {
                    public int compare(Drink drink1, Drink drink2) {
                        if (drink1.getTime().before(drink2.getTime())) return -1;
                        if (drink1.getTime().after(drink2.getTime())) return 1;
                        return 0;
                    }});

                for (Drink drink:drinks) {
                    series.appendData(new DataPoint(new Date(drink.getTime().getTime()), drink.getPrevBac()), false, 100);
                    Long time = drink.getTime().getTime() + 1000; //This is weird, but having 2 identical times meant the vertical lines did not match up, so I bumped the second time on a second to allow vertical lines
                    series.appendData(new DataPoint(new Date(time), drink.getPostBac()), false, 100);
                }

                graph.addSeries(series);
//                graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
                // activate horizontal zooming and scrolling
                graph.getViewport().setScalable(true);

                // activate horizontal scrolling
                graph.getViewport().setScrollable(true);


                //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
                //graph.getGridLabelRenderer().setNumHorizontalLabels(drinks.size()); // only 4 because of the space


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}
