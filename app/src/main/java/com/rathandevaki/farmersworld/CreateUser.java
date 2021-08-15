package com.rathandevaki.farmersworld;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class CreateUser extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1;

    String imageUrl;
    Uri imageUri;
    public static final int IMAGE_CODE = 1;
    StorageReference storageReference;

    List<String> setList;
    EasyImage easyImage;
    //  PlanSetAdapter adapter;
    int imageFlag = 0;
    final List<String> setCount = new ArrayList<>();
    int count = 0;

    Button btn;
    CircleImageView image;
    EditText et_username;
    String username="";
    EditText et_phone;
    String phone="";
    EditText et_address;
    String address="";
    EditText et_dob;
    String dob="";
    EditText et_email;
    String email="";
    EditText et_password;
    String password="";
    EditText et_adhar;
    String adhar="";
    ProgressBar progressBar;
    LinearLayout loginLayout;
    public CreateUser() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_user, container, false);

        et_username=(EditText)rootView.findViewById(R.id.userNameSignUp);
        et_password=(EditText)rootView.findViewById(R.id.passwordSignup);
        et_address=(EditText)rootView.findViewById(R.id.userAddressSignUp);
        et_phone=(EditText)rootView.findViewById(R.id.userPhoneSignUp);

        et_dob=(EditText)rootView.findViewById(R.id.userDOBSignUp);
        et_email=(EditText)rootView.findViewById(R.id.userEmailSignUp);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progress_bar);
        btn = (Button)rootView.findViewById(R.id.signUpBtn);
        loginLayout=(LinearLayout)rootView.findViewById(R.id.login_layout);


        init(rootView);

     /*   btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toasty.success(getContext(),"Created",Toasty.LENGTH_SHORT).show();
        }
});*/
   return rootView;
    }

    private void init(View rootView) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UsersList");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNotification();
                signUpUser();
            }
        });
        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });
        image=(CircleImageView)rootView.findViewById(R.id.userPhotoCreateAccount);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }


    private void selectImage() {

        easyImage = new EasyImage.Builder(getContext())
                .setCopyImagesToPublicGalleryFolder(true)
                .setFolderName("FarmersWorld")
                .allowMultiple(false)
                .build();

        easyImage.openChooser(CreateUser.this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                // onPhotosReturned(imageFiles);

                imageFlag = 1;
                imageUri = Uri.fromFile(new File(imageFiles[0].getFile().toString()));
                Log.v("TAG", "ImageUri=>" + imageUri);
                image.setImageURI(imageUri);

            }

            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                //Some error handling
                error.printStackTrace();
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {
            }
        });

    }


    private void signUpUser() {
        //SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        //creating pcs


        username=et_username.getText().toString();
        phone=et_phone.getText().toString();
        address=et_address.getText().toString();
        dob=et_dob.getText().toString();
        email=et_email.getText().toString();
        password=et_password.getText().toString();
        Log.v("Name",username);

        if (TextUtils.isEmpty(username)) {
           et_username.setError("Enter Name");
            Toasty.error(getContext(), "Enter Name").show();
        } else if (TextUtils.isEmpty(phone) || phone.length() < 10) {
            et_phone.setError("Invalid Number");
            Toasty.error(getContext(), "Invalid Number").show();
        } else if (TextUtils.isEmpty(address)) {
            et_address.setError("Enter Address");
            Toasty.error(getContext(), "Enter Address").show();
        } else if (TextUtils.isEmpty(dob)) {
           et_dob.setError("Enter DOB");
            Toasty.error(getContext(), "Enter DOB").show();

        }else if (TextUtils.isEmpty(password)) {
            et_dob.setError("Enter Password ");
            Toasty.error(getContext(), "Enter Password").show();
        }
        else if (TextUtils.isEmpty(email)) {
            et_email.setError("Enter Email");
            Toasty.error(getContext(), "Enter Email").show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            btn.setVisibility(View.GONE);
            uploadData();

        }
    }



    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        Log.v("date", currentDateandTime);
        return currentDateandTime;
    }


    private void uploadData() {
        final HashMap<String, Object> usersMap = new HashMap<>();
        storageReference = FirebaseStorage.getInstance().getReference("ProfilePhotos");
        Log.v("in upload", "yes");
        if (imageFlag == 1) {
            Log.v("in imageFlag", "yes");
            final StorageReference reference = storageReference.child(phone + ".jpg");
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.v("URL",uri.toString());
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();
                            databaseReference1 = firebaseDatabase.getReference();

                            //Its common for all users

                            usersMap.put("ProfilePhoto", uri.toString());
                            usersMap.put("Name", username);
                            usersMap.put("Phone", phone);
                            usersMap.put("EmailID",email);
                            usersMap.put("Address", address);
                            usersMap.put("DOB", dob);
                            //usersMap.put("Aadhar", adhar);
                            usersMap.put("Password",password);

                            final String pushKey = databaseReference.push().getKey();
                                databaseReference1.child("users").child(username).child("Password").setValue(password);
                                databaseReference.child("UserDetails").child(phone).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.v("present pushkey", pushKey);
                                        Toasty.success(getContext(), "Account created Succesfully.").show();
                                        progressBar.setVisibility(View.GONE);
                                        btn.setVisibility(View.GONE);

                                    }
                                });
                            }
                    });
                }
            });
        } else {

            Toasty.error(getContext(), "You haven't choose profile photo").show();
            progressBar.setVisibility(View.GONE);
            btn.setVisibility(View.VISIBLE);

        }
    }
    private void addNotification() {
        // Builds your notification
        Log.v("Not ","Notification");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getContext(), "M_CH_ID");

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo)
                .setTicker("Hearty365")
                .setPriority(Notification.PRIORITY_MAX) // this is deprecated in API 26 but you can still use for below 26. check below update for 26 API
                .setContentTitle("Default notification")
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void toLogin()
    {
        Intent i = new Intent(getActivity(), Login.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }

}