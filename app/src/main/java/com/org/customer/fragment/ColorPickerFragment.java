package com.org.customer.fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.org.common.Constatnts;
import com.org.clinify.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.org.customer.activity.MyConnectionReceiverCustomer;
import yuku.ambilwarna.AmbilWarnaDialog;

import static android.content.Context.MODE_PRIVATE;

public class ColorPickerFragment extends Fragment implements View.OnClickListener {

    //c
    private ImageView color_picker;
    private TextView color_picker_error;
    private boolean check;
    private ProgressBar car_color_progress;

    //d
    private int default_color;

    //m
    private MyConnectionReceiverCustomer my_connection_receiver;

    //n
    private Button next_task;

    //u
    private String user_id;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_color_picker, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        check = false;
        default_color = 0;
        color_picker = v.findViewById(R.id.colorPicker);
        color_picker_error = v.findViewById(R.id.textViewError);
        car_color_progress = v.findViewById(R.id.progressBarCarColor);

        default_color = ContextCompat.getColor(getActivity(), R.color.colorPrimary);

        next_task = v.findViewById(R.id.next);

        next_task.setOnClickListener(this);
        color_picker.setOnClickListener(this);

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

        my_connection_receiver = new MyConnectionReceiverCustomer(getActivity(),
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        R.string.internet_service_status, Snackbar.LENGTH_INDEFINITE), next_task);

        getActivity().registerReceiver(my_connection_receiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void openColorPicker() {
        AmbilWarnaDialog colorPickerAmbil = new AmbilWarnaDialog(getActivity(), default_color,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        default_color = color;
                        color_picker.setBackgroundColor(default_color);
                        check = true;
                    }
                });

        colorPickerAmbil.show();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.colorPicker) {
            openColorPicker();
        }


        if (v.getId() == R.id.next) {
            if (!check && default_color != 0) {
                color_picker_error.setVisibility(View.VISIBLE);
            } else {
                car_color_progress.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("carColor", String.valueOf(default_color));
                editor.apply();
                DatabaseReference Users = FirebaseDatabase.getInstance().getReference().child("Customers")
                        .child("profile").child(user_id).child("timeStamp");
                Users.setValue(ServerValue.TIMESTAMP);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        car_color_progress.setVisibility(View.GONE);
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.action_blankFragment_to_plan1);
                    }
                }, Constatnts.TIMESTAMP_DELAY);
            }
        }

    }
}