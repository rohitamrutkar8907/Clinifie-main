package com.org.customer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoQuery;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;

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
public class ProofLocationFragment extends Fragment implements OnMapReadyCallback,
        PermissionsListener, View.OnClickListener {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private CarmenFeature home;
    private CarmenFeature work;
    private final String geojsonSourceLayerId = "geojsonSourceLayerId";
    private final String symbolIconId = "symbolIconId";
    private LatLng location;

    private static final String RED_MARKER_ICON_ID = "RED_MARKER_ICON_ID";
    private static final String COMPASS_ICON_ID = "COMPASS_ICON_ID";
    Symbol symbolStart;
    SymbolManager symbolManager;

    //b
    Button button;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    //c
    private final DatabaseReference cleaner_reference = FirebaseDatabase.getInstance()
            .getReference().child("Cleaner");

    //d
    private final DatabaseReference drivers_available = FirebaseDatabase.getInstance()
            .getReference().child("DriversAvailable");
    private final Boolean driver_found = false;
    private String driver_found_id;


    //g
    private GeoQuery geo_query;
    private GeoFire geoFire;
    private PermissionsManager permissionsManager;


    //m
    public static Handler mHandler = new Handler();


    //r
    private final double radius = 1f;

    //s
    private double store_latitude;
    private final double store_longtitude = 0.0;
    private EditText search_editText;


    //t
    private long total_customer_child;
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Mapbox.getInstance(getActivity(), getString(R.string.access_token));

        v = inflater.inflate(R.layout.fragment_proof_location, container, false);
        mapView = v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        geoFire = new GeoFire(drivers_available);
        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

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
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }

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
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                style.addImage(symbolIconId, BitmapFactory.decodeResource(
                        getActivity().getResources(), R.drawable.map_default_map_marker));
                SharedPreferences sharedPreferencesMaps = getActivity().getSharedPreferences("SharedPrefs", MODE_PRIVATE);
                // Move map camera to the selected location
                String lat = sharedPreferencesMaps.getString("la_ti_tude", "");
                String lng = sharedPreferencesMaps.getString("long_ti_tude", "");
                LatLng takestanLatLong = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                                .zoom(14)
                                .build()), 4000);

                enableLocationComponent(style);

                addUserLocations();
                // Create an empty GeoJSON source using the empty feature collection
                setUpSource(style);
                // Set up a new symbol layer for displaying the searched location's feature coordinates
                setupLayer(style);


                //get symbol image from drawable
                style.addImage(RED_MARKER_ICON_ID,
                        BitmapUtils.getBitmapFromDrawable(getResources()
                                .getDrawable(R.drawable.mapbox_marker_icon_default)));

                style.addImage(COMPASS_ICON_ID,
                        BitmapUtils.getBitmapFromDrawable(getResources()
                                .getDrawable(R.drawable.mapbox_compass_icon)));

                //initialize symbol manager
                symbolManager = new SymbolManager(mapView, mapboxMap, style);
                symbolManager.setIconAllowOverlap(true);
                symbolManager.setIconIgnorePlacement(true);

                //create symbol
                symbolStart = symbolManager.create(new SymbolOptions()
                        .withLatLng(takestanLatLong)
                        .withIconImage(RED_MARKER_ICON_ID)
                        .withSymbolSortKey(5.0f)
                        .withIconSize(1.5f));

            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
