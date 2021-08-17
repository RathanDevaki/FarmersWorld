package com.rathandevaki.farmersworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class notificationAdapter extends FirebaseRecyclerAdapter<Notifi, notificationAdapter.notificationViewholder> {
    CircleImageView ProfilePhoto;
    private Context mContext;
    public notificationAdapter(
            @NonNull FirebaseRecyclerOptions<Notifi> options)
    {
        super(options);
    }

    @NonNull
    @Override
    public notificationAdapter.notificationViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_notifi, parent, false);
        return new notificationAdapter.notificationViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull notificationAdapter.notificationViewholder holder, int position, @NonNull Notifi model) {

        holder.note.setText(model.getNote());
        holder.date.setText(model.getDate());
    }

    // Sub Class to create references of the views in Crad
    // view (here "voice.xml")
    public class notificationViewholder extends RecyclerView.ViewHolder {
        TextView date,note;
        public notificationViewholder(@NonNull View itemView)
        {
            super(itemView);

             note=itemView.findViewById(R.id.name);
            date=itemView.findViewById(R.id.date);

        }
    }
}
