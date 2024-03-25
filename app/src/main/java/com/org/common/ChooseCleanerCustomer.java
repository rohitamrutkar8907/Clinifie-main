package com.org.common;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.org.clinify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.org.cleaner.activitys.CleanerMainActivity;
import com.org.customer.activity.CustomersMainActivity;

public class ChooseCleanerCustomer extends AppCompatActivity implements View.OnClickListener {


    Button customer;
    Button cleaner;
    DatabaseReference ref_driverAvailable = FirebaseDatabase.getInstance().getReference("DriversAvailable");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cleaner_customer2);

        customer = findViewById(R.id.customer);
        cleaner = findViewById(R.id.cleaner);

        customer.setOnClickListener(this);
        cleaner.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        final String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(user_id).child("key");


        if (v.getId() == R.id.customer) {
            databaseReference.setValue("customer");
            startActivity(new Intent(getApplicationContext(), CustomersMainActivity.class));
            finish();
        }

        if (v.getId() == R.id.cleaner) {
            databaseReference.setValue("cleaner");
            ref_driverAvailable.child(FirebaseAuth.getInstance()
                    .getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                startActivity(new Intent(getApplicationContext(), CleanerMainActivity.class));
                                finish();

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
        }
    }
}
