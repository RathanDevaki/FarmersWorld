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

public class personAdapter extends FirebaseRecyclerAdapter<
        Person, personAdapter.personsViewholder> {
  ImageView ProductPhoto;
    public String likedBy;
 // ImageView likeButton;

    private Context mContext;
   // SharedPreferences preferences =mContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

    public personAdapter(
            @NonNull FirebaseRecyclerOptions<Person> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") with data in
    // model class(here "person.class")

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_person, parent, false);
        likedBy=init(view);
        Log.v("LKB",likedBy);
        return new personAdapter.personsViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull Person model) {
        holder.ProductName.setText(model.getProductName());

        holder.Rate.setText(model.getRate());

        holder.Quantity.setText(model.getQuantity());
        holder.Verity.setText(model.getVerity());
        holder.UserName.setText(model.getUserName());
        holder.UserID.setText(model.getUserID());
        holder.Required.setText(model.getRequired());
        Picasso.get().load(model.getProductPhoto()).into(ProductPhoto);

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
        holder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Click","Comment");
                Intent intent = new Intent(view.getContext(), PostComments.class);
                intent.putExtra("COMMENTOR", model.getDataKey());
                intent.putExtra("Type","UserPost");
                view.getContext().startActivity(intent);
            }
        });

    }
    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class personsViewholder extends RecyclerView.ViewHolder {
        TextView ProductName,Quantity,Rate,Required,UserID,UserName,Verity,ctc;
        ImageView likeButton,commentButton;


        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);
            likeButton=itemView.findViewById(R.id.likeButton);
           ctc = itemView.findViewById(R.id.clickToCall);
            Verity=itemView.findViewById(R.id.verity);
            UserName=itemView.findViewById(R.id.user_name);
            UserID=itemView.findViewById(R.id.user_id);
            Required=itemView.findViewById(R.id.required);
            ProductPhoto=itemView.findViewById(R.id._post_photo);
            ProductName   = itemView.findViewById(R.id.product_name);
            Rate = itemView.findViewById(R.id.rate);
            Quantity= itemView.findViewById(R.id.quantity);
            commentButton=itemView.findViewById(R.id.commentButton);
        }
    }
    public String init(View view){
        SharedPreferences preferences =view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        likedBy= preferences.getString("UserName","");
       // Log.v("In VOice Ada",likedBy);
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
        //complete
    }
}