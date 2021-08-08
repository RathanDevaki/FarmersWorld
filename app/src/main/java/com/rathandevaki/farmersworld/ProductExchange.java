package com.rathandevaki.farmersworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rathandevaki.farmersworld.databinding.FragmentProductExchangeBinding;

import java.util.ArrayList;

public class ProductExchange extends Fragment {

    private RecyclerView recyclerView;
    ProductAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase;

    private static final String TAG = "ProductExchange";
    View v;
    //vars
    private ArrayList<String> mproductName = new ArrayList<>();
    private ArrayList<String> mpostImages = new ArrayList<>();
    FragmentProductExchangeBinding mBinding;

    FloatingActionButton fb;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

       v= inflater.inflate(R.layout.fragment_product_exchange, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
       mbase = firebaseDatabase.getReference().child("UserPost");

        recyclerView = v.findViewById(R.id.recyclerview);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<SingleProductPost> options = new FirebaseRecyclerOptions.Builder<SingleProductPost>()
                .setQuery(mbase, SingleProductPost.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new ProductAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
        init(v);

        return v;
    }
    public void init(View v)
    {

    fb=(FloatingActionButton)v.findViewById(R.id.addProduct);
    fb.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment fragment = new AddProduct();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    });
    }

}