package com.org.customer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.org.cleaner.fragment.model.Customer;
import com.org.common.EnterPhone;
import com.org.clinify.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.org.customer.fragment.bottem_sheet_fragment.ExampleBottomSheetDialog;
import com.org.customer.fragment.bottem_sheet_fragment.ExampleBottomSheetDialogPlan;
import com.razorpay.PaymentResultListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class CustomersMainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ExampleBottomSheetDialog.BottomSheetListener,
        ExampleBottomSheetDialogPlan.BottomSheetDialogPlan,
        PaymentResultListener,
        View.OnClickListener {


    private static final String TAG = "CustomersMainActivity";

    private NavController navController;

    private NavigationView navigationView;

    private DrawerLayout drawerLayout;

    private String userId;
    private String userEmail;
    private String tDay;
    private String ADate;
    private String name;
    private String years;
    private String dates;
    private String months;
    private long backpressed_time = 0L;


    private ProgressBar progressBarMain;

    private DatabaseReference users;

    private Boolean check;

    private Date todayDate;
    private Date afterDate;

    private Calendar ca1;
    private Calendar ca2;

    private ImageView profileImg;

    private StorageReference storageReference;
    private StorageReference sr;

    private Toolbar toolbar;

    private MyConnectionReceiverCustomer myConnectionReceiver;

    private TextView navUsername;
    private TextView navUseremail;

    private View headerView;

    private GoogleMap mMap;

    SharedPreferences sharedPreferencesMaps;

    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_main);

        //initialize objects
        progressBarMain = findViewById(R.id.customerMainProgress);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        navController = Navigation.findNavController(CustomersMainActivity.this, R.id.nav_host_fragment);
        navigationView.setItemIconTintList(null);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //user details
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference time_stamp = FirebaseDatabase.getInstance().getReference()
                .child("Customers")
                .child("profile")
                .child(userId);
        sharedPreferencesMaps = getSharedPreferences("SharedPrefs", MODE_PRIVATE);

        Map mp = new HashMap();
        mp.put("timeStamp", ServerValue.TIMESTAMP);

        time_stamp.updateChildren(mp);


        //headerview
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.nameHeader);
        profileImg = headerView.findViewById(R.id.profileImage);

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //register listener
        profileImg.setOnClickListener(this);

        FirebaseDatabase.getInstance().getReference().child("Customers").child("profile")
                .child(userId)
                .child("name")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        navController = Navigation.findNavController(CustomersMainActivity.this,
                                R.id.nav_host_fragment);
                        if (dataSnapshot.getValue() != null) {
                            progressBarMain.setVisibility(View.GONE);
                            navController.setGraph(R.navigation.flow_when_user_registration_completed);
                        } else {
                            progressBarMain.setVisibility(View.GONE);
                            navController.setGraph(R.navigation.flow_when_user_not_registered);
                        }
                        NavigationUI.setupWithNavController(navigationView, navController);
                        NavigationUI.setupActionBarWithNavController(CustomersMainActivity.this,
                                navController, drawerLayout);

                        navigationView.setNavigationItemSelectedListener(CustomersMainActivity.this);

                        SharedPreferences sharedPreferencesMaps = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
                        name = sharedPreferencesMaps.getString("customername", "");
                        // email = sharedPreferencesMaps.getString("email", "");
                        users = FirebaseDatabase.getInstance().getReference().child("Customers")
                                .child("profile").child(userId);
                        users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                com.org.customer.fragment.model.Customer customer = dataSnapshot.getValue(com.org.customer.fragment.model.Customer.class);
                                if (customer != null) {
                                    if (customer.getName() != null) {
                                        navUsername.setText(customer.getName());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


        StorageReference storageReferences = FirebaseStorage.getInstance().getReference()
                .child("Customer/" + userId + "/profile.jpg");

        storageReferences.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide
                        .with(getApplicationContext())
                        .load(uri)
                        .centerCrop()
                        .placeholder(R.drawable.ic_person)
                        .into(profileImg);
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }


    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp() || NavigationUI.navigateUp(navController, drawerLayout);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.qr_code:
                DatabaseReference check = FirebaseDatabase.getInstance().getReference()
                        .child("Customers").child("profile").child(userId)
                        .child("my_cleaner");
                check.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            navController.navigate(R.id.myQrCode);
                            drawerLayout.closeDrawers();
                        } else {
                            navController.navigate(R.id.showMessage);
                            drawerLayout.closeDrawers();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;

            case R.id.privacy_policy:
                DatabaseReference check2 = FirebaseDatabase.getInstance().getReference()
                        .child("Customers").child("profile").child(userId)
                        .child("my_cleaner");
                check2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            navController.navigate(R.id.privacyPolicy);
                            drawerLayout.closeDrawers();
                        } else {
                            navController.navigate(R.id.showMessage);
                            drawerLayout.closeDrawers();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;

            case R.id.work_done:
                DatabaseReference check3 = FirebaseDatabase.getInstance().getReference()
                        .child("Customers").child("profile").child(userId)
                        .child("my_cleaner");
                check3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            navController.navigate(R.id.workDones);
                            drawerLayout.closeDrawers();
                        } else {
                            navController.navigate(R.id.showMessage);
                            drawerLayout.closeDrawers();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;

            case R.id.help:
                DatabaseReference check4 = FirebaseDatabase.getInstance().getReference()
                        .child("Customers").child("profile").child(userId)
                        .child("my_cleaner");
                check4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            navController.navigate(R.id.action_mainActivitys_to_help);
                            drawerLayout.closeDrawers();
                        } else {
                            navController.navigate(R.id.showMessage);
                            drawerLayout.closeDrawers();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;


            case R.id.nav_logout:
                if (userId != null) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), EnterPhone.class));
                    finish();
                }

        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (backpressed_time + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(this, "Please press back  again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpressed_time = System.currentTimeMillis();
    }

    @Override
    public void onButtonClicked(String text) {
        navController.navigate(R.id.action_mapboxTutorial_2_to_carDetails);
    }

    @Override
    public void onButtonClickedPlan(String text) {
        if (navController.getGraph().getId() == R.id.navigation_graph2) {
            navController.navigate(R.id.action_plan1_to_paymentFragment);
        } else {
            navController.navigate(R.id.action_plan1_to_paymentActivityPlan);

        }
/*
        Thread t1 = new Thread(run1);
        t1.start();*/

    }

    Runnable run1 = new Runnable() {
        @Override
        public void run() {
            SharedPreferences sharedPreferencesMaps = getApplicationContext().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
            //CusMapsActivity data
            String days = sharedPreferencesMaps.getString("days", "");
            String month = sharedPreferencesMaps.getString("month", "");
            synchronized (this) {
                try {
                    wait(2000);
                    savedata(Integer.parseInt(days), Integer.parseInt(month));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    };

    private void savedata(final int v, final int w) {
        DatabaseReference Users = FirebaseDatabase.getInstance().getReference().child("Customers")
                .child("profile").child(userId).child("timeStamp");

        Users.addListenerForSingleValueEvent(new ValueEventListener() {

            //CusMapsActivity data
            String totalchileOfDriver = sharedPreferencesMaps.getString("temp_tot_customer_child", "");
            String lat = sharedPreferencesMaps.getString("lats", "");
            String lng = sharedPreferencesMaps.getString("longs", "");
            String driverfoundId = sharedPreferencesMaps.getString("driverFoundID", "");

            //UserDetails data
            String name = sharedPreferencesMaps.getString("customername", "");
            String email = sharedPreferencesMaps.getString("email", "");
            String contactNumber = sharedPreferencesMaps.getString("contactNum", "");

            //car details
            String carNumber = sharedPreferencesMaps.getString("carNum", "");
            String carModels = sharedPreferencesMaps.getString("carModel", "");
            String carColors = sharedPreferencesMaps.getString("carColor", "");

            DatabaseReference Users2 = FirebaseDatabase.getInstance().getReference().child("Customers")
                    .child("profile");

            DatabaseReference Cleaner = FirebaseDatabase.getInstance().getReference().child("Cleaner")
                    .child("myCustomers").child(driverfoundId);

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {

                    ca1 = new GregorianCalendar();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
                    ca1.setTimeInMillis(dataSnapshot.getValue(Long.class));
                    todayDate = ca1.getTime();
                    ca1.add(Calendar.DATE, v);
                    afterDate = ca1.getTime();
                    ca1.setTime(afterDate);

                    ca2 = new GregorianCalendar();
                    ca2.setTimeInMillis(dataSnapshot.getValue(Long.class));
                    tDay = sdf.format(ca2.getTime());
                    ca2.add(Calendar.DATE, v);
                    ADate = sdf.format(ca2.getTime());

                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setStart_date(tDay);
                    customer.setExpiry_date(ADate);
                    customer.setLatitude(lat);
                    customer.setLongtitude(lng);
                    customer.setPlan_selected(String.valueOf(v));
                    customer.setCar_model(carModels);
                    customer.setCar_color(carColors);
                    customer.setContact_number(contactNumber);
                    customer.setMy_cleaner(driverfoundId);
                    customer.setCar_number(carNumber);
                    customer.setToken("true");
                    customer.setUserId(userId);
                    customer.setTimStamp(ServerValue.TIMESTAMP);

                    dates = String.valueOf(ca1.get(Calendar.DATE));

                    int temp = ca1.get(Calendar.MONTH);
                    temp = temp + w;
                    months = String.valueOf(temp);
                    if (ca1.get(Calendar.MONTH) == 12) {
                        years = String.valueOf(ca1.get(Calendar.YEAR) + 1);
                    } else {
                        years = String.valueOf(ca1.get(Calendar.YEAR));
                    }

                    customer.setDate(dates);
                    customer.setMonth(months);
                    customer.setYear(years);


                    Map<String, Object> hash2 = new HashMap<>();
                    hash2.put(userId, customer);
                    Users2.updateChildren(hash2);


                    Map<String, Object> hash1 = new HashMap<>();
                    hash1.put(userId, customer);
                    Cleaner.updateChildren(hash1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            //Toast.makeText(getApplicationContext(), "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferencesMaps = getApplicationContext().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
            //CusMapsActivity data
            String days = sharedPreferencesMaps.getString("days", "");
            String month = sharedPreferencesMaps.getString("month", "");

            savedata(Integer.parseInt(days), Integer.parseInt(month));
            startActivity(new Intent(getApplicationContext(), CustomersMainActivity.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentError(int code, String response) {
        //Log.d(TAG, "error code : " + code);
    }


    @Override
    protected void onStart() {
        super.onStart();

        myConnectionReceiver = new MyConnectionReceiverCustomer(getApplicationContext(),
                Snackbar.make(findViewById(android.R.id.content),
                        "Internet service is not enabled", Snackbar.LENGTH_INDEFINITE), progressBarMain);
        registerReceiver(myConnectionReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myConnectionReceiver);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.profileImage) {
            DatabaseReference check = FirebaseDatabase.getInstance().getReference()
                    .child("Customers").child("profile").child(userId)
                    .child("my_cleaner");
            check.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() != null) {
                        navController.navigate(R.id.profileActivity);
                        drawerLayout.closeDrawers();
                    } else {
                        navController.navigate(R.id.showMessage);
                        drawerLayout.closeDrawers();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    /********************end*****************/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}