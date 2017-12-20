package com.example.hi284.androidproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class StartActivity extends AppCompatActivity implements OnMapReadyCallback {
    final private int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 0;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mCurrentLocation;
    private GoogleMap mGoogleMap = null;
    private DBHelper mDbHelper;
    private Address bestResult = new Address(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mDbHelper = new DBHelper(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (!checkLocationPermissions()) {
            requestLocationPermissions(REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION);
        } else {
            getLastLocation(); //현재 위치로 이동
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button findButton = (Button)findViewById(R.id.button);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAddress();
            }
        });
    }



    @Override // 메뉴버튼 추가 (현재위치 이동버튼, km설정 버튼)
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rest_add_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView textView =(TextView)findViewById(R.id.result);
        switch (item.getItemId()){
            case R.id.add_map:
                mGoogleMap.clear();
                getLastLocation();// 현재위치로 이동
                getRestIn(1);
                return true;
            case R.id.add_marker1 :
                mGoogleMap.clear();
                getAddress();
                getRestIn(1);
                return true;
            case R.id.add_marker2 :
                mGoogleMap.clear();
                getAddress();
                getRestIn(2);
                return true;
            case R.id.add_marker3 :
                mGoogleMap.clear();
                getAddress();
                getRestIn(3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // 권한 설정
    private boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions(int requestCode) {
        ActivityCompat.requestPermissions(
                StartActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                requestCode    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
        );
    }

    // 현재 위치로 이동하기
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        Task task = mFusedLocationClient.getLastLocation();
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() { // Task<Location> 객체 반환
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    mCurrentLocation = location;
                    bestResult.setLatitude(mCurrentLocation.getLatitude());
                    bestResult.setLongitude(mCurrentLocation.getLongitude());
                    LatLng curLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()); // 현재위치의 위도, 경도값 얻어오기
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation,15)); // 줌레벨 15로 현재위치로 이동
                    //updateUI();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_location_detected), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //  googleMap 객체를 얻어오기
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMarkerClickListener(new MyMarkerClickListener());
    }

    // 주소검색
    private void getAddress() {
        EditText addressEdit = (EditText)findViewById(R.id.edit_text);
        TextView addressResult = (TextView)findViewById(R.id.result);
        String input = addressEdit.getText().toString();
        Cursor c = mDbHelper.getRestAlready(addressEdit.getText().toString());
        c.moveToFirst();

        if(c.getCount() != 0) {
            try {
                mGoogleMap.clear();
                LatLng location = new LatLng(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
                mGoogleMap.addMarker(
                        new MarkerOptions().position(location).alpha(0.8f).icon(BitmapDescriptorFactory.fromResource(R.drawable.res_location))
                );
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
                getRestIn(1); // 기본으로 1KM이내에 있는 주변맛집 띄워줌
                addressResult.setText(String.format("[ %s , %s ]",
                        c.getString(4),
                        c.getString(5)));
            } catch (Exception e) {
                Log.e(getClass().toString(),"Failed in using Geocoder.", e);
                return;
            }
        } else {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.KOREA);
                List<Address> addresses = geocoder.getFromLocationName(input,1);
                if (addresses.size() >0) {
                    mGoogleMap.clear();
                    bestResult = (Address) addresses.get(0);
                    LatLng location = new LatLng(bestResult.getLatitude(), bestResult.getLongitude());
                    mGoogleMap.addMarker(
                            new MarkerOptions().position(location).alpha(0.8f).icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location))
                    );
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
                    getRestIn(1); // 기본으로 1KM이내에 있는 주변맛집 띄워줌
                    addressResult.setText(String.format("[ %s , %s ]",
                            bestResult.getLatitude(),
                            bestResult.getLongitude()));
                }
            } catch (IOException e) {
                Log.e(getClass().toString(),"Failed in using Geocoder.", e);
                return;
            }
        }
    }

    //마커 클릭 이벤트
    String resLat;
    String resLng;
    class MyMarkerClickListener implements GoogleMap.OnMarkerClickListener {

        @Override
        public boolean onMarkerClick(Marker marker) {
            resLat = Double.toString(marker.getPosition().latitude);
            resLng = Double.toString(marker.getPosition().longitude);
            String resName;
            String resNum;
            Cursor c = mDbHelper.checkRest(resLat, resLng);

            if (c.getCount() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);

                builder.setTitle("맛집 등록")
                        .setMessage("새로운 맛집으로 등록하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(
                                        getApplicationContext(), // 현재화면의 제어권자
                                        MainActivity.class); // 다음넘어갈 화면
                                intent.putExtra("rest_lat", resLat);
                                intent.putExtra("rest_lng", resLng);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                c.moveToFirst();
                resName = c.getString(1);
                resNum = c.getString(2);

                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        Restaurant.class); // 다음넘어갈 화면
                intent.putExtra("rest_name", resName);
                intent.putExtra("rest_number", resNum);
                startActivity(intent);
            }

            return false;
        }
    }

    // KM 선택시 반경 KM내의 존재하는 음식점 보여주기
    private void getRestIn(int i) {
        Cursor c = mDbHelper.getAllRest();
        c.moveToFirst();

        Location mLocation = new Location("Point Current");
        Location rLocation = new Location("Point Rest");
        mLocation.setLatitude(bestResult.getLatitude());
        mLocation.setLongitude(bestResult.getLongitude());

        for(int count = 0 ; count < c.getCount() ; count++) {
            rLocation.setLatitude(Double.parseDouble(c.getString(4)));
            rLocation.setLongitude(Double.parseDouble(c.getString(5)));
            double distance = mLocation.distanceTo(rLocation);

            if(distance <= i*1000) {
                LatLng location = new LatLng(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
                mGoogleMap.addMarker(
                        new MarkerOptions().position(location).alpha(0.8f).icon(BitmapDescriptorFactory.fromResource(R.drawable.res_location))
                );
            }
            c.moveToNext();
        }
    }
}