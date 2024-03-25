package com.org.cleaner.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.org.cleaner.fragment.model.Customer;
import com.org.cleaner.fragment.model.Date;
import com.org.clinify.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class SavePictureAndProof extends Fragment {

    private ImageView imageView;
    public static final int RequestPermissionCode = 1;
    private String user_id;
    private ProgressBar progress_bar;
    private Customer cu;


    private String customer_uid;
    private DatabaseReference car_wash_message;
    private DatabaseReference how_many_times_work_done;

    private String month_year_string;
    private String t2;
    private int count = 0;
    private String latitude;
    private String longititude;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.save_picture_and_proof, container, false);
        imageView = v.findViewById(R.id.imageView);
        progress_bar = v.findViewById(R.id.progressBar4);

        if (getArguments() != null) {
            SavePictureAndProofArgs args = SavePictureAndProofArgs.fromBundle(getArguments());
            cu = args.getCustomer();
            latitude = args.getLatitude();
            longititude = args.getLongititude();
        }
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        customer_uid = cu.getUserId();

        EnableRuntimePermission();
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);

        return v;
    }

    private String getMonthYearString(DataSnapshot snapshot) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(snapshot.getValue(Long.class));
        java.util.Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        t2 = sdf.format(d);
        String date_month_array[] = t2.split("-");
        month_year_string = date_month_array[1] + "-" + date_month_array[2];
        return month_year_string;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);


            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference()
                    .child("Cleaner")
                    .child("timeStamp");

            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    /*****************************************SAVING DATA PROOF OF CLEANER*****************************/
                    String month_year_string = getMonthYearString(snapshot);
                    how_many_times_work_done = FirebaseDatabase.getInstance().getReference()
                            .child("Workdone")
                            .child(user_id)
                            .child(customer_uid)
                            .child(month_year_string);

                    car_wash_message = FirebaseDatabase.getInstance().
                            getReference("CarWashMessage")
                            .child(user_id)
                            .child(customer_uid)
                            .child(month_year_string)
                            .child(t2).child("latitude");

                    car_wash_message.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() == null) {

                                car_wash_message = FirebaseDatabase.getInstance().
                                        getReference("CarWashMessage")
                                        .child(user_id)
                                        .child(customer_uid)
                                        .child(month_year_string)
                                        .child(t2);

                                Date d = new Date(latitude, longititude, "done");
                                car_wash_message.setValue(d);
                                car_wash_message.child("timeStamp").setValue(ServerValue.TIMESTAMP);

                                car_wash_message = FirebaseDatabase.getInstance().
                                        getReference("CarWashMessage")
                                        .child(user_id)
                                        .child(customer_uid)
                                        .child(month_year_string);

                                car_wash_message.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot d : snapshot.getChildren()) {
                                            count++;
                                        }
                                        how_many_times_work_done.setValue(count);
                                        progress_bar.setVisibility(View.GONE);
                                        count = 0;
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                /*****************************************SAVING DATA PROOF OF CLEANER*****************************/


                                /*****************************************SAVING IMAGE PROOF OF CLEANER*****************************/
                                StorageReference proof_image_reference = FirebaseStorage.getInstance().getReference()
                                        .child("CarWashMessage/" + user_id + "/" + customer_uid + "/" + month_year_string + "/" + t2);

                                progress_bar.setVisibility(View.VISIBLE);
                                Uri tempUri = getImageUri(getContext(), bitmap);
                                proof_image_reference.putFile(tempUri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                progress_bar.setVisibility(View.GONE);
                                                Toast.makeText(getActivity(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                                            }
                                        });
                                /*****************************************SAVING IMAGE PROOF OF CLEANER*****************************/

                            } else {
                                Toast.makeText(getActivity(), "already saved", Toast.LENGTH_SHORT).show();
                                progress_bar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            Toast.makeText(getActivity(), "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] result) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}



