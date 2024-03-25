package com.org.cleaner.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.org.cleaner.activitys.MyConnectionReceiverCleaner;
import com.org.customer.fragment.model.Cleaner;
import com.org.clinify.R;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class UploadCleaner extends Fragment implements View.OnClickListener {


    private Button btnbrowse, btnupload,edit;
    private EditText txtdata, txtdata2;
    private ImageView imgview;
    private Uri FilePathUri;
    private DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    private ProgressDialog progressDialog;
    private String userid, tokenid;
    private StorageReference storageReference2;

    private StorageReference sr;

    private EditText txtdata3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_upload_cleaner, container, false);

      /*  MyConnectionReceiverCleaner myConnectionReceiver = new MyConnectionReceiverCleaner(getContext(), btnupload);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        getActivity().registerReceiver(myConnectionReceiver, intentFilter);*/


        databaseReference = FirebaseDatabase.getInstance().getReference("Cleaner").child("profile");
        btnbrowse = v.findViewById(R.id.btnbrowse);
        btnupload = v.findViewById(R.id.btnupload);
        edit = v.findViewById(R.id.edit_button);
        txtdata = v.findViewById(R.id.txtdata);
        txtdata2 = v.findViewById(R.id.txtdata2);
        txtdata3 = v.findViewById(R.id.txtdata3);
        imgview = v.findViewById(R.id.imgView);
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        txtdata3.setText(phone);

        sr = FirebaseStorage.getInstance().getReference().child("Cleaner/" + userid + "/profile.jpg");

        sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                if (uri != null) {
                    if (getActivity() != null) {
                        Glide
                                .with(getActivity())
                                .load(uri)
                                .centerCrop()
                                .placeholder(R.drawable.ic_person)
                                .into(imgview);
                    }
                }
            }
        });


        databaseReference.child(userid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Cleaner cs = snapshot.getValue(Cleaner.class);
                        if (cs != null) {
                            if (cs.getName() != null && cs.getAddress() != null && cs.getContact_number() != null) {
                                txtdata.setText(cs.getName());
                                txtdata2.setText(cs.getAddress());
                                //txtdata3.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        progressDialog = new ProgressDialog(getContext());
        storageReference2 = FirebaseStorage.getInstance().getReference();
        edit.setOnClickListener(this);

        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });


        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if (txtdata.getText().toString().trim().equalsIgnoreCase("")) {
                    txtdata.setError("This filed cannot be empty");
                } else if (txtdata2.getText().toString().trim().equalsIgnoreCase("")) {
                    txtdata2.setError("This filed cannot be empty");
                } else if (txtdata3.getText().toString().trim().equalsIgnoreCase("")) {
                    txtdata3.setError("This filed cannot be empty");
                } else if (txtdata3.getText().length() > 13) {
                    txtdata3.setError("Phone number should be of 10 digits");
                } else {
                    UploadImage();
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public void UploadImage() {

        if (FilePathUri != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            sr.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String name = txtdata.getText().toString().trim();
                            String address = txtdata2.getText().toString().trim();
                            String contact_number = txtdata3.getText().toString().trim();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            Cleaner imageUploadInfo = new Cleaner(name, "true", address, contact_number, userid);
                            //String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child(userid).setValue(imageUploadInfo);
                            txtdata.setEnabled(false);
                            txtdata2.setEnabled(false);
                         /*   NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_cleaner);
                            navController.navigate(R.id.action_upload_cleaner_to_cleaner_save_location);*/
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.edit_button){
            txtdata.setEnabled(true);
            txtdata2.setEnabled(true);
        }
    }
}
