package edu.spot_finder_client;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import edu.spot_finder_client.login.LoginActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rest.Lot;
import rest.RESTCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private String BASE_URL = "https://whispering-peak-77610.herokuapp.com";
    FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.main_content_fragment, mapFragment).commit();
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

//        TextView tv_user_info = findViewById(R.id.user_info);
//        Intent intent = getIntent();
//        String name = intent.getStringExtra("username");
//        tv_user_info.setText(intent.getStringExtra("username"));

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (id) {
            case R.id.test_API_call: {
                Lot lot = new Lot(1);
                lot.GET(this.getApplicationContext(), null);
                lot.setName("Test");
                break;
            }
            case R.id.login_menu_item: {
                Intent login = new Intent(this.getApplicationContext(), LoginActivity.class);
                startActivity(login);
                break;
            }
            case R.id.map_menu_item: {
                SupportMapFragment mapFragment = SupportMapFragment.newInstance();
                fragmentManager.beginTransaction().replace(R.id.main_content_fragment, mapFragment).commit();
                mapFragment.getMapAsync(this);
                ft.replace(R.id.main_content_fragment, mapFragment, "map").commit();
                break;
            }
            case R.id.checkin_menu_item: {
                ft.replace(R.id.main_content_fragment, QRFragment.newInstance(), "qr").commit();
                break;
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/maps/dir/?api=1&destination=" + String.valueOf(marker.getPosition().latitude)
                        + "%2C+" + String.valueOf(marker.getPosition().longitude)));
                startActivity(intent);
            }
        });


        final Geocoder geocoder = new Geocoder(MainActivity.this);
        Lot lot = new Lot();
        final ArrayList<Lot> lots = new ArrayList<>();

        lot.LIST(getApplicationContext(), new RESTCallback() {
            @Override
            public void onSuccess(JSONObject result) {

            }

            @Override
            public void onSuccess(JSONArray result) {
                for (int i = 0; i < result.length(); i++) {
                    try {
                        lots.add(Lot.fromJSON(result.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                List<Address> addressList;
                Address a;
                LatLng latLng = new LatLng(0, 0);
                for (Lot l : lots) {
                    try {
                        addressList = geocoder.getFromLocationName(l.getAddress(), 1);

                        a = addressList.get(0);
                        latLng = new LatLng(a.getLatitude(), a.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latLng).title("Available Spots: " + l.getAvailable() + "/" + l.getCount()).snippet("" +
                                "Address: " + l.getAddress()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });


    }

}
