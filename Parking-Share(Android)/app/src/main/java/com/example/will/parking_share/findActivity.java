package com.example.will.parking_share;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class findActivity extends FragmentActivity implements OnMapReadyCallback {
    TextView currentlocation;
    TextView address;
    String add;
    Button bt2;
    double lat;
    double lon;
    String[][] parking;
    GoogleMap mMap;
    boolean occupying=false;
    String selected=null;
    Button startstop;
    TextView parkingview;
    String result;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        currentlocation=(TextView)findViewById(R.id.currentlocation);
        address=(TextView)findViewById(R.id.address);
        bt2=(Button)findViewById(R.id.button2);
        Intent i = getIntent();
        username=i.getStringExtra("username");
        getGPSInfo();
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        startstop=(Button)findViewById(R.id.startparking);
        parkingview=(TextView)findViewById(R.id.textView7);
        startstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected!=null&&occupying==false&&startstop.getText().toString().equals("Start Parking")){
                    new Thread(){
                        @Override
                        public void run() {
                            Map<String,String> info=new HashMap<String, String>();
                            info.put("name",selected);
                            info.put("username",username);
                            result=SendPost.sendPost("http://192.168.100.4:5000/","startparking",info);
                            if(result.equals("Start parking successfully!")){
                                runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        Toast.makeText(findActivity.this, result,
                                                Toast.LENGTH_LONG).show();
                                        parkingview.setText("Parking at "+selected+"...");
                                        startstop.setText("Stop Parking");
                                        occupying=true;
                                    }

                                });
                            }else{
                                runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        Toast.makeText(findActivity.this, result,
                                                Toast.LENGTH_LONG).show();
                                    }

                                });
                            }
                        }
                    }.start();
                }else if(startstop.getText().toString().equals("Stop Parking")){
                    new Thread(){
                        @Override
                        public void run() {
                            final Map<String,String> info=new HashMap<String, String>();
                            info.put("name",selected);
                            final String stopresult=SendPost.sendPost("http://192.168.100.4:5000/","stopparking",info);
                            runOnUiThread(new Runnable()
                            {
                                public void run()
                                {
                                    Toast.makeText(getApplicationContext(), stopresult,
                                            Toast.LENGTH_LONG).show();
                                    if(stopresult.equals("Stop parking successfully!")){
                                        startstop.setText("Start Parking");
                                        parkingview.setText("Select a parking space please!");
                                        selected=null;
                                        occupying=false;
                                    }
                                }

                            });
                        }
                    }.start();

                }
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                new Thread(){
                    @Override
                    public void run() {
                        try{
                            Map<String,String> info=new HashMap<String, String>();
                            info.put("lat",""+lat);
                            info.put("lon",""+lon);
                            String park=SendPost.sendPost("http://192.168.0.101:5000/","findparking",info);
                            parking=SendPost.parkingToList(park);
                        }catch (Exception e){e.printStackTrace();}
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try{
                                    if(parking==null){
                                        Toast.makeText(findActivity.this, "No Parking space found!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        for(int i=0;i<parking.length;i++){
                                            LatLng now = new LatLng(Double.parseDouble(parking[i][2]),Double.parseDouble(parking[i][3]));
                                            mMap.addMarker(new MarkerOptions().position(now)
                                                    .title("Name: "+parking[i][0]).snippet("Description: "+parking[i][1]));
                                            System.out.println("marking"+parking[i][0]);
                                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                                @Override
                                                public boolean onMarkerClick(Marker marker) {
                                                    if(!occupying)
                                                        selected=marker.getTitle().toString().substring(6);
                                                        parkingview.setText("Do you want to park at "+selected+"?");
                                                        return false;
                                                }
                                            });
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }.start();
            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        LatLng troy = new LatLng(31.8072459,-86.0326418);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(troy));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

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
                if (ActivityCompat.checkSelfPermission(findActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(findActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
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

    public void updateGPSInfo(final Location location){
        if (location != null ) {
            lat=location.getLatitude();
            lon=location.getLongitude();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        add=SendPost.getAdressFromGoogle(lat,lon);
                    }catch (Exception e){e.printStackTrace();}
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                currentlocation.setText(lat+", "+lon);
                                address.setText(add);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }).start();
        }
    }


}
