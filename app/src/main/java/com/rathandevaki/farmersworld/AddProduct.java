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

public class AddProduct extends Fragment {
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
    EditText productName,verity,quantity,required,rate;
    String _productName= " ";
    String _verity="";
    String _quantity="";
    String _rate="";
    String _required="";

    String user_id="";
    public void AddProduct()
    {
        //constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_add_product, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
      //  Toast.makeText(getContext(),preferences.getString("UserID",""),Toast.LENGTH_LONG).show();
        image=(CircleImageView)v.findViewById(R.id.addPhoto);
        user_id=preferences.getString("UserID","");
        productName=(EditText)v.findViewById(R.id.productName);
        verity=(EditText)v.findViewById(R.id.verity);
        quantity=(EditText)v.findViewById(R.id.quantity);
        required=(EditText)v.findViewById(R.id.required);
        rate=(EditText)v.findViewById(R.id.rate);

        progressBar=(ProgressBar)v.findViewById(R.id.progress_bar);
        postButton = (Button)v.findViewById(R.id.postButton);

        init(v);
        return v;

    }
    public void init(View v)
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UsersList");

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
        _productName=productName.getText().toString();
        _verity=verity.getText().toString();
        _quantity=quantity.getText().toString();
        _required=required.getText().toString();
        _rate=rate.getText().toString();

        if(TextUtils.isEmpty(_productName))
        {
           productName.setError("Enter Product Name");
            Toasty.error(getContext(), "Enter Product Name").show();
        }

        else if(TextUtils.isEmpty(_quantity))
        {
            productName.setError("Enter Quantity");

        }
        else if(TextUtils.isEmpty(_verity))
        {
            productName.setError("Enter Verity");

        }
        else if(TextUtils.isEmpty(_required))
        {
            productName.setError("Enter Required Product");

        }
        else if(TextUtils.isEmpty(_rate))
        {
            productName.setError("Enter Rate in Rs.");

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

        easyImage.openChooser(AddProduct.this);


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
    {
        databaseReference1 =firebaseDatabase.getReference();
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.v("ID",user_id);
                user_name_=snapshot.child("UserDetails").child(user_id).child("Name").getValue(String.class);
                Log.v("Name",user_name_);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final HashMap<String, Object> usersMap = new HashMap<>();
        storageReference = FirebaseStorage.getInstance().getReference("ProductPost");
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
                            usersMap.put("ProductPhoto", uri.toString());
                            usersMap.put("ProductName", _productName);
                            usersMap.put("Verity", _verity);
                            usersMap.put("Quantity",_quantity);
                            usersMap.put("Required", _required);
                            usersMap.put("Rate", _rate);
                            usersMap.put("UserID",user_id);

                            final String pushKey = databaseReference.push().getKey();
                            usersMap.put("DataKey",pushKey);
                            databaseReference.child("UserPost").child(pushKey).setValue(usersMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.v("present pushkey", pushKey);
                                    Toasty.success(getContext(), "Posted succesfully").show();
                                    progressBar.setVisibility(View.GONE);
                                    postButton.setVisibility(View.VISIBLE);
                                   moveToProductExchange();

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
    public void moveToProductExchange()
    {
        Fragment fragment = new ProductExchange();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.collectorPresenter, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

