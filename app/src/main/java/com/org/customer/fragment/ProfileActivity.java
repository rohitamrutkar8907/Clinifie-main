package com.org.customer.fragment;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.org.clinify.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.org.customer.fragment.model.Cleaner;
import com.org.customer.fragment.model.Customer;

import static android.content.Context.MODE_PRIVATE;

public class ProfileActivity extends Fragment {

    private String user_id;
    private TextView text_view_phone;
    private TextView car_number;
    private ImageView image_view_image_profile;
    private TextView tv_name;
    private StorageReference storage_reference;
    private ProgressBar load_profile;
    private DatabaseReference customer_details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }


        text_view_phone = v.findViewById(R.id.cust_phone);
        car_number = v.findViewById(R.id.cust_car_number);
        image_view_image_profile = v.findViewById(R.id.imageProfile);
        tv_name = v.findViewById(R.id.tv_name);
        load_profile = v.findViewById(R.id.load_profile);
        load_profile.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferencesMaps = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);

        //CusMapsActivity data
        String email_id = sharedPreferencesMaps.getString("email", "");

        customer_details = FirebaseDatabase.getInstance().getReference();
        customer_details.child("Customers").child("profile").child(user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String name = dataSnapshot.child("name").getValue().toString();
                        tv_name.setText(name);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        storage_reference = FirebaseStorage.getInstance().getReference();
        StorageReference storageReferences = storage_reference.child("Customer/" + user_id + "/profile.jpg");
        storageReferences.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null) {
                    load_profile.setVisibility(View.GONE);
                    if (getActivity() != null) {
                        Glide
                                .with(getActivity())
                                .load(uri)
                                .centerCrop()
                                .placeholder(R.drawable.ic_person)
                                .into(image_view_image_profile);
                    }
                }
            }
        });

        return v;
    }
}