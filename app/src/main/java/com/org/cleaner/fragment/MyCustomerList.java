package com.org.cleaner.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.org.cleaner.fragment.adapters.RecyclerViewAdapter;
import com.org.cleaner.fragment.interfaces.RecyclerViewClickInterface;
import com.org.cleaner.fragment.model.Customer;
import com.org.clinify.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyCustomerList extends Fragment  {

    private RecyclerView recycler_view;
    private RecyclerViewAdapter recyclerview_adapter;
    private SwipeRefreshLayout swipe_refresh_layout;
    private List<Customer> list;
    private String user_id;
    public static final String TAG = "Myfragment";
    private Customer customer1;
    private Date toDay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_customer_list, container, false);

        recycler_view = v.findViewById(R.id.r);
        swipe_refresh_layout = v.findViewById(R.id.swipe_refresh_layout);
        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //swipe_refresh_layout.setOnRefreshListener(this);


        list = new ArrayList<>();

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance()
                .getReference("Cleaner").child("timeStamp");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(snapshot.getValue(Long.class));
                toDay = calendar.getTime();
                toDay = toDay;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Cleaner").child("myCustomers").child(user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot data : snapshot.getChildren()) {
                            customer1 = data.getValue(Customer.class);
                            Calendar calendar = Calendar.getInstance();
                            int year = Integer.parseInt(customer1.getYear());
                            int month = Integer.parseInt(customer1.getMonth()) - 1;
                            int date = Integer.parseInt(customer1.getDate());
                            calendar.set(year, month, date);
                            Date customerExpiryDate = calendar.getTime();
                            customerExpiryDate = customerExpiryDate;
                            if (toDay.before(customerExpiryDate)) {
                                list.add(customer1);
                            }
                        }

                        RecyclerViewClickInterface recyclerViewClickInterface = new RecyclerViewClickInterface() {
                            @Override
                            public void onItemClick(int position) {
                                Customer customer = list.get(position);
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_cleaner);
                                MyCustomerListDirections.ActionMyCustomerListToCustomerDetailInfo action = MyCustomerListDirections.actionMyCustomerListToCustomerDetailInfo(customer);
                                navController.navigate(action);
                            }
                        };

                        recyclerview_adapter = new RecyclerViewAdapter(list, recyclerViewClickInterface);
                        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recycler_view.setAdapter(recyclerview_adapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return v;
    }



}

