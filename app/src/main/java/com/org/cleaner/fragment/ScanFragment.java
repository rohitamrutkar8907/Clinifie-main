package com.org.cleaner.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.zxing.Result;
import com.org.cleaner.fragment.model.Customer;
import com.org.clinify.R;
import com.org.common.Constatnts;
import com.org.customer.activity.ProvideRequestPermission;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.org.common.Constatnts.MY_CAMERA_REQUEST_CODE;
import static com.org.common.Constatnts.PERMISSION_REQUEST_CODE;
import static com.org.common.Constatnts.PLAY_SERVICES_ERROR_CODE;

public class ScanFragment extends Fragment implements OnClickListener, OnMapReadyCallback {

    private View v;

    private TextView location_text;
    private TextView status;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Customer customer;


    //scan
    private CodeScanner mCodeScanner;
    Activity activity;
    private String message;
    private String latitude;
    private String longititude;
    private String customer_uid;
    private String token;
    private Marker mCurrLocationMarker;
    private double store_latitude, store_longtitude = 0.0;
    private GoogleMap m_map;
    private CodeScannerView scannerView;
    private ProgressBar progress_bar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_scan, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        location_text = v.findViewById(R.id.location);
        status = v.findViewById(R.id.waiting);
        progress_bar = v.findViewById(R.id.progress_bar);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        activity = getActivity();

        if (getArguments() != null) {
            ScanFragmentArgs args = ScanFragmentArgs.fromBundle(getArguments());
            customer = args.getCustomer();
        }

        message = customer.getCar_number();


        if (checkCameraPermission()) {
            scannerView = v.findViewById(R.id.scanner_view);
            mCodeScanner = new CodeScanner(activity, scannerView);

            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (message.equals(result.getText())) {
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_cleaner);
                                ScanFragmentDirections.ActionScanFragmentToSavePictureAndProof main = ScanFragmentDirections.actionScanFragmentToSavePictureAndProof(customer);
                                main.setLatitude(String.valueOf(store_latitude));
                                main.setLongititude(String.valueOf(store_longtitude));
                                navController.navigate(main);
                            } else {
                                Toast.makeText(getActivity(), "Please scan right qr code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            scannerView.setOnClickListener(this);
        } else {
            requestCameraPermission();
        }


        return v;
    }

    //Called when there is a change in the availibility of location of data
    LocationCallback mLocationCallback = new LocationCallback() {


        @Override
        public void onLocationResult(LocationResult locationResult) {

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {

                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);

                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                store_latitude = mLastLocation.getLatitude();
                store_longtitude = mLastLocation.getLongitude();
                location_text.setText(String.valueOf(store_latitude) + " " + String.valueOf(store_longtitude));
                if (!location_text.getText().equals("TextView")) {
                    scannerView.setVisibility(View.VISIBLE);
                    progress_bar.setVisibility(View.GONE);
                    status.setText("Ready to save");
                }

                //move map camera
                m_map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                m_map.setMinZoomPreference(13.0f);
                m_map.setMaxZoomPreference(20.0f);
            }
        }
    };

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.scanner_view) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        m_map = googleMap;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(999999); // two minute interval
        mLocationRequest.setFastestInterval(999999);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (!checkLocationPermission()) {
            requestLocationPermission();
        } else {
            initGoogleMap();
            m_map.setMyLocationEnabled(true);

        }

        m_map.getUiSettings().setAllGesturesEnabled(false);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        m_map.setMyLocationEnabled(true);

    }

    private void initGoogleMap() {
        if (isServicesOk()) {
            if (checkLocationPermission()) {
                checkGps();
                CancellationTokenSource cts = new CancellationTokenSource();
                fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

            } else {
                Intent intent = new Intent(getActivity(), ProvideRequestPermission.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    private boolean isServicesOk() {
        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();
        int result = googleApi.isGooglePlayServicesAvailable(getActivity());
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApi.isUserResolvableError(result)) {
            Dialog dialog = googleApi.getErrorDialog(getActivity(), result, PLAY_SERVICES_ERROR_CODE, new DialogInterface
                    .OnCancelListener() {
                @Override
                public void onCancel(DialogInterface task) {
                    // Toast.makeText(getActivity(), "Dialog is cancelled by User", Toast.LENGTH_SHORT)
                    //.show();
                }
            });
            dialog.show();
        } else {
            //Toast.makeText(getActivity(), "Play services are required by getActivity() application", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void checkGps() {
        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        settingsClient.checkLocationSettings(locationSettingsRequest);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getActivity()).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    // mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(
                                        getActivity(),
                                        Constatnts.REQUEST_CHECK_SETTINGS);

                            } catch (ClassCastException | IntentSender.SendIntentException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }


    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        if (!checkCameraPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onResume() {
        if (mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        if (!checkLocationPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission
                        .ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_CAMERA_REQUEST_CODE:
                final int no_of_req = grantResults.length;
                final boolean isGranted = no_of_req == 1 && PackageManager.PERMISSION_GRANTED == grantResults[no_of_req - 1];
                if (isGranted) {
                    CodeScannerView scannerView = v.findViewById(R.id.scanner_view);
                    mCodeScanner = new CodeScanner(activity, scannerView);
                    mCodeScanner.setDecodeCallback(new DecodeCallback() {
                        @Override
                        public void onDecoded(@NonNull final Result result) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "got", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Not Granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
