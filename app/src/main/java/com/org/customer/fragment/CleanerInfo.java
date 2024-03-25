package com.org.customer.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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

import org.jetbrains.annotations.NotNull;

import static android.content.Context.MODE_PRIVATE;


public class CleanerInfo extends Fragment {

    //a
    private TextView address;

    //d
    private DatabaseReference databaseReference_cleaner = FirebaseDatabase.getInstance().getReference()
            .child("Cleaner").child("profile");

   /* private DatabaseReference databaseReference_customer = FirebaseDatabase.getInstance().getReference()
            .child("Customers").child("profile");*/

    //i
    private ImageView image_cleaner;

    //m
    private String my_cleaner;

    //n
    private TextView name;

    //p
    private TextView phone;

    //s
    private StorageReference storage_reference = null;

    //u
    private String user_id;

    private CardView name_card;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cleaner_info, container, false);

        name = v.findViewById(R.id.name);
        address = v.findViewById(R.id.address);
        phone = v.findViewById(R.id.phone);
        name_card = v.findViewById(R.id.name_card);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        String value = sharedPreferences.getString("value", "");
        // name.setText(value);


        databaseReference_cleaner.child(value)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        if (snapshot.child("name").getValue() != null &&
                                snapshot.child("address").getValue() != null &&
                                snapshot.child("contact_number").getValue() != null) {
                            String value1 = snapshot.child("name").getValue().toString();
                            name.setText(value1);
                            String value2 = snapshot.child("address").getValue().toString();
                            address.setText(value2);
                            String value3 = snapshot.child("contact_number").getValue().toString();
                            phone.setText(value3);
                        } else {
                            name.setText("please wait...");
                            address.setText("please wait...");
                            phone.setText("please wait...");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        image_cleaner = v.findViewById(R.id.myCleanerImage);

        storage_reference = FirebaseStorage.getInstance().getReference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StorageReference storage_references = storage_reference.child("Cleaner")
                        .child(value + "/profile.jpg");
                storage_references.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        if (uri != null) {
                            if (getActivity() != null) {
                                Glide
                                        .with(getActivity())
                                        .load(uri)
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_person)
                                        .into(image_cleaner);
                            }
                        }
                    }
                });
            }
        }, 500);


        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        name_card.clearAnimation();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       /*

        databaseReference_customer.child(user_id).child("my_cleaner")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
dataSnapshot != null
                        if (dataSnapshot != null) {

                            my_cleaner = dataSnapshot.getValue(String.class);
                            databaseReference_cleaner.child(my_cleaner)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if () {
                                                Cleaner cleaner = dataSnapshot.getValue(Cleaner.class);
                                                if (cleaner != null) {
                                                    name.setText(cleaner.getName());
                                                    address.setText(cleaner.getAddress());
                                                    phone.setText(cleaner.getContact_number());
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StorageReference storage_references = storage_reference.child("Customer")
                        .child(my_cleaner + "/profile.jpg");
                storage_references.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        if (uri != null) {
                            if (getActivity() != null) {
                                Glide
                                        .with(getActivity())
                                        .load(uri)
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_person)
                                        .into(image_cleaner);
                            }
                        }
                    }
                });
            }
        }, 500);*/
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}