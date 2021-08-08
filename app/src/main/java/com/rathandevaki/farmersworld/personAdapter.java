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

public class personAdapter extends FirebaseRecyclerAdapter<
        Person, personAdapter.personsViewholder> {
  ImageView ProductPhoto;
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
    // view (here "person.xml")
    class personsViewholder extends RecyclerView.ViewHolder {
        TextView ProductName,Quantity,Rate,Required,UserID,UserName,Verity,ctc;
        ImageView likeButton;


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
        }
    }

  /*  public void updateLikeInfo(String pref_userId,String user_id)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        DatabaseReference databaseReference;
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        final HashMap<String, Object> pushMap = new HashMap<>();
        String notificationString = pref_userId+ " Liked Your Photo";

        final String pushKey=databaseReference.push().getKey();
        pushMap.put("Notification",notificationString);
        pushMap.put("DateNtime",currentDateandTime);


      //  databaseReference.child("Notifications").child(user_id).child(pushKey).setValue(pushMap

    }*/
}