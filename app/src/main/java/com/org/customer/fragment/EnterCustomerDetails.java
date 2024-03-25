package com.org.customer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.org.common.Constatnts;
import com.org.clinify.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.org.customer.activity.MyConnectionReceiverCustomer;

import static android.content.Context.MODE_PRIVATE;

public class EnterCustomerDetails extends Fragment implements View.OnClickListener {

    //c
    private EditText car_model;
    private EditText car_number;
    private EditText customer_name;

    //i
    private Uri imageUri;

    //m
    private MyConnectionReceiverCustomer my_connection_receiver;

    //p
    private ProgressBar progress_bar;

    //n
    private Button next_task;

    //s
    private StorageReference storageReference;

    //u
    private String user_id;

    ImageView profile_image;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details_car, container, false);

        car_number = v.findViewById(R.id.carNumber);
        car_model = v.findViewById(R.id.carModel);
        next_task = v.findViewById(R.id.next);
        customer_name = v.findViewById(R.id.customer_name);
        progress_bar = v.findViewById(R.id.progressBar3);

        profile_image = v.findViewById(R.id.profile_image);
        next_task.setOnClickListener(this);
        profile_image.setOnClickListener(this);

        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();

        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        my_connection_receiver = new MyConnectionReceiverCustomer(getActivity(), Snackbar.make(getActivity()
                        .findViewById(android.R.id.content),
                R.string.internet_service_status, Snackbar.LENGTH_INDEFINITE), next_task);
        getActivity().registerReceiver(my_connection_receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(my_connection_receiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.next)
        {
            String car_mod = car_model.getText().toString();
            String car_num = car_number.getText().toString();
            String cust_name = customer_name.getText().toString();

            if (car_mod.isEmpty()) {
                car_model.setError("Please enter the car Model");
                car_model.requestFocus();
            }

            if (car_num.isEmpty()) {
                car_number.setError("Please enter the car number");
                car_number.requestFocus();
            }

            if (!Constatnts.CAR_NUMBER_PATTERN.matcher(car_num).matches()) {
                car_number.setError("Please enter a valid car number format");
                car_number.requestFocus();
            }

            if (cust_name.isEmpty()) {
                customer_name.setError("Please enter the customer name");
                customer_name.requestFocus();
            }

            if(imageUri == null)
            {
                Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_SHORT).show();
            }

            if ( (!car_mod.isEmpty() &&  cust_name.length() < 20) &&
                    !car_num.isEmpty() &&
                    Constatnts.CAR_NUMBER_PATTERN.matcher(car_num).matches() &&
                    (!cust_name.isEmpty()  &&  cust_name.length() < 30)&& imageUri != null) {

                car_number.setError(null);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("carNum", car_num);
                editor.putString("carModel", car_mod);
                editor.putString("customername", cust_name);
                editor.apply();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_carDetails_to_blankFragment);
            }

        }

        if(v.getId() == R.id.profile_image)
        {
            Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGallery, 1000);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                //code review completed
                if (data != null) {
                    progress_bar.setVisibility(View.VISIBLE);
                    imageUri = data.getData();
                    profile_image.setImageURI(imageUri);
                    //s
                    StorageReference sr = storageReference.child("Customer/" + user_id + "/profile.jpg");
                    sr.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progress_bar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Image saved", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress_bar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Image not saved", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }
    }
}