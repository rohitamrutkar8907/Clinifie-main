package com.org.customer.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.org.clinify.R;
import com.org.customer.fragment.adapters.WorkDoneAdapters;
import com.org.customer.fragment.interfaces.WorkDoneViewClickInterface;
import com.org.customer.fragment.model.Customer;
import com.org.customer.fragment.model.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class WorkDones extends Fragment {

    private RecyclerView recyclerView;
    private WorkDoneAdapters workDoneAdapters;
    private String user_id;
    private DatabaseReference databaseReference;
    private Customer customers;
    private String my_cleaner;
    private FirebaseRecyclerOptions<Date> options;
    private Query query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_work_dones, container, false);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            recyclerView = v.findViewById(R.id.recycler_view_v_work);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            SharedPreferences sharedPreferencesMaps = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
            my_cleaner = sharedPreferencesMaps.getString("my_cleaner", "");

            databaseReference = FirebaseDatabase.getInstance().getReference();
            user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference()
                    .child("Customers")
                    .child("profile")
                    .child(user_id)
                    .child("timeStamp");

            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String month_year_string = getMonthYearString(snapshot);

                    query = FirebaseDatabase.getInstance().getReference().child("CarWashMessage")
                            .child(my_cleaner).child(user_id).child(month_year_string);

                    options = new FirebaseRecyclerOptions.Builder<Date>()
                            .setQuery(query, Date.class).build();

                    workDoneAdapters = new WorkDoneAdapters(options, new WorkDoneViewClickInterface() {
                        @Override
                        public void onLocClick(int pos, Date date) {
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("la_ti_tude", date.getLatitude());
                            editor.putString("long_ti_tude", date.getLongtitude());
                            editor.apply();
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                            navController.navigate(R.id.action_workDones_to_proofLocationFragment);
                        }


                        @Override
                        public void onGalleryClick(int pos, Date date) {
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                            WorkDonesDirections.ActionWorkDonesToGalleryFragment action = WorkDonesDirections.actionWorkDonesToGalleryFragment(getCurrentDate(date.getTimeStamp()));
                            navController.navigate(action);
                        }
                    });

                    recyclerView.setAdapter(workDoneAdapters);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        return v;
    }

    private String getCurrentDate(Long time_stamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time_stamp);
        java.util.Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String t2 = sdf.format(d);
        return t2;
    }

    private String getMonthYearString(DataSnapshot snapshot) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(snapshot.getValue(Long.class));
        java.util.Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String t2 = sdf.format(d);
        String date_month_array[] = t2.split("-");
        String month_year_string = date_month_array[1] + "-" + date_month_array[2];
        return month_year_string;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            new Handler(Looper.getMainLooper())
                    .postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            workDoneAdapters.stopListening();
                        }
                    }, 500);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            new Handler(Looper.getMainLooper())
                    .postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            workDoneAdapters.startListening();
                        }
                    }, 500);
        }
    }


}