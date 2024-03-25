package com.org.cleaner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.org.clinify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class MainDashboard extends Fragment implements View.OnClickListener {

    ImageView myCustomers;
    ImageView saveData;
    ImageView saveLocation;
    ImageView saveDocs;
    ImageView settings;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_main_dashboard, container, false);
        myCustomers = v.findViewById(R.id.myCustomers);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        navController = Navigation.findNavController(getActivity(),
                R.id.nav_host_fragment_cleaner);

        myCustomers.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.myCustomers) {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_cleaner);
            navController.navigate(R.id.action_main_dash_board_to_my_customer_list);
        }
        
    }
}