package com.rathandevaki.farmersworld;

import android.content.Context;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class voiceAdapter extends FirebaseRecyclerAdapter<Voice, voiceAdapter.voiceViewholder> {
    ImageView PostPhoto;
    CircleImageView ProfilePhoto;
    private Context mContext;
    public voiceAdapter(
            @NonNull FirebaseRecyclerOptions<Voice> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "voice.xml") iwth data in
    // model class(here "voice.class")

    // Function to tell the class about the Card view (here
    // "voice.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public voiceAdapter.voiceViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_voice, parent, false);
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
                //  updateLikeInfo(model.getPrefID(),model.getUserID());
                Log.v("Likde By","123");


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
}
