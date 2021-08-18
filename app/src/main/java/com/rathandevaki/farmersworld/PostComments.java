package com.rathandevaki.farmersworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PostComments extends AppCompatActivity {


    private RecyclerView recyclerView;
    EditText commentText;
    ImageView postComment;
    commentAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Crea
    String Type; // ="FarmerVoice";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);
        String sessionId = getIntent().getStringExtra("COMMENTOR");
        Type=getIntent().getStringExtra("Type");
        Log.v("Comment - Type",sessionId+"-"+Type);

        SharedPreferences sp=this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String prefUserId=sp.getString("UserID","");
        String prefUserName=sp.getString("UserName","");


        mbase = FirebaseDatabase.getInstance().getReference().child(Type).child(sessionId).child("Comments");


        postComment=(ImageView) findViewById(R.id.postComment);
        commentText=(EditText) findViewById(R.id.commentArea);


            postComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(TextUtils.isEmpty(commentText.getText()))
                    {
                        commentText.setError("Please Enter comment");
                        return;
                    }
                    else {
                        String _commentText=commentText.getText().toString();
                        Log.v("Comm", "Clicked comment" + _commentText);
                        Log.v("Pref",prefUserId+"  "+prefUserName);
                        uploadComment(prefUserName,_commentText,Type,sessionId,prefUserId);
                    }

                }
            });

        recyclerView = findViewById(R.id.recycler5);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Comments> options
                = new FirebaseRecyclerOptions.Builder<Comments>()
                .setQuery(mbase,Comments.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new commentAdapter(options);
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
    public void uploadComment(String commtName,String commt,String Type,String dataKey,String prefUserId) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        final HashMap<String, Object> usersMap = new HashMap<>();

        usersMap.put("Comment", commt);
        usersMap.put("CommentorName", commtName);
        final String pushKey = databaseReference.push().getKey();

        databaseReference.child(Type).child(dataKey).child("Comments").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                commentText.setText("");
                updateNotification(commtName,prefUserId);
            }
        });
    }
        public void updateNotification(String cmmntName,String prefUserID){
           // Log.v("Like BY",likedBy);
          //  Log.v("Like ",likedTo);
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference drf=firebaseDatabase.getReference();
            final HashMap<String, Object> usersMap = new HashMap<>();
            final String pushKey = drf.push().getKey();
            Log.v("In Note",prefUserID);
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            Log.v("Date",date);

            usersMap.put("Date",date);
            usersMap.put("Note",cmmntName+" Comment on your Post. ");
            drf.child("Notifications").child(prefUserID).child(pushKey).setValue(usersMap);
            //complete
        }

}