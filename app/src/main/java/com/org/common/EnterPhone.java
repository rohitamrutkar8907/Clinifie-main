package com.org.common;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.org.customer.activity.CustomersMainActivity;
import com.org.clinify.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.org.cleaner.activitys.CleanerMainActivity;

public class EnterPhone extends AppCompatActivity implements View.OnClickListener {


    private TextInputEditText edit_text_mobile;
    private ProgressBar progressBarMain;
    private MaterialButton btnMain;
    private MyConnectionReceiver myConnectionReceiver;
    private String user_id;


    @Override
    protected void onStart() {
        super.onStart();
        myConnectionReceiver = new MyConnectionReceiver(getApplicationContext(), Snackbar.make(findViewById(android.R.id.content),
                "Internet service is not enabled", Snackbar.LENGTH_INDEFINITE), btnMain);
        registerReceiver(myConnectionReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone_number);

        edit_text_mobile = findViewById(R.id.editTextMobile);
        btnMain = findViewById(R.id.buttonContinue);
        progressBarMain = findViewById(R.id.progress_activity_main);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            LinearLayout ll  = findViewById(R.id.linear);
            ll.setBackgroundColor(Color.WHITE);
            edit_text_mobile.setVisibility(View.GONE);
            btnMain.setVisibility(View.GONE);
            progressBarMain.setVisibility(View.VISIBLE);

            user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("Users").child(user_id).child("key");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String check = snapshot.getValue(String.class);
                    if (check != null) {

                    /********************************Cleaner***********************************/
                        if (check.equals("cleaner")) {
                            DatabaseReference ref_driverAvailable = FirebaseDatabase
                                    .getInstance().getReference("DriversAvailable");

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


                        /********************************Customer***********************************/

                        if (check.equals("customer")) {
                           DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("Customers")
                                   .child("profile")
                                   .child(user_id)
                                   .child("latitude");
                           d.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            startActivity(new Intent(getApplicationContext(), CustomersMainActivity.class));
                                            finish();
                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                           });
                        }
                    }

                    /*********************************ELSE***********************************/
                    else {
                        startActivity(new Intent(getApplicationContext(), ChooseCleanerCustomer.class));
                        //startActivity(new Intent(getApplicationContext(), DemoTp.class));
                        finish();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }


            });

        }
        btnMain.setOnClickListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myConnectionReceiver);
    }


    //@Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonContinue) {


            String mobile = edit_text_mobile.getText().toString().trim();

            if (mobile.isEmpty() || mobile.length() != 10) {
                edit_text_mobile.setError("Enter a valid mobile");
                edit_text_mobile.requestFocus();
                return;
            } else {
                progressBarMain.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("contactNum", mobile);
                editor.apply();
                startActivity(new Intent(getApplicationContext(), VerifyPhoneActivity.class));
                finish();
            }
        }

    }
}