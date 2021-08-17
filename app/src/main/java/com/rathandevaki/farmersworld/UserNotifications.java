package com.rathandevaki.farmersworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserNotifications extends AppCompatActivity {

    private RecyclerView recyclerView;
    notificationAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Crea
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notifications);
        SharedPreferences sp=this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String prefUserId=sp.getString("UserID","");

        mbase = FirebaseDatabase.getInstance().getReference().child("Notifications").child(prefUserId);

        recyclerView = findViewById(R.id.recycler4);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Notifi> options
                = new FirebaseRecyclerOptions.Builder<Notifi>()
                .setQuery(mbase,Notifi.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new notificationAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);

    }
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}