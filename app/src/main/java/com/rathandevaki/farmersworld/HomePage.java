package com.rathandevaki.farmersworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import es.dmoral.toasty.Toasty;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void productExchange(View view) {
        Toasty.success(HomePage.this,"Product exchange",Toasty.LENGTH_SHORT).show();
    }

    public void farmerVoice(View view) {
        Toasty.success(HomePage.this,"Product exchange",Toasty.LENGTH_SHORT).show();
    }

    public void workingOpportunity(View view) {
        Toasty.success(HomePage.this,"Product exchange",Toasty.LENGTH_SHORT).show();
    }
}