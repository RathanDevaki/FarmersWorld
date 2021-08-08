package com.rathandevaki.farmersworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class AddVoice extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;

    String imageUrl;
    Uri imageUri;
    public static final int IMAGE_CODE = 1;
    StorageReference storageReference;
    String user_name_="";

    List<String> setList;
    EasyImage easyImage;
    //  PlanSetAdapter adapter;
    int imageFlag = 0;
    final List<String> setCount = new ArrayList<>();
    int count = 0;

    Button postButton;
    ProgressBar progressBar;
    CircleImageView image;
    EditText about_post;
    EditText productName;
    String _about_post="";
    String user_id="";
    String profile_photo="";
    public  AddVoice()
    {
        //constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_add_voice, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        //  Toast.makeText(getContext(),preferences.getString("UserID",""),Toast.LENGTH_LONG).show();
        image=(CircleImageView)v.findViewById(R.id.photo_voice);
        user_id=preferences.getString("UserID","");
        about_post=(EditText)v.findViewById(R.id.about_post_voice);
        //user name and profile retrieved by UsedID
        progressBar=(ProgressBar)v.findViewById(R.id.progress_bar);
        postButton = (Button)v.findViewById(R.id.postButton_voice);

        init(v);
        return v;

    }
    public void init(View v)
    {
        firebaseDatabase = FirebaseDatabase.getInstance();


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postData();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }
    public void postData(){
        _about_post=about_post.getText().toString();

        if(TextUtils.isEmpty(_about_post))
        {
            productName.setError("Type Something");

        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            postButton.setVisibility(View.GONE);
            uploadData();

        }

    }

    private void selectImage() {

        easyImage = new EasyImage.Builder(getContext())
                .setCopyImagesToPublicGalleryFolder(true)
                .setFolderName("FarmersWorld")
                .allowMultiple(false)
                .build();

        easyImage.openChooser(AddVoice.this);


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
                //Not necessary to remove any files manually anymore
            }
        });

    }
    public void uploadData()
    { databaseReference = firebaseDatabase.getReference().child("FarmerVoice");
        databaseReference1 =firebaseDatabase.getReference();
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.v("ID",user_id);
                user_name_=snapshot.child("UserDetails").child(user_id).child("Name").getValue(String.class);
                profile_photo=snapshot.child("UserDetails").child(user_id).child("ProfilePhoto").getValue(String.class);

                Log.v("Name",user_name_);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final HashMap<String, Object> usersMap = new HashMap<>();
        storageReference = FirebaseStorage.getInstance().getReference("FarmerVoice");
        Log.v("in upload", "yes");
        if (imageFlag == 1) {
            final String pushKey = databaseReference.push().getKey();
            final StorageReference reference = storageReference.child( pushKey + ".jpg");
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.v("URL",uri.toString());
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference();

                            usersMap.put("UserName",user_name_);
                            usersMap.put("PostPhoto", uri.toString());
                            usersMap.put("ProfilePhoto", profile_photo);
                             usersMap.put("AboutPost",_about_post);
                            usersMap.put("UserID",user_id);

                            final String pushKey = databaseReference.push().getKey();

                            databaseReference.child("FarmerVoice").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.v("present pushkey", pushKey);
                                    Toasty.success(getContext(), "Posted succesfully").show();
                                    progressBar.setVisibility(View.GONE);
                                    postButton.setVisibility(View.VISIBLE);
                                    moveTofarmerVoice();

                                }
                            });
                        }
                    });
                }
            });
        } else {

            Toasty.error(getContext(), "You haven't choose photo").show();
            progressBar.setVisibility(View.GONE);
            postButton.setVisibility(View.VISIBLE);

        }
    }
    public void moveTofarmerVoice()
    {
        Fragment fragment = new ProductExchange();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.collectorPresenter, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}