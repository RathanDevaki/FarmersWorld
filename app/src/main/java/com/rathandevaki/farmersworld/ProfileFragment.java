package com.rathandevaki.farmersworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button btn;
    CircleImageView image;
    TextView username,phone,address,email,adhar;

    ProgressBar progressBar;
    LinearLayout loginLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        image=(CircleImageView)v.findViewById(R.id.photo_profile);
        username=(TextView)v.findViewById(R.id.profile_name);
        address=(TextView)v.findViewById(R.id.profile_address);
        phone=(TextView)v.findViewById(R.id.profile_phone);
        email=(TextView)v.findViewById(R.id.email_id);
        adhar=(TextView)v.findViewById(R.id.adhar_number);
        btn = (Button)v.findViewById(R.id.sign_out);

        init(v);
        return v;
    }
    public void init(View v)
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String pref_userid =preferences.getString("UserID", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("UserDetails").child(preferences.getString("UserID", "")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.child("Name").getValue(String.class));
                address.setText(snapshot.child("Address").getValue(String.class));
                phone.setText(snapshot.child("Phone").getValue(String.class));
                email.setText(snapshot.child("EmailID").getValue(String.class));
                adhar.setText(snapshot.child("Aadhar").getValue(String.class));
                Glide
                        .with(getContext())
                        .load(snapshot.child("ProfilePhoto").getValue(String.class))
                        .into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UsersList");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Signing Out",Toast.LENGTH_SHORT).show();
                logOut();
            }
        });
    }
    public void logOut(){

        SharedPreferences preferences =getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();


        FragmentManager fm = getActivity().getSupportFragmentManager();

        Intent i = new Intent(getActivity(),Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }


}