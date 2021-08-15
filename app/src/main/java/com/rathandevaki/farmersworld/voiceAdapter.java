package com.rathandevaki.farmersworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class voiceAdapter extends FirebaseRecyclerAdapter<Voice, voiceAdapter.voiceViewholder> {
    ImageView PostPhoto;
    CircleImageView ProfilePhoto;
    public String likedBy;
    private Context mContext;

    public voiceAdapter(

            @NonNull FirebaseRecyclerOptions<Voice> options)
    {
        super(options);

    }

    @NonNull
    @Override
    public voiceAdapter.voiceViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_voice, parent, false);
        likedBy=init(view);
        Log.v("LKB",likedBy);
        return new voiceAdapter.voiceViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull voiceAdapter.voiceViewholder holder, int position, @NonNull Voice model) {

        holder.UserName.setText(model.getUserName());
        holder.UserID.setText(model.getUserID());
        holder.AboutPost.setText(model.getAboutPost());
     /* holder.ProductPhoto.setImageURI(model.getProductPhoto());
        Glide
                .with(mContext)
                .load(model.getProductPhoto())

                .into(ProductPhoto);*/
        Picasso.get().load(model.getPostPhoto()).into(PostPhoto);
        Picasso.get().load(model.getProfilePhoto()).into(ProfilePhoto);

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.likeButton.setImageResource(R.drawable.ic_baseline_like_red_24);
                updateLikeInfo(likedBy,model.getUserID());
                Log.v("Likde By",likedBy);


            }
        });
        holder.ctc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + model.getUserID()));
                view.getContext().startActivity(intent);
            }
        });

    }

    // Sub Class to create references of the views in Crad
    // view (here "voice.xml")
    public class voiceViewholder extends RecyclerView.ViewHolder {
        TextView AboutPost,UserID,UserName,ctc;
        ImageView likeButton;

        public voiceViewholder(@NonNull View itemView)
        {
            super(itemView);
            PostPhoto=itemView.findViewById(R.id._post_photo);
            AboutPost=itemView.findViewById(R.id.about_post);
            ProfilePhoto=itemView.findViewById(R.id.photo_profile);
            ctc = itemView.findViewById(R.id.clickToCall);
            likeButton=itemView.findViewById(R.id.likeButton);
            UserName=itemView.findViewById(R.id.user_name);
            UserID=itemView.findViewById(R.id.user_id);

        }
    }
    public String init(View view){
        SharedPreferences preferences =view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        likedBy= preferences.getString("UserName","");
        Log.v("In VOice Ada",likedBy);
        return likedBy;
    }
    public void updateLikeInfo(String likedBy,String likedTo){
        Log.v("Like BY",likedBy);
        Log.v("Like to",likedTo);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference drf=firebaseDatabase.getReference();
        final HashMap<String, Object> usersMap = new HashMap<>();
        final String pushKey = drf.push().getKey();

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        Log.v("Date",date);

        usersMap.put("Date",date);
        usersMap.put("Note",likedBy+" Liked Your Post. ");
        drf.child("Notifications").child(likedTo).child(pushKey).setValue(usersMap);
    }
}
