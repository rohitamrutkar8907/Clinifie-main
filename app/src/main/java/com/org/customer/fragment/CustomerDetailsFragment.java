package com.org.customer.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.org.clinify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.org.customer.fragment.model.Cleaner;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

import com.org.customer.fragment.model.Customer;

import org.jetbrains.annotations.NotNull;


public class CustomerDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CustomerDetailsFragment";
    private static final int REQUEST_PHONE_CALL = 999;

    private String userId;
    private String contact;
    private String my_cleaner;
    private String phone_number;

    private ImageView whatsapp;
    private ImageView phone;
    private ImageView info;
    private ImageView cleanerImage;
    private ImageView status_image;

    private CardView cardView;
    private Customer customers;

    Button recharge;

    //customer info
    DatabaseReference databaseReference, dbref;
    DatabaseReference rootRef;
    TextView planStatus3;
    TextView planStatus4;
    TextView days;
    TextView status_text;
    TextView time;
    private String month_year_string;
    private StorageReference storage_reference = null;
    private SharedPreferences sharedPreferences1;
    private SharedPreferences sharedPreferences2;
    private Handler h = new Handler();
    SharedPreferences.Editor editor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences1 = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        sharedPreferences2 = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_details_customer, container, false);
        //planStatus1 = v.findViewById(R.id.planStatus1);
        //planStatus2 = v.findViewById(R.id.planStatus2);

        cleanerImage = v.findViewById(R.id.myCleanerImage);
        days = v.findViewById(R.id.days);
        recharge = v.findViewById(R.id.recharge_cus_det);
        whatsapp = v.findViewById(R.id.whatsapp);
        phone = v.findViewById(R.id.phone);
        info = v.findViewById(R.id.info);
        status_text = v.findViewById(R.id.status_text);

        storage_reference = FirebaseStorage.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        rootRef = databaseReference.child("Customers").child("profile").child(userId);

        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.top_animation);
        Animation anim2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        anim2.setRepeatCount(-1);
        cleanerImage.setAnimation(anim);
        days.setAnimation(anim2);

        recharge.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        phone.setOnClickListener(this);
        info.setOnClickListener(this);

        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        readData(new FirebaseCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCallBack(Customer customer) {


                if (customer.getMy_cleaner() != null &&
                        customer.getCar_color() != null &&
                        customer.getCar_number() != null &&
                        customer.getContact_number() != null &&
                        customer.getTimeStamp() != null &&
                        customer.getDate() != null
                        && customer.getCar_model() != null) {

                    my_cleaner = customer.getMy_cleaner();

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
                                            .into(cleanerImage);
                                }
                            }
                        }
                    });

                    if (getActivity() != null) {
                        editor = sharedPreferences1.edit();
                        editor.putString("carNum", customer.getCar_number());
                        editor.putString("carModel", customer.getCar_model());
                        editor.putString("customername", customer.getName());
                        editor.putString("carColor", customer.getCar_color());
                        editor.putString("days", customer.getDate());
                        editor.putString("month", customer.getMonth());
                        editor.putString("my_cleaner", customer.getMy_cleaner());
                        editor.apply();

                    }

                    Calendar ca1 = Calendar.getInstance();
                    ca1.setTimeInMillis(customer.getTimeStamp());

                    Calendar ca2 = Calendar.getInstance();
                    ca2.set(Integer.parseInt(customer.getYear()),
                            Integer.parseInt(customer.getMonth()) - 1,
                            Integer.parseInt(customer.getDate()) + 1);


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String dateBeforeString = sdf.format(ca1.getTime());
                    String dateAfterString = sdf.format(ca2.getTime());

                    //Parsing the date
                    LocalDate dateBefore = LocalDate.parse(dateBeforeString);
                    LocalDate dateAfter = LocalDate.parse(dateAfterString);

                    //calculating number of days in between

                    long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);

                    DatabaseReference dfs = FirebaseDatabase.getInstance().getReference()
                            .child("Cleaner")
                            .child("myCustomers")
                            .child(my_cleaner)
                            .child(userId);

                    if (noOfDaysBetween >= 1) {
                        days.setText(String.valueOf(noOfDaysBetween));
                    } else {
                        days.setText("0");
                        dfs.setValue(null);
                        recharge.setVisibility(View.VISIBLE);
                    }

                }

                Calendar ca1 = Calendar.getInstance();
                if (customer.getTimeStamp() != null) {
                    ca1.setTimeInMillis(customer.getTimeStamp());
                    SimpleDateFormat sdfs = new SimpleDateFormat("dd-MM-yyyy");
                    String check_date = sdfs.format(ca1.getTime());
                    String date_month_array[] = check_date.split("-");
                    month_year_string = date_month_array[1] + "-" + date_month_array[2];
                    DatabaseReference check_ref = FirebaseDatabase.getInstance().getReference();

                    check_ref.child("CarWashMessage")
                            .child(my_cleaner)
                            .child(userId)
                            .child(month_year_string)
                            .child(check_date)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.hasChildren() == false) {
                                        if (isAdded()) {
                                            // status_image.setImageResource(R.drawable.ic_about_nav);
                                            status_text.setText("not done");
                                        }
                                    } else {
                                        if (isAdded()) {
                                            //status_image.setImageResource(R.drawable.ic_check);
                                            status_text.setText("done");
                                        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_CALL && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Toast.makeText(getActivity(), "Granted", Toast.LENGTH_LONG).show();
            readData(new FirebaseCallback() {
                @Override
                public void onCallBack(Customer customer) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + customer.getContact_number()));
                    startActivity(callIntent);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.whatsapp) {
            DatabaseReference databaseReferences = FirebaseDatabase.getInstance().getReference();
            readData(new FirebaseCallback() {
                @Override
                public void onCallBack(Customer customer) {
                    databaseReferences.child("Cleaner").child("profile").child(customer.getMy_cleaner())
                            .child("contact_number")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    contact = dataSnapshot.getValue(String.class);
                                    String url = "https://api.whatsapp.com/send?phone=" + contact;
                                    try {
                                        if (getActivity() != null) {
                                            PackageManager pm = getActivity().getPackageManager();
                                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }

                                    } catch (PackageManager.NameNotFoundException e) {
                                        Toast.makeText(getActivity(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
            });

        }

        if (v.getId() == R.id.phone) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                readData(new FirebaseCallback() {
                    @Override
                    public void onCallBack(Customer customer) {
                        DatabaseReference databaseReferences = FirebaseDatabase.getInstance().getReference();
                        databaseReferences.child("Cleaner").child("profile")
                                .child(customer.getMy_cleaner())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        try {
                                            Cleaner cln = dataSnapshot.getValue(Cleaner.class);
                                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                                            if (cln != null) {
                                                callIntent.setData(Uri.parse("tel:" + cln.getContact_number()));
                                                startActivity(callIntent);
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(getActivity(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }
                });
            }
        }

        if (v.getId() == R.id.info) {
            dbref = FirebaseDatabase.getInstance().getReference().child("Customers").child("profile")
                    .child(userId);
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    String value = snapshot.child("my_cleaner").getValue().toString();
                    //  planStatus1.setText(value);
                    // Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                    sharedPreferences2 = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    editor.putString("value", value);
                    editor.apply();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();

                }
            });

            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_mainActivitys2_to_cleanerInfo);
        }

        if (v.getId() == R.id.recharge_cus_det) {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_mainActivitys2_to_mapboxTutorial_22);
        }
    }

    private void readData(FirebaseCallback firebaseCallback) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customers = snapshot.getValue(Customer.class);
                firebaseCallback.onCallBack(customers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        rootRef.addValueEventListener(valueEventListener);
    }

    private interface FirebaseCallback {
        void onCallBack(Customer customer);
    }
}