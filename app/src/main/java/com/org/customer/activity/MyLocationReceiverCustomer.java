package com.org.customer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.org.clinify.R;


public class MyLocationReceiverCustomer extends BroadcastReceiver {

    private static final String TAG = "MyLocationReceiver";
    private Context context;
    private Snackbar snackbar;
    private Activity act;

    public MyLocationReceiverCustomer(Activity act, Context context, Snackbar snackbar){
        this.context = context;
        this.snackbar = snackbar;
        this.act = act;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(gpsEnabled && networkEnabled) {
                    NavController navController = Navigation.findNavController(act, R.id.nav_host_fragment);
                    navController.navigate(R.id.mapboxTutorial_2);
            } else {
                NavController navController = Navigation.findNavController(act, R.id.nav_host_fragment);
                navController.navigate(R.id.gps_check_fragment);
            }
        }

    }

}