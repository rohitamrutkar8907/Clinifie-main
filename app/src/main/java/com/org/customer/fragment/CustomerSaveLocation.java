package com.org.customer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.org.clinify.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

/**
 * Use the places plugin to take advantage of Mapbox's location search ("geocoding") capabilities. The plugin
 * automatically makes geocoding requests, has built-in saved locations, includes location picker functionality,
 * and adds beautiful UI into your Android project.
 */
public class CustomerSaveLocation extends Fragment
        implements OnMapReadyCallback,
        PermissionsListener,
        View.OnClickListener,
        MapboxMap.OnCameraMoveListener,
        MapboxMap.OnCameraIdleListener {

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private CarmenFeature home;
    private CarmenFeature work;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
    private LatLng location;

    private ProgressBar load_bar;

    //b
    Button button;
    View v;

    //c
    private final DatabaseReference cleaner_reference = FirebaseDatabase.getInstance()
            .getReference().child("Cleaner");

    //d
    private final DatabaseReference drivers_available = FirebaseDatabase.getInstance()
            .getReference().child("DriversAvailable");
    private Boolean driver_found = false;
    private String driver_found_id;

    //g
    private GeoQuery geo_query;
    private GeoFire geoFire;
    private PermissionsManager permissionsManager;


    //m
    public static Handler mHandler = new Handler();


    //r
    private double radius = 1f;

    //s
    private double store_latitude = 0.0, store_longtitude = 0.0;
    private EditText search_editText;


    //t
    private long total_customer_child;

    private static final String RED_MARKER_ICON_ID = "RED_MARKER_ICON_ID";
    private static final String COMPASS_ICON_ID = "COMPASS_ICON_ID";
    Symbol symbolStart;
    SymbolManager symbolManager;

    LatLng latlng_global;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        // This contains the MapView in XML and needs to be called after the access token is configured.


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Mapbox.getInstance(getActivity(), getString(R.string.access_token));
        v = inflater.inflate(R.layout.fragment_cleaner_save_location, container, false);
        mapView = v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        latlng_global = new LatLng(-90.0, 90);
        geoFire = new GeoFire(drivers_available);
        button = v.findViewById(R.id.button4);
        button.setOnClickListener(this);
        load_bar = v.findViewById(R.id.load_bar);
        SharedPreferences sharedPreferencesMaps = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesMaps.edit();
        editor.putString("check", "true");
        editor.apply();

        return v;
    }


    Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            store_latitude = location.getLatitude();
            store_longtitude = location.getLongitude();

            geo_query = geoFire.queryAtLocation(new GeoLocation(store_latitude, store_longtitude), radius);
            geo_query.removeAllListeners();
            geo_query.addGeoQueryEventListener(new GeoQueryEventListener() {
                @Override
                public void onKeyEntered(String key, GeoLocation location) {
                    total_customer_child = 0;

                    if (!driver_found) {
                        driver_found = true;
                        driver_found_id = key;

                        cleaner_reference.child(driver_found_id).child("myCustomers").
                                addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
                                            total_customer_child = total_customer_child + 1;
                                        }

                                        if (total_customer_child >= 10) {
                                            driver_found = (!driver_found);
                                            driver_found_id = null;
                                        } else {
                                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("temp_tot_customer_child", String.valueOf(total_customer_child + 1));
                                            editor.putString("lats", String.valueOf(store_latitude));
                                            editor.putString("longs", String.valueOf(store_longtitude));
                                            editor.putString("driverFoundID", driver_found_id);
                                            editor.apply();
                                            mHandler.removeCallbacks(mToastRunnable);
                                            radius = 0.0;
                                            driver_found = !driver_found;
                                            driver_found_id = null;
                                            load_bar.setVisibility(View.GONE);
                                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                            if (navController.getGraph().getId() == R.id.navigation_graph2) {
                                                navController.navigate(R.id.action_mapboxTutorial_22_to_plan1);
                                            } else {
                                                navController.navigate(R.id.action_mapboxTutorial_2_to_carDetails);

                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }

                }

                @Override
                public void onKeyExited(String key) {

                }

                @Override
                public void onKeyMoved(String key, GeoLocation location) {

                }

                @Override
                public void onGeoQueryReady() {
                    if (!driver_found) {
                        radius = (radius + 1.0f) % 8f;
                    }
                }

                @Override
                public void onGeoQueryError(DatabaseError error) {

                }
            });
        }
    };

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getActivity())) {
            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(getActivity(), loadedMapStyle).build());
            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.NONE);
            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.addOnCameraMoveListener(this);
        mapboxMap.addOnCameraIdleListener(this);
        LatLng takestanLatLong = new LatLng(36.071636, 49.695322);
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
                initSearchFab();
                addUserLocations();
                // Create an empty GeoJSON source using the empty feature collection
                setUpSource(style);
                // Set up a new symbol layer for displaying the searched location's feature coordinates
                setupLayer(style);


                //get symbol image from drawable
