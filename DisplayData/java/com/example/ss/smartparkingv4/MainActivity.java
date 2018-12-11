package com.example.ss.smartparkingv4;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

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
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);//fragment used to map
        mapFragment.getMapAsync((OnMapReadyCallback) this);//asyncronuous, run in the background as a thread, once the map is loaded it will call another function to do things
        setParkingLayoutFlag(false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //45.749808,21.210744794165244
        LatLng location = new LatLng(45.749808, 21.210744794165244);
        popup = new PopupWindow(this);
        layout = new LinearLayout(this);
        //everything that is added to the map is created by an option object and added in

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title("Parking lot");
        markerOptions.snippet("Available parking slots");
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(Color.BLUE));
        //markerOptions.draggable(true);

        markerOptions.zIndex(0.5F);

        LatLng location2 = new LatLng(45.74905905636424, 21.20810925978731);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(location2);
        markerOptions2.title("Parking lot");
        markerOptions2.snippet("Available parking slots");


        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(Color.BLUE));
       // markerOptions2.draggable(true);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
        Marker1=googleMap.addMarker(markerOptions);
        googleMap.addMarker(markerOptions2);
        googleMap.setOnMarkerClickListener(this);
        //googleMap.setInfoWindowAdapter(this);

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        //sLog.d("MainActivity", marker.getTitle());
        if (marker.equals(Marker1)) {

            /*View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_event_marker, null);
            //ShowPopUp popup=new ShowPopUp();
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.popup_example, null, false),600,600, true);

            pw.showAtLocation(view, Gravity.CENTER, 0, 0);*/

            ParkingLayout cdd = new ParkingLayout(MainActivity.this);
            cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            cdd.show();
            cdd.show();

        }
        return true;

    }
    public static void  reserveParkingSlot()
    {
        /*CustomDialogClass cdd = new CustomDialogClass(MainActivity.this);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();*/
    }
}

