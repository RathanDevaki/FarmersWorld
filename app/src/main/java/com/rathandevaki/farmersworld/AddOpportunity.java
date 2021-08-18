package com.rathandevaki.farmersworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rathandevaki.farmersworld.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import pl.aprilapps.easyphotopicker.EasyImage;


public class AddOpportunity extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;

    String imageUrl;
    Uri imageUri;
    public static final int IMAGE_CODE = 1;
    StorageReference storageReference;
    String user_name_ = "";

    List<String> setList;
    EasyImage easyImage;
    //  PlanSetAdapter adapter;
    int imageFlag = 0;
    final List<String> setCount = new ArrayList<>();
    int count = 0;


    Button postButton;
    ProgressBar progressBar;
    CircleImageView image;

    String _job_description = "";
    String user_id = "";

    String profile_photo = "";

    String _job_duration="";
    String _job_salary="";
    String _job_vacancies="";
    String _job_location="";

    EditText job_salary;
    EditText job_location;
    EditText job_vacancies;
    EditText job_duration;
    EditText job_description;
    public AddOpportunity() {
        //constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_opportunity, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        //  Toast.makeText(getContext(),preferences.getString("UserID",""),Toast.LENGTH_LONG).show();

        user_id = preferences.getString("UserID", "");
        job_description = (EditText) v.findViewById(R.id.job_desctiption);
        job_location = (EditText)v.findViewById(R.id.job_location);
        job_vacancies=(EditText)v.findViewById(R.id.job_vacancies);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        postButton = (Button) v.findViewById(R.id.postButton_opportunity);
        job_duration=(EditText)v.findViewById(R.id.job_duration);
        job_salary=(EditText)v.findViewById(R.id.job_salary);
        init(v);
        return v;

    }

    public void init(View v) {
        firebaseDatabase = FirebaseDatabase.getInstance();


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postData();
            }
        });

    }

    public void postData() {
        Log.v("Tag","In PostData ");
       _job_description = job_description.getText().toString();
       _job_duration=job_duration.getText().toString();
       _job_location= job_location.getText().toString();
       _job_salary=job_salary.getText().toString();
       _job_vacancies=job_vacancies.getText().toString();
        if (TextUtils.isEmpty(_job_description))
        {
           job_description.setError("Type Something");

        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            postButton.setVisibility(View.GONE);
            getUserDetails();


        }

    }
    public void getUserDetails()
    {
        databaseReference1 = firebaseDatabase.getReference();
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.v("ID", user_id);
                user_name_ = snapshot.child("UserDetails").child(user_id).child("Name").getValue(String.class);
                profile_photo = snapshot.child("UserDetails").child(user_id).child("ProfilePhoto").getValue(String.class);

                Log.v("Name", user_name_);
                uploadData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void uploadData() {

        final HashMap<String, Object> usersMap = new HashMap<>();
        storageReference = FirebaseStorage.getInstance().getReference("JobOpportunity");
        databaseReference = firebaseDatabase.getReference().child("JobOpportunity");

                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();

                            usersMap.put("JobDescription", _job_description);
                            usersMap.put("JobDuration", _job_duration);
                            usersMap.put("Salary", _job_salary);
                            usersMap.put("Location", _job_location);
                            usersMap.put("Vacancies", _job_vacancies);
                            usersMap.put("UserName", user_name_);
                            usersMap.put("ProfilePhoto", profile_photo);

                            usersMap.put("UserID", user_id);

                            final String pushKey = databaseReference.push().getKey();
                            usersMap.put("DataKey",pushKey);
                            databaseReference.child("JobOpportunity").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.v("present pushkey", pushKey);
                                    Toasty.success(getContext(), "Posted succesfully").show();
                                    progressBar.setVisibility(View.GONE);
                                    postButton.setVisibility(View.VISIBLE);
                                    moveToOpportunity();

                                }
                            });

    }

    public void moveToOpportunity() {
        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.opportunity_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}