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

public class commentAdapter extends FirebaseRecyclerAdapter<Comments, commentAdapter.commentViewholder> {
    CircleImageView ProfilePhoto;
    private Context mContext;
    public commentAdapter(
            @NonNull FirebaseRecyclerOptions<Comments> options)
    {
        super(options);
    }

    @NonNull
    @Override
    public commentAdapter.commentViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_comments, parent, false);
        return new commentAdapter.commentViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull commentAdapter.commentViewholder holder, int position, @NonNull Comments model) {

        holder._name.setText(model.getCommentorName());
        holder._comment.setText(model.getComment());
    }

    // Sub Class to create references of the views in Crad
    // view (here "voice.xml")
    public class commentViewholder extends RecyclerView.ViewHolder {
        TextView _name,_comment;
        public commentViewholder(@NonNull View itemView)
        {
            super(itemView);

           _comment=itemView.findViewById(R.id.comment);
            _name=itemView.findViewById(R.id.name);

        }
    }
}
