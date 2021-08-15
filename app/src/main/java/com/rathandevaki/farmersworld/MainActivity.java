package com.rathandevaki.farmersworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String pref_username=sharedPreferences.getString("UserID","");
        Log.v("Preference",sharedPreferences.getString("UserID", ""));

        //Toast.makeText(this,pref_username,Toast.LENGTH_LONG).show();
        if(pref_username.equals("")) {
            Intent i = new Intent(getApplicationContext(), Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else{
            saveUserID(pref_username);

            Toasty.success(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,NavDrawer.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    private void saveUserID(String str_uid) { // Save user ID and Plan Name in shared Preferences for future use
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserID", str_uid).apply();
        editor.commit();
        Log.v("Pref wthout login",preferences.getString("UserID", ""));

    }
    public String getPrefMain(){
        SharedPreferences sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String pref_username=sharedPreferences.getString("UserID","");
        return pref_username;
    }
}