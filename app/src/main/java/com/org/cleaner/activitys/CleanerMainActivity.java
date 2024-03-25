package com.org.cleaner.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.org.common.Constatnts;
import com.org.common.EnterPhone;
import com.org.clinify.R;
import com.google.android.gms.maps.MapFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CleanerMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    private MapFragment mapFragment;
    private NavController navController;
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cleaner_main);

        Toolbar toolbar = findViewById(R.id.toolbar_cleaners);
        setSupportActionBar(toolbar);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Cleaner").child("timeStamp").setValue(ServerValue.TIMESTAMP);
            }

        }, 2000);

        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference check_profile =
                FirebaseDatabase.getInstance().getReference().child("Cleaner")
                        .child("profile").child(user_id).child("contact_number");

        check_profile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {

                    navController = Navigation.findNavController(CleanerMainActivity.this,
                            R.id.nav_host_fragment_cleaner);
                    navController.setGraph(R.navigation.flow_when_profile_not_saved);
                    drawerLayout = findViewById(R.id.drawer_layout);
                    NavigationView navigationView = findViewById(R.id.nav_view);
                    NavigationUI.setupWithNavController(navigationView, navController);
                    NavigationUI.setupActionBarWithNavController(CleanerMainActivity.this, navController, drawerLayout);
                    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            return setNavigation(item);
                        }
                    });
                } else {
                    //d
                    DatabaseReference drivers_available = FirebaseDatabase.getInstance()
                            .getReference().child("DriversAvailable").child(user_id);

                    drivers_available.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.hasChildren()) {

                                navController = Navigation.findNavController(CleanerMainActivity.this,
                                        R.id.nav_host_fragment_cleaner);
                                navController.setGraph(R.navigation.flow_when_location_not_saved);
                                drawerLayout = findViewById(R.id.drawer_layout);
                                NavigationView navigationView = findViewById(R.id.nav_view);
                                NavigationUI.setupWithNavController(navigationView, navController);
                                NavigationUI.setupActionBarWithNavController(CleanerMainActivity.this, navController, drawerLayout);
                                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                                    @Override
                                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                        return setNavigation(item);
                                    }
                                });

                            } else {

                                navController = Navigation.findNavController(CleanerMainActivity.this,
                                        R.id.nav_host_fragment_cleaner);
                                navController.setGraph(R.navigation.flow_when_profile_and_location_saved);
                                drawerLayout = findViewById(R.id.drawer_layout);
                                NavigationView navigationView = findViewById(R.id.nav_view);
                                NavigationUI.setupWithNavController(navigationView, navController);
                                NavigationUI.setupActionBarWithNavController(CleanerMainActivity.this, navController, drawerLayout);
                                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                                    @Override
                                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                        return setNavigation(item);

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    private boolean setNavigation(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), EnterPhone.class);
                startActivity(i);
                finish();
                break;

            case R.id.edit_profile:
                navController.navigate(R.id.action_main_dash_board_to_upload_cleaner);
                break;

            case R.id.privacy_policy_cleaner:
                navController.navigate(R.id.action_main_dash_board_to_privacyPolicyCleanerFragment);
                break;

            case R.id.help_cleaner:
                navController.navigate(R.id.action_main_dash_board_to_helpCleanerFragment);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            /********************start*****************/
            //case 1
            case Constatnts.REQUEST_CHECK_SETTINGS:
                /********************start*****************/
                switch (resultCode) {
                    //case 1
                    case Activity.RESULT_OK:
                        /********************start*****************/
                        Toast.makeText(getApplicationContext(), "Location  enabled by user ."
                                , Toast.LENGTH_LONG).show();
                        navController.navigate(R.id.main_dash_board);
                        /********************end*****************/
                        break;


                    //case 2
                    case Activity.RESULT_CANCELED:
                        /********************start*****************/
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(getApplicationContext(), "Location not enabl., user cancelled."
                                , Toast.LENGTH_LONG).show();
                        break;
                    /********************end*****************/
                    default:
                        /********************start*****************/
                        break;
                    /********************end*****************/
                }
                break;
            /********************end*****************/
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp() || NavigationUI.navigateUp(navController, drawerLayout);
    }


  /*  @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    }*/

}