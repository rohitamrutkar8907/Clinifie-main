package com.org.customer.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.org.clinify.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GalleryFragment extends Fragment {


    public GalleryFragment() {
        // Required empty public constructor
    }

    private ImageView gallery;
    private String user_id;
    private ProgressBar progress_bar;
    private String my_cleaner;
    private String current_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        gallery = v.findViewById(R.id.gallery);
        progress_bar = v.findViewById(R.id.load_gallery_image);

        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference time_stamp_reference = FirebaseDatabase.getInstance().getReference()
                .child("Customers")
                .child("profile")
                .child(user_id)
                .child("timeStamp");

        time_stamp_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String month_year_string = getMonthYearString(snapshot);

                if (getArguments() != null) {
                    GalleryFragmentArgs args = GalleryFragmentArgs.fromBundle(getArguments());
                    current_date = args.getCurrentDate();

                }

                DatabaseReference get_my_cleaner = FirebaseDatabase.getInstance().getReference()
                        .child("Customers")
                        .child("profile")
                        .child(user_id)
                        .child("my_cleaner");
                get_my_cleaner.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        my_cleaner = snapshot.getValue(String.class);
                        StorageReference proof_image_reference = FirebaseStorage.getInstance().getReference()
                                .child("CarWashMessage/" + my_cleaner + "/" + user_id + "/" + month_year_string + "/" + current_date);
                        proof_image_reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (uri != null) {
                                    progress_bar.setVisibility(View.GONE);
                                    if (getActivity() != null) {
                                        Glide
                                                .with(getActivity())
                                                .load(uri)
                                                .centerCrop()
                                                .placeholder(R.drawable.ic_person)
                                                .into(gallery);
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progress_bar.setVisibility(View.GONE);
                            }
                        });
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

        return v;
    }

    private String getMonthYearString(DataSnapshot snapshot) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(snapshot.getValue(Long.class));
        java.util.Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String t2 = sdf.format(d);
        String date_month_array[] = t2.split("-");
        String month_year_string = date_month_array[1] + "-" + date_month_array[2];
        return month_year_string;
    }


}
