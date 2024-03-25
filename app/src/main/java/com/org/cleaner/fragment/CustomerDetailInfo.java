package com.org.cleaner.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.animation.TransformationCallback;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import com.org.cleaner.fragment.model.Customer;
import com.org.clinify.R;

import java.util.List;
import java.util.Locale;
import java.util.zip.DeflaterOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailInfo extends Fragment implements View.OnClickListener {

    View v;
    private TextView address_text_view;
    private TextView ratingTextView;
    private NavController navController;
    private Customer customer;
    private ProgressBar progress_bar;

    private static final String TAG = "DetailFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_customer_detail_info, container, false);


        address_text_view = v.findViewById(R.id.nameTextView);
        ratingTextView = v.findViewById(R.id.ratingTextView);
        progress_bar = v.findViewById(R.id.progressBar2);


        navController = Navigation.findNavController(getActivity(),
                R.id.nav_host_fragment_cleaner);

        if (getArguments() != null) {
            CustomerDetailInfoArgs args = CustomerDetailInfoArgs.fromBundle(getArguments());
            customer = args.getCustomer();
            String latitude = customer.getLatitude();
            String longtitude = customer.getLongtitude();
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> my_address = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longtitude), 1);
                address_text_view.setText(my_address.get(0).getAddressLine(0));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        setHasOptionsMenu(true);

        return v;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                CustomerDetailInfoDirections.ActionCustomerDetailInfoToScanFragment action = CustomerDetailInfoDirections.actionCustomerDetailInfoToScanFragment(customer);
                navController.navigate(action);
                break;

            /*case R.id.item2:
                CustomerDetailInfoDirections.ActionCcustomerDetailInfoToNavigationFragment action2 = CustomerDetailInfoDirections.actionCcustomerDetailInfoToNavigationFragment();
                action2.setLatitude(customer.getLatitude());
                action2.setLatitude(customer.getLongtitude());
                navController.navigate(R.id.navigation_fragment);
                break;*/
        }
        return true;
    }


    @Override
    public void onClick(View view) {
    }
}