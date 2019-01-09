package com.example.ss.smartpartkingsystemv6;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import static com.example.ss.smartpartkingsystemv6.ParkingLayout.index;
import static com.example.ss.smartpartkingsystemv6.ParkingLayout.unavailableParkingSpots;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    PopupWindow popup;
    LinearLayout layout;
    private Marker Marker1;
    GoogleMap mMap;
    private boolean  parkingLayoutFlag;
    public boolean getParkingLayoutFlag() {
        return parkingLayoutFlag;
    }

    public void setParkingLayoutFlag(boolean ParkingLayoutFlag) {
        this.parkingLayoutFlag = ParkingLayoutFlag;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);//fragment used to map
        mapFragment.getMapAsync((OnMapReadyCallback) this);//asyncronuous, run in the background as a thread, once the map is loaded it will call another function to do things
        setParkingLayoutFlag(false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //45.749808,21.210744794165244
        //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng location = new LatLng(45.738830, 21.211973);
        popup = new PopupWindow(this);
        layout = new LinearLayout(this);
        //everything that is added to the map is created by an option object and added
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());

        Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(45.738903, 21.211880), new LatLng(45.738864, 21.211905))
                .add(new LatLng(45.738864, 21.211905),new LatLng(45.738872, 21.211932))
                .add(new LatLng(45.738872, 21.211932),new LatLng(45.738912, 21.211910))

                .add(new LatLng(45.738872, 21.211932),new LatLng(45.738881, 21.211960))
                .add(new LatLng(45.738881, 21.211960),new LatLng(45.738920, 21.211941))
                .add(new LatLng(45.738881, 21.211960),new LatLng(45.738890, 21.211986))
                .add(new LatLng(45.738890, 21.211986),new LatLng(45.738927, 21.211970))

                .add(new LatLng(45.738890, 21.211986),new LatLng(45.738900, 21.212018))

                .color(Color.GRAY)
                .width(6.5f)
                .geodesic(true));
        LatLng location2 = new LatLng(45.74905905636424, 21.20810925978731);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(location2);
        markerOptions2.title("Parking lot");
        markerOptions2.snippet("Available parking slots");


        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(Color.BLUE));
        // markerOptions2.draggable(true);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
        Marker1=googleMap.addMarker(markerOptions);
        googleMap.addMarker(markerOptions2);
        googleMap.setOnMarkerClickListener(this);
        //googleMap.setInfoWindowAdapter(this);

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        ParkingLayout cdd = new ParkingLayout(MapsActivity.this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cdd.show();
        //cdd.show();

        // }
        return true;

    }

}