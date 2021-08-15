package com.rathandevaki.farmersworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText u_id,password_et;
    String db_name;
    String user_id="";
    String password="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button button = (Button) findViewById(R.id.get_otp);
        u_id=(EditText) findViewById(R.id.user_id_et);
        user_id=u_id.getText().toString();
        password_et=(EditText) findViewById(R.id.password);

    }
    private void init() {
    }
    public void verifyLogin(View view)
    {
        if (TextUtils.isEmpty(u_id.getText().toString()))
        {

            u_id.setError("User Id required");
            Toasty.error(Login.this, "Empty field not allowed!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password_et.getText().toString())){
            password_et.setError("Password required");
            Toasty.error(Login.this, "Empty field not allowed!", Toast.LENGTH_SHORT).show();
        }
        else {

            final String str_uid=u_id.getText().toString();
            final String str_password=password_et.getText().toString();
            Log.v("In else ", str_uid);
            firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("UserDetails");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if(snapshot.hasChild(str_uid))
                    {
                        DatabaseReference db1=firebaseDatabase.getReference().child("UserDetails").child(str_uid);
                        db1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild("Password"))
                                {
                                   db_name=snapshot.child("Name").getValue(String.class);
                                    String db_password=snapshot.child("Password").getValue(String.class);
                                    if(db_password.equals(str_password)) {
                                        Toasty.success(Login.this, "Hello "+db_name+"", Toast.LENGTH_SHORT).show();
                                        saveUserID(str_uid);

                                        Toasty.success(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Login.this,NavDrawer.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toasty.error(Login.this, "Wrong Password.!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                    else
                    {
                        Toasty.error(getApplicationContext(),"Username or password error.!",Toasty.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }
            });

            Toast.makeText(Login.this, "Loading..", Toast.LENGTH_LONG).show();
        }

    }
    private void saveUserID(String str_uid) { // Save user ID and Plan Name in shared Preferences for future use
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserID", str_uid).apply();
        editor.putString("UserName",db_name);
        editor.commit();
        Log.v("Preference",preferences.getString("UserID", ""));

    }
    public void create_account(View view) {

        Fragment fragment = new CreateUser();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

}