package com.org.common;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.goodiebag.pinview.Pinview;
import com.org.customer.activity.CustomersMainActivity;

import com.org.clinify.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.org.cleaner.activitys.CleanerMainActivity;


import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private String mVerificationId;
    private FirebaseAuth mAuth;
    private Button btnVerify;
    private ProgressBar progressBar;
    private TextView txtView;
    private TextView edit;
    private MyConnectionReceiver myConnectionReceiver;
    private boolean cus;

    private Pinview pin;
    private String user_id;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_verify_phone);

        mAuth = FirebaseAuth.getInstance();

        pin = findViewById(R.id.pinview);
        pin.setTextColor(android.R.color.background_dark);
       // pin.setPinBackgroundRes(R.drawable.sample_background);
        pin.showCursor(true);

        //myLayout.addView(pin);
        btnVerify = findViewById(R.id.buttonSignIn);
        progressBar = findViewById(R.id.progressbarVerify);
        txtView = findViewById(R.id.number_to_verify);
        edit = findViewById(R.id.edit);

        SharedPreferences sharedPreferencesMaps = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        String mobile = sharedPreferencesMaps.getString("contactNum", "");
        sendVerificationCode(mobile);
        txtView.setText(mobile);


        edit.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myConnectionReceiver = new MyConnectionReceiver(getApplicationContext(), Snackbar.make(findViewById(android.R.id.content),
                "Internet service is not enabled", Snackbar.LENGTH_INDEFINITE), btnVerify);
        registerReceiver(myConnectionReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private void sendVerificationCode(String mobile) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder()
                .setPhoneNumber("+91" + mobile)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(mCallbacks)
                .setActivity(this)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                pin.setValue(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {

        if (mVerificationId != null) {
            //creating the credential
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            //signing the user
            signInWithPhoneAuthCredential(credential);
        } else {
            Toast.makeText(this, "Please wait for capta", Toast.LENGTH_LONG).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profil e activity
                          /*  NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                            navController.navigate(R.id.action_verifyPhoneActivity_to_customerMapsActivity);*/

                            progressBar.setVisibility(View.GONE);

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
                    }
                });
    }

    //aniket added something
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonSignIn) {
            String code = pin.getValue();
            if (code.isEmpty() || code.length() < 6) {
                //pin.set("Enter valid code");
                Toast.makeText(this, "Enter valid code", Toast.LENGTH_SHORT).show();
                pin.requestFocus();
                return;
            }
            //verifying the code entered manually
            verifyVerificationCode(code);
        }

        if (v.getId() == R.id.edit) {
            startActivity(new Intent(getApplicationContext(), EnterPhone.class));
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myConnectionReceiver);
    }
}