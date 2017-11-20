package com.apptime.alexw.dartmouthdrinks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DrinksActivity extends AppCompatActivity {

    private ListView lv;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseUser;
    User currentTimeUser;
    Button graphButton;
    int pos;

    List<Drink> drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        lv = findViewById(R.id.drinksListView);
        graphButton = findViewById(R.id.graphButton);

        //if you hit graph, go to the graph activity
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent graph = new Intent(getApplicationContext(), GraphActivity.class);
                graph.putExtra("ID", pos);
                startActivity(graph);
            }
        });

        mDatabase = Utils.getDatabase().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        databaseUser = Utils.getDatabase().getReference("users").child(currentUser.getUid());

        //used to get the data to set up a drinksAdapter
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentTimeUser = dataSnapshot.getValue(User.class);

                pos = getIntent().getIntExtra("ID", 0);
                Log.d("Lets go", "onDataChange: " + pos);
                drinks = currentTimeUser.getHistory().get(pos).getDrinkList();
                Log.d("Lets go", "onDataChange: " + drinks.toString());

                DrinkAdapter adapter = new DrinkAdapter(getApplicationContext(), drinks);
                if (lv != null) lv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}
