package com.rathandevaki.farmersworld.ui.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.rathandevaki.farmersworld.Demo;
import com.rathandevaki.farmersworld.FarmerVoice;
import com.rathandevaki.farmersworld.R;
import com.rathandevaki.farmersworld.UsersActivity;
import com.rathandevaki.farmersworld.WorkingOpportunity;

public class HomeFragment extends Fragment {
    Button buttonTriggerNotification;

    private HomeViewModel homeViewModel;
    LinearLayout _add_product,_farmer_voice,_working_opportunity,_chat;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        createNotificationChannel();

        triggerNotification();
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



    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.NEWS_CHANNEL_ID),getString(R.string.CHANNEL_NEWS), NotificationManager.IMPORTANCE_DEFAULT );
            notificationChannel.setDescription(getString(R.string.CHANNEL_DESCRIPTION));
            notificationChannel.setShowBadge(true);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void triggerNotification(){
        Intent intent = new Intent(getContext(), UsersActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), getString(R.string.NEWS_CHANNEL_ID))
                .setSmallIcon(R.drawable.email)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.email))
                .setContentTitle("FARMERS WORLD")
                .setContentText("New messages are available.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("New messages from One or More users. Click"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setChannelId(getString(R.string.NEWS_CHANNEL_ID))
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(getResources().getInteger(R.integer.notificationId), builder.build());


    }
}