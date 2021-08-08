package com.rathandevaki.farmersworld;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FarmerVoice extends AppCompatActivity {


    private RecyclerView recyclerView;
    voiceAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    // Firebase Realtime Database
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_voice);
        fb=(FloatingActionButton)findViewById(R.id.addVoice);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*  Intent intent=new Intent(Demo.this,AddProduct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent); */
               fb.setVisibility(View.GONE);
                Fragment fragment = new AddVoice();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.voice_layout, fragment);
                transaction.commit();

            }
        });

        mbase = FirebaseDatabase.getInstance().getReference().child("FarmerVoice");

        recyclerView = findViewById(R.id.recycler2);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Voice> options
                = new FirebaseRecyclerOptions.Builder<Voice>()
                .setQuery(mbase,Voice.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new voiceAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
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
    public void create_account(View view) {

        Fragment fragment = new CreateUser();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.voice_layout, fragment);
        transaction.commit();
    }
}
/*"https://firebasestorage.googleapis.com/v0/b/farmersworld-c1c64.appspot.com/o/FarmerVoice%2F-MfTYNhDp_xVwVmrupt9.jpg?alt=media&token=dde0e139-1932-4b8e-bf92-5e19880a6f45"
"https://firebasestorage.googleapis.com/v0/b/farmersworld-c1c64.appspot.com/o/ProfilePhotos%2F9482972770.jpg?alt=media&token=e909f86a-a0c5-490b-8700-d3e79923cae3" */