package com.example.king.managebook.view.confirmation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.king.managebook.R;
import com.example.king.managebook.common.Constants;
import com.example.king.managebook.custom.LoadingDialog;
import com.example.king.managebook.model.body.LatLngBody;
import com.example.king.managebook.model.google_map.Location;
import com.example.king.managebook.model.google_map.SingleShotLocationProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsOrderActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener{

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Toolbar toolbar;
    private LoadingDialog loadingDialog;

    List<Marker> lsMarker;
    private GoogleMap mMap;
    private Location location;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_order);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        toolbar = findViewById(R.id.toolbar);
        lsMarker = new ArrayList<>();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        loadingDialog = new LoadingDialog(this);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        location = (Location) intent.getSerializableExtra(Constants.LOCATION);
        address = intent.getStringExtra(Constants.ADDRESS);
        checkPermission();
    }


    public void loadMarker(Location sbv, String address) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(sbv.getLat(), sbv.getLng()));
        markerOptions.title(address);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        Marker marker = mMap.addMarker(markerOptions);
        lsMarker.add(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(marker.getPosition()).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void drawCircle(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Bạn đang ở đây");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        Marker currLocationMarker = mMap.addMarker(markerOptions);
        currLocationMarker.setTag(0);
        lsMarker.add(currLocationMarker);
        currLocationMarker.showInfoWindow();

        mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(1000)
                .strokeColor(R.color.md_red_700)
                .fillColor(R.color.md_blue_700));
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String permissions[] = new String[2];
            permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
            permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }  else {
            SingleShotLocationProvider.getCurrentLocation(this, location -> {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    drawCircle(latLng);

                }

            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] !=
                    PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            } else {
                SingleShotLocationProvider.getCurrentLocation(this, location -> {
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        drawCircle(latLng);
                    }

                });
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        if(location!=null && address!=null){
            loadMarker(location, address);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }


}
