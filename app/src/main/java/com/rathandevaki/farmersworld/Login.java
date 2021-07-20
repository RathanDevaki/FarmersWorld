package com.rathandevaki.farmersworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

            String str_uid=u_id.getText().toString();
            String str_password=password_et.getText().toString();
            Log.v("In else ", str_uid);
            firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("UsersList");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if(snapshot.hasChild(str_uid))
                    {
                        DatabaseReference db1=firebaseDatabase.getReference().child("UsersList").child(str_uid);
                        db1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild("Password"))
                                {
                                    String db_password=snapshot.child("Password").getValue(String.class);
                                    if(db_password.equals(str_password)) {
                                        Toasty.success(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Login.this,HomePage.class);
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

          //  final DatabaseReference databaseReference1 = firebaseDatabase.getReference();
            Toast.makeText(Login.this, "Loading..", Toast.LENGTH_LONG).show();
        }

    }

    public void create_account(View view) {
        Intent intent=new Intent(getApplicationContext(),CreateAccount.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}