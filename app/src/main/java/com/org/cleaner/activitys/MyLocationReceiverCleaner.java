package com.org.cleaner.activitys;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.org.clinify.R;


public class MyLocationReceiverCleaner extends BroadcastReceiver {

    private static final String TAG = "MyLocationReceiver";
    private Context context;
    private Snackbar snackbar;
    private Activity act;

    public MyLocationReceiverCleaner(Activity act, Context context, Snackbar snackbar){
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
                    navController.navigate(R.id.cleaner_save_location);
            } else {
                NavController navController = Navigation.findNavController(act, R.id.nav_host_fragment);
                navController.navigate(R.id.action_customerMapsActivity2_to_gpsCheckFragment2);
            }
        }

    }

}