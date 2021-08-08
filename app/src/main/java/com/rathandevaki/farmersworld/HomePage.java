package com.rathandevaki.farmersworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void productExchange(View view) {
        Intent i=new Intent(HomePage.this,NavDrawer.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        Toasty.success(HomePage.this,"Product exchange",Toasty.LENGTH_SHORT).show();
    }
}