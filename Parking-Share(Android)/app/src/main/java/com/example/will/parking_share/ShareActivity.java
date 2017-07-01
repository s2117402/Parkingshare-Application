package com.example.will.parking_share;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class ShareActivity extends FragmentActivity implements OnMapReadyCallback {
    Button getlocation;
    Button submit;
    EditText longtitude;
    EditText latitude;
    EditText psname;
    TextView googleadress;
    private GoogleMap mMap;
    EditText description;
    Map<String,String> info= new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getlocation=(Button)findViewById(R.id.getlocation);
        submit=(Button)findViewById(R.id.submit);
        longtitude=(EditText)findViewById(R.id.longtitude);
        description=(EditText)findViewById(R.id.description);

        latitude=(EditText)findViewById(R.id.latitude);
        psname=(EditText)findViewById(R.id.psname);
        googleadress=(TextView) findViewById(R.id.googleaddress);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!psname.getText().toString().equals("")&&
                        !description.getText().toString().equals("")&&
                        !latitude.getText().toString().equals("")&&
                        !longtitude.getText().toString().equals("")){
                    info.put("name",psname.getText().toString());
                    info.put("description",description.getText().toString());
                    info.put("lat",latitude.getText().toString());
                    info.put("lon",longtitude.getText().toString());
                    new Thread(){
                        @Override
                        public void run() {
                            try{
                                final String result=SendPost.sendPost("http://192.168.0.101:5000/","newparking",info);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ShareActivity.this, result, Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                }).start();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }
            }
        });
        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGPSInfo();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        LatLng troy = new LatLng(31.8072459,-86.0326418);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(troy));

    }

    public void getGPSInfo() {
        final LocationManager mManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final Location mLocation = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        updateGPSInfo(mLocation);
        mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                System.out.println("change");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                System.out.println("change");

            }

            @Override
            public void onProviderEnabled(String provider){
                System.out.println("change");

                // 当GPS LocationProvider可用时，更新位置
                if (ActivityCompat.checkSelfPermission(ShareActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(ShareActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                updateGPSInfo(mManager.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider) {
                System.out.println("change");


            }
        });

    }


    /**
     * 更新经纬度信息
     * @param location
     */


    public void updateGPSInfo(final Location location){
        final String previousLo=longtitude.getText().toString();
        final String previousLa=latitude.getText().toString();
        if (location != null ) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ShareActivity.this, "Locating...", Toast.LENGTH_SHORT).show();
                            if(!previousLa.equals(location.getLatitude())&& !previousLo.equals(location.getLongitude())){
                                longtitude.setText(""+location.getLongitude());
                                latitude.setText(""+location.getLatitude());
                                LatLng now = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.clear();
                                mMap.addMarker(new MarkerOptions().position(now)
                                        .title(psname.getText().toString()));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(now));
                                new Thread(){
                                    @Override
                                    public void run() {
                                        try{
                                            googleadress.setText(SendPost.getAdressFromGoogle(location.getLatitude(),location.getLongitude()));

                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                            }

                        }
                    });
                }
            }).start();
        }
    }



}
