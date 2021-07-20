package com.rathandevaki.farmersworld;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.EasyImage;

public class CreateAccount extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String imageUrl;
    Uri imageUri;
    public static final int IMAGE_CODE = 1;
    StorageReference storageReference;
    List<String> setList;
    EasyImage easyImage;
    //  PlanSetAdapter adapter;
    int imageFlag = 0;
    final List<String> setCount = new ArrayList<>();
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

    }
}