package com.rathandevaki.farmersworld.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.rathandevaki.farmersworld.Demo;
import com.rathandevaki.farmersworld.FarmerVoice;
import com.rathandevaki.farmersworld.R;
import com.rathandevaki.farmersworld.UsersActivity;
import com.rathandevaki.farmersworld.WorkingOpportunity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    LinearLayout _add_product,_farmer_voice,_working_opportunity,_chat;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        init(root);
        return root;
    }
    public void init(View root)
    {
        _add_product=(LinearLayout) root.findViewById(R.id.main_product_exchange);
        _farmer_voice= (LinearLayout) root.findViewById(R.id.farmer_voice);
        _working_opportunity=root.findViewById(R.id.working_opportunity);
        _chat=(LinearLayout)root.findViewById(R.id.chat);
        _add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                //for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                 //   fm.popBackStack();
                //}
                Intent i = new Intent(getActivity(), Demo.class);
                startActivity(i);

               /* Fragment fragment = new ProductExchange();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, fragment);
                transaction.addToBackStack(null);
                transaction.commit();*/
            }
        });
        _farmer_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                //for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                //   fm.popBackStack();
                //}
                Intent i = new Intent(getActivity(), FarmerVoice.class);
                startActivity(i);
            }
        });
        _working_opportunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                Intent i = new Intent(getActivity(), WorkingOpportunity.class);
                startActivity(i);
            }
        });
        _chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getActivity().getSupportFragmentManager();
                Intent i = new Intent(getActivity(), UsersActivity.class);
                startActivity(i);
            }
        });


    }
}