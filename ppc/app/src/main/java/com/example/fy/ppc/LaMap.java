package com.example.fy.ppc;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import common.Couple;

public class LaMap extends Activity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double longitude ;
    private  double latitude ;
    public String currentUserId;
    public Couple currentCouple;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_la_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        Intent intent = getIntent();
        currentCouple = (Couple) intent.getSerializableExtra("currentCouple");
        currentUserId = intent.getStringExtra("currentUserId");
        longitude = intent.getDoubleExtra("longitude", 0);
        latitude = intent.getDoubleExtra("latitude", 0);
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("IL EST PLANQUE ICI"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, WelcomeActivity.class).putExtra("currentCouple", currentCouple).putExtra("currentUserId", currentUserId));
        finish();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used. 
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));

    }
}
