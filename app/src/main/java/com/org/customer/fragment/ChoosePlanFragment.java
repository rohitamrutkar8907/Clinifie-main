package com.org.customer.fragment;


import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.org.clinify.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.org.customer.fragment.bottem_sheet_fragment.ExampleBottomSheetDialogPlan;
import com.org.customer.fragment.model.Plan;
import com.org.customer.fragment.adapters.PlanAdapter;
import com.org.customer.fragment.interfaces.RecyclerViewClickInterface;

import static android.content.Context.MODE_PRIVATE;
import com.org.customer.activity.MyConnectionReceiverCustomer;


public class ChoosePlanFragment extends Fragment implements RecyclerViewClickInterface {

    //a
    private PlanAdapter adapter;

    //m
    private MyConnectionReceiverCustomer my_connection_receiver;

    //p
    private ProgressBar progress_bar;

    //r
    private RecyclerView recycler_view;


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

        View v = inflater.inflate(R.layout.fragment_choose_plan, container, false);

        recycler_view = v.findViewById(R.id.plan_recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        progress_bar = v.findViewById(R.id.MyProgressPlan);

        FirebaseRecyclerOptions<Plan> options =
                new FirebaseRecyclerOptions.Builder<Plan>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Plan"), Plan.class)
                        .build();

        adapter = new PlanAdapter(options, this);
        recycler_view.setAdapter(adapter);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(my_connection_receiver);
    }


    @Override
    public void onResume() {
        super.onResume();
        my_connection_receiver = new MyConnectionReceiverCustomer(getActivity(), Snackbar.make(getActivity().findViewById(android.R.id.content),
                "Internet service is not enabled", Snackbar.LENGTH_INDEFINITE), progress_bar);
        getActivity().registerReceiver(my_connection_receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
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
        adapter.stopListening();
    }


    @Override
    public void onItemClick(int pos, Plan plan) {
        progress_bar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String ss = String.valueOf(Integer.parseInt(plan.getDays()) / 30);
        editor.putString("days", plan.getDays());
        editor.putString("month", ss);
        editor.putString("money", plan.getRs());
        editor.apply();
        progress_bar.setVisibility(View.GONE);
        ExampleBottomSheetDialogPlan bottomSheetDialog1 = new ExampleBottomSheetDialogPlan();
        bottomSheetDialog1.show(getChildFragmentManager(), "ExampleBottomSheetPlan");
    }


}