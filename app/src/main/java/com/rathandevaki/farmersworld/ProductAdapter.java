package com.rathandevaki.farmersworld;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProductAdapter extends FirebaseRecyclerAdapter<SingleProductPost,ProductAdapter.productViewholder> {
    public String likedBy;

    public ProductAdapter(@NonNull FirebaseRecyclerOptions<SingleProductPost> options)
    {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull productViewholder holder,
                                    int position, @NonNull SingleProductPost model)
    {

        holder.ProductName.setText(model.getProductName());

        holder.Verity.setText(model.getVerity());
        holder.Quantity.setText(model.getQuantity());
    }
    @NonNull
    public productViewholder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_product_post, parent, false);

        return new productViewholder(view);
    }
    static class productViewholder extends RecyclerView.ViewHolder {
        TextView ProductName, Verity, Quantity;
        public productViewholder(@NonNull View itemView)
        {
            super(itemView);

            ProductName = itemView.findViewById(R.id.product_name);
            Verity= itemView.findViewById(R.id.verity);
            Quantity= itemView.findViewById(R.id.quantity);
        }
    }


}
