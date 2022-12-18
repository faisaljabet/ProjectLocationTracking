package com.example.locationtracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    //Current Location
    boolean isPermissionGranted;
    GoogleMap mGoogleMap;
    private FusedLocationProviderClient mLocationClient;
    private int GPS_REQUEST_CODE = 9001;
    private String currentLatitude;
    private String currentLongitude;


    public static Integer id;
    private GoogleMap mMap;

    // creating array list for adding all our fields.
    public static ArrayList<String> project_names;
    public static ArrayList<String> categories;
    public static ArrayList<String> affiliated_agencies;
    public static ArrayList<String> descriptions;
    public static ArrayList<String> project_start_times;
    public static ArrayList<String> project_completion_times;
    public static ArrayList<String> total_budgets;
    public static ArrayList<String> completion_percentages;
    public static ArrayList<LatLng> locations;
    public static ArrayList<Integer> locationIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Current Location
        isGPSenable();
        checkMyPermission();
        initMap();
        mLocationClient = new FusedLocationProviderClient(this);
        markCurrLoc();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this);

        project_names = new ArrayList<>();
        categories = new ArrayList<>();
        affiliated_agencies = new ArrayList<>();
        descriptions = new ArrayList<>();
        project_start_times = new ArrayList<>();
        project_completion_times = new ArrayList<>();
        total_budgets = new ArrayList<>();
        completion_percentages = new ArrayList<>();
        // in below line we are initializing our array list.
        locations = new ArrayList<>();
        locationIndex = new ArrayList<>();
        locationIndex.add(0);
        locationIndex.add(0);
        locationIndex.add(1);
        locationIndex.add(1);
        locationIndex.add(2);
        locationIndex.add(3);
        locationIndex.add(3);
        locationIndex.add(3);
        locationIndex.add(4);
        locationIndex.add(4);
        locationIndex.add(4);
        locationIndex.add(5);
        locationIndex.add(6);
        locationIndex.add(7);
        locationIndex.add(8);
        locationIndex.add(9);
        locationIndex.add(10);
        locationIndex.add(11);

        project_names.add("JICA Support Program 3 for Strengthening Mathematics and Science Education in Primary Education Project");
        project_names.add("Project for Capacity Building of Nursing Services Phase 2");
        project_names.add("The Project for Strengthening Health Systems through Organizing Communities");
        project_names.add("Safe Motherhood Promotion Project");
        project_names.add("Safe Motherhood Promotion Project Phase 2");
        project_names.add("National Integrity Strategy Support Project Phase 2");
        project_names.add("Project for Capacity Development of City Corporations (C4C)");
        project_names.add("Strengthening Paurashava Governance Project (SPGP)");
        project_names.add("Strengthening Public Investment Management System (SPIMS) Project Phase 2");
        project_names.add("Upazila Integrated Capacity Development Project (UICDP)");
        project_names.add("The Integrated Energy and Power Master Plan Project in Bangladesh");
        project_names.add("The Project for Gas Network System Digitalization and Improvement of Operational Efficiency in Gas Sector in Bangladesh");


        categories.add("Education");
        categories.add("Health");
        categories.add("Health");
        categories.add("Health");
        categories.add("Health");
        categories.add("Governance");
        categories.add("Governance");
        categories.add("Governance");
        categories.add("Governance");
        categories.add("Governance");
        categories.add("Energy and Mining");
        categories.add("Energy and Mining");


        descriptions.add("National level of academic skills and knowledge obtained in primary mathematics and science is improved.\n");
        descriptions.add("The quality of nursing education is improved in Bangladesh\n");
        descriptions.add("The Health status of population in Bangladesh is improved\n");
        descriptions.add("Approaches of Reproductive Health (RH) services extracted from the Project are standardized and applied to other districts.\n");
        descriptions.add("Maternal and neonatal health status is improved in Bangladesh.\n");
        descriptions.add("Transparency and accountability system of the public administration and associated institutions is enhanced.\n");
        descriptions.add("Functions and organizational structure of the target City Corporations (CCs) are optimized.\n");
        descriptions.add("Measures of Pourashava capacity development are taken nation-wide based on mid-long term strategy.\n");
        descriptions.add("Public investment contributes to achieving mid-long term development plan\n");
        descriptions.add("Promoting development works and public service delivery, based on the regional characteristics, through strengthened capacity of Upazila Parishad.\n");
        descriptions.add("A low/zero carbon energy demand/supply system will be established based on the premise of ensuring energy security and economic viability.\n");
        descriptions.add("Reliable, effective and efficient gas and power supply for economic development of the country is achieved.\n");

        affiliated_agencies.add("Ministry of Primary and Mass Education, Directorate of Primary Education\n");
        affiliated_agencies.add("Directorate General of Nursing and Midwifery Services (DGNM), Bangladesh Nursing and Midwifery Council (BNMC)\n");
        affiliated_agencies.add("Operational Plans (OPs) of the Ministry of Health and Family Welfare/ Sectors-Wide Program Management and Monitoring, Health Economics and Finance, Non-communicable Disease Control (NCDC), Community Based Healthcare (CBHC), Upazila Health Care (UHC), Hospital Services Management (HSM), Lifestyle and Health Education Promotion (L & HEP)\n");
        affiliated_agencies.add("Ministry of Health and Family Welfare*2, Directorate of Family Planning, Directorate of Health Services, District Family Planning Office, District Health Services Office, and Upazila Health Complex\n");
        affiliated_agencies.add("Ministry of Health and Family Welfare*2, Directorate of Family Planning, Directorate of Health Services, District Family Planning Office, District Health Services Office, and Upazila Health Complex\n");
        affiliated_agencies.add("Cabinet Division of the Government of Bangladesh\n");
        affiliated_agencies.add("Local Government Division (LGD), Ministry of Local Government, Rural Development and Cooperatives (MLGRD&C)\n");
        affiliated_agencies.add("Local Government Division (LGD), Ministry of Local Government, Rural Development and Cooperatives (MLGRD&C)\n");
        affiliated_agencies.add("Programming Division, Planning Commission, Ministry of Planning\n");
        affiliated_agencies.add("Local Government Division (LGD), Ministry of Local Government, Rural Development and Cooperatives (MLGRD&C)\n");
        affiliated_agencies.add("MoPEMR (Ministry of Power, Energy and Mineral Resources)\n");
        affiliated_agencies.add("Energy and Mineral Resources Division (EMRD) Ministry of Power, Energy and Mineral Resources MoPEMR)\n");
        // on below line we are adding our
        // locations in our array list.
        locations.add(new LatLng(23.729211164246585, 90.40874895549243));
        locations.add(new LatLng(23.801862310944188, 90.35700996898726));
        locations.add(new LatLng(23.768773179764562, 90.37269632665758));
        locations.add(new LatLng(23.733211657612223, 90.40993638432778));
        locations.add(new LatLng(23.728881264793493, 90.40888399782175));
        locations.add(new LatLng(23.728881264793493, 90.40888399782175));
        locations.add(new LatLng(23.75363622259384, 90.39417243785537));
        locations.add(new LatLng(23.780215725696586, 90.40895332665768));
        locations.add(new LatLng(23.728881264793493, 90.40888399782175));
        locations.add(new LatLng(23.75363622259384, 90.39417243785537));
        locations.add(new LatLng(23.780215725696586, 90.40895332665768));
        locations.add(new LatLng(23.728407931193587, 90.40787482665709));
        locations.add(new LatLng(23.7284766533655, 90.40910864263893));
        locations.add(new LatLng(23.7284766533655, 90.40910864263893));
        locations.add(new LatLng(23.7708680271343, 90.38098892695923));
        locations.add(new LatLng(23.7284766533655, 90.40910864263893));
        locations.add(new LatLng(23.728917093659554, 90.40956388277749));
        locations.add(new LatLng(23.728917093659554, 90.40956388277749));

        project_start_times.add("4/1/2019");
        project_start_times.add("3/26/2022");
        project_start_times.add("6/29/2017");
        project_start_times.add("7/1/2006");
        project_start_times.add("7/1/2011");
        project_start_times.add("7/1/2011");
        project_start_times.add("1/6/2016");
        project_start_times.add("2/15/2014");
        project_start_times.add("9/28/2019");
        project_start_times.add("9/16/2017");
        project_start_times.add("6/22/2021");
        project_start_times.add("1/27/2020");

        project_completion_times.add("6/30/2023");
        project_completion_times.add("3/25/2026");
        project_completion_times.add("7/28/2022");
        project_completion_times.add("6/30/2010");
        project_completion_times.add("6/30/2016");
        project_completion_times.add("6/30/2016");
        project_completion_times.add("6/5/2021");
        project_completion_times.add("10/16/2018");
        project_completion_times.add("9/27/2023");
        project_completion_times.add("12/15/2022");
        project_completion_times.add("1/31/2024");
        project_completion_times.add("12/22/2022");

        total_budgets.add("JPY 380M");
        total_budgets.add("JPY 300M");
        total_budgets.add("JPY 400M");
        total_budgets.add("JPY 350M");
        total_budgets.add("JPY 420M");
        total_budgets.add("JPY 400M");
        total_budgets.add("JPY 410M");
        total_budgets.add("JPY 150M");
        total_budgets.add("JPY 350M");
        total_budgets.add("JPY 405M");
        total_budgets.add("JPY 285M");
        total_budgets.add("JPY 180M");


        completion_percentages.add("77.36%");
        completion_percentages.add("22.15%");
        completion_percentages.add("97.55%");
        completion_percentages.add("100.00%");
        completion_percentages.add("100.00%");
        completion_percentages.add("100.00%");
        completion_percentages.add("99.50%");
        completion_percentages.add("100.00%");
        completion_percentages.add("80.48%");
        completion_percentages.add("100.00%");
        completion_percentages.add("58.87%");
        completion_percentages.add("99.43%");


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
        // inside on map ready method
        // we will be displaying all our markers.
        // for adding markers we are running for loop and
        // inside that we are drawing marker on our map.
        for (int i = 0; i < locations.size(); i++) {

            //below line is use to add marker to each location of our array list.
            //mMap.addMarker(new MarkerOptions().position(locations.get(i)).title(project_names.get(locationIndex.get(i))));

            Marker mk = mMap.addMarker(new MarkerOptions().position(locations.get(i)).title(categories.get(locationIndex.get(i))));
            mk.setTag(locationIndex.get(i));
            Integer id = (Integer) mk.getTag();

            // below lin is use to zoom our camera on map.
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            // below line is use to move our camera to the specific location.
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(locations.get(i)));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                id = (Integer) marker.getTag();
                startActivity(new Intent(MainActivity.this, ShowInfo.class));
                Log.i("Mashrafi", id + "");
                return false;
            }
        });

        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    private void initMap() {
        if (isPermissionGranted) {
            if (isGPSenable()) {
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                supportMapFragment.getMapAsync(this);
            }
        }
    }

    private boolean isGPSenable() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(providerEnable){
            return true;
        } else{
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("GPS is required for this app to operate. Please enable GPS")
                    .setPositiveButton("Yes", ((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false)
                    .show();
        }
        return false;
    }


    @SuppressLint("MissingPermission")
    private void markCurrLoc() {
        mLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Location location = task.getResult();
                currentLatitude = location.getLatitude() + "";
                currentLongitude = location.getLongitude() + "";
                gotoLocation(location.getLatitude(), location.getLongitude());
            }
        });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng LatLng = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng, 14);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void checkMyPermission() {

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GPS_REQUEST_CODE){
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(providerEnable){
                Toast.makeText(this, "GPS is enable", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "GPS is not enable", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