//                style.addImage(RED_MARKER_ICON_ID,
//                        BitmapUtils.getBitmapFromDrawable(getResources()
//                                .getDrawable(R.drawable.mapbox_marker_icon_default)));

//                style.addImage(COMPASS_ICON_ID,
//                        BitmapUtils.getBitmapFromDrawable(getResources()
//                                .getDrawable(R.drawable.mapbox_compass_icon)));

                //initialize symbol manager
//                symbolManager = new SymbolManager(mapView, mapboxMap, style);
//                symbolManager.setIconAllowOverlap(true);
//                symbolManager.setIconIgnorePlacement(true);

                //create symbol
//                symbolStart = symbolManager.create(new SymbolOptions()
//                        .withLatLng(takestanLatLong)
//                        .withIconImage(RED_MARKER_ICON_ID)
//                        .withSymbolSortKey(5.0f)
//                        .withIconSize(1.5f));


            }
        });
    }

    @Override
    public void onCameraMove() {
        //get the camera position
        latlng_global = mapboxMap.getCameraPosition().target;
        //set the symbol icon for move
        //symbolStart.setIconImage(COMPASS_ICON_ID);
        //set the camera new latlng for symbol
        //symbolStart.setLatLng(latlng_global);
        //symbolManager.update(symbolStart);
    }

    @Override
    public void onCameraIdle() {
//        if (symbolStart != null) {
//            set the icon when move is finished
//            symbolStart.setIconImage(RED_MARKER_ICON_ID);
//            symbolManager.update(symbolStart);
//            Toast.makeText(getActivity(), latlng_global.getLatitude() +
//                    "\n"
//                    + latlng_global.getLongitude(), Toast.LENGTH_SHORT).show();
//        }

    }

    private void initSearchFab() {
        v.findViewById(R.id.fab_location_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.access_token))
                        .placeOptions(PlaceOptions.builder()
                                .backgroundColor(Color.parseColor("#EEEEEE"))
                                .limit(10)
                                .addInjectedFeature(home)
                                .addInjectedFeature(work)
                                .build(PlaceOptions.MODE_CARDS))
                        .build(getActivity());
                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
            }
        });
    }

    private void addUserLocations() {
        home = CarmenFeature.builder().text("Mapbox SF Office")
                .geometry(Point.fromLngLat(-122.3964485, 37.7912561))
                .placeName("50 Beale St, San Francisco, CA")
                .id("mapbox-sf")
                .properties(new JsonObject())
                .build();

        work = CarmenFeature.builder().text("Mapbox DC Office")
                .placeName("740 15th Street NW, Washington DC")
                .geometry(Point.fromLngLat(-77.0338348, 38.899750))
                .id("mapbox-dc")
                .properties(new JsonObject())
                .build();
    }

    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(symbolIconId),
                iconOffset(new Float[]{0f, -8f})
        ));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

            // Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);

            // Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
            // Then retrieve and update the source designated for showing a selected location's symbol layer icon

            if (mapboxMap != null) {

                Style style = mapboxMap.getStyle();
                if (style != null) {

                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[]{Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }

                    location = new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                            ((Point) selectedCarmenFeature.geometry()).longitude());

                    // Move map camera to the selected location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(
                                            ((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude())
                                    )
                                    .zoom(14)
                                    .build()), 4000);
                }
            }
        }
    }

    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button4) {
            if (location != null) {
                load_bar.setVisibility(View.VISIBLE);
                mToastRunnable.run();
            } else {
                Toast.makeText(getActivity(), "Please select your car Location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getContext(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
    }
}
