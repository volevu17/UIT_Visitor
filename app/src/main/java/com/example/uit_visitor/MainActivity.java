package com.example.uit_visitor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.example.uit_visitor.Models.Asset;
import com.example.uit_visitor.Models.Map;
import com.example.uit_visitor.Retrofit_Config.APIClient;
import com.example.uit_visitor.Retrofit_Config.APIMethods;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uit_visitor.databinding.ActivityMainBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    final String[] permissions={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private MapView mapView = null;
    public Map map = null;
    public Asset asset = null;
    public Asset asset1 = null;
    public Asset asset2 = null;
    //hello ba da
    double latitude, longtitude;
    final String assetID = "6H4PeKLRMea1L0WsRXXWp9";
    final String assetID1 = "2UZPM2Mvu11Xyq5jCWNMX1";
    final String assetID2 = "4cdWlxEvmDRBBDEc2HRsaF";

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Welcome", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        mapView = (MapView) findViewById(R.id.mapView);

        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.setHorizontalMapRepetitionEnabled(false);
        mapView.setVerticalMapRepetitionEnabled(false);
        mapView.setScrollableAreaLimitLatitude(MapView.getTileSystem().getMaxLatitude(),MapView.getTileSystem().getMinLatitude(),0);
        CompassOverlay compassOverlay = new CompassOverlay(this, mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);
        mapView.getOverlays().add(new RotationGestureOverlay(mapView));
        IMapController mapController = mapView.getController();


        APIMethods methods = APIClient.getRetrofitInstance().create(APIMethods.class);
        Call<Map> call = methods.getMap();
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {


                map = response.body();
                latitude = Double.parseDouble(String.valueOf(map.options.get("default").getAsJsonObject().get("center").getAsJsonArray().get(1)));
                longtitude = Double.parseDouble(String.valueOf(map.options.get("default").getAsJsonObject().get("center").getAsJsonArray().get(0)));
                mapController.setZoom(20.0);
                GeoPoint geoPoint = new GeoPoint(latitude, longtitude);
//                    double bound1 = Double.parseDouble(String.valueOf(map.options.get("default").getAsJsonObject().get("bounds").getAsJsonArray().get(0)));
//                    double bound2 = Double.parseDouble(String.valueOf(map.options.get("default").getAsJsonObject().get("bounds").getAsJsonArray().get(1)));
//                    double bound3 = Double.parseDouble(String.valueOf(map.options.get("default").getAsJsonObject().get("bounds").getAsJsonArray().get(2)));
//                    double bound4 = Double.parseDouble(String.valueOf(map.options.get("default").getAsJsonObject().get("bounds").getAsJsonArray().get(3)));
//                    mapView.setScrollableAreaLimitDouble(new BoundingBox(bound2, bound1, bound4, bound3));
                mapController.setCenter(geoPoint);
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {


            }
        });

        Call<Asset> call1 = methods.getAsset(assetID);
        call1.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                asset = response.body();
                Marker marker = new Marker(mapView);
                double xcoor = Double.parseDouble(String.valueOf(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().get("coord").getAsJsonObject().get("lat")));
                double ycoor = Double.parseDouble(String.valueOf(asset.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().get("coord").getAsJsonObject().get("lon")));
                GeoPoint weatherGeoPoint= new GeoPoint(xcoor, ycoor);
                marker.setPosition(weatherGeoPoint);
                marker.setTitle("weather asset1");
                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        asset_detail_info asset_info = asset_detail_info.newInstance(asset);
                        asset_info.show(getSupportFragmentManager(),"TAG");
                        return true;
                    }
                });
                mapView.getOverlays().add(marker);

            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {

            }
        });
        Call<Asset> call2 = methods.getAsset(assetID1);
        call2.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                asset1 = response.body();
                Marker marker = new Marker(mapView);
                double xcoor = Double.parseDouble(String.valueOf(asset1.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().get("coord").getAsJsonObject().get("lat")));
                double ycoor = Double.parseDouble(String.valueOf(asset1.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().get("coord").getAsJsonObject().get("lon")));
                GeoPoint weatherGeoPoint= new GeoPoint(21.1, ycoor);
                marker.setPosition(weatherGeoPoint);
                marker.setTitle("weather asset2");
                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        asset_detail_info asset_info = asset_detail_info.newInstance(asset1);
                        asset_info.show(getSupportFragmentManager(),"TAG1");
                        return true;
                    }
                });
                mapView.getOverlays().add(marker);

            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {

            }
        });
        Call<Asset> call3 = methods.getAsset(assetID2);
        call3.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                asset2 = response.body();
                Marker marker = new Marker(mapView);
                double xcoor = Double.parseDouble(String.valueOf(asset2.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().get("coord").getAsJsonObject().get("lat")));
                double ycoor = Double.parseDouble(String.valueOf(asset2.attributes.get("weatherData").getAsJsonObject().get("value").getAsJsonObject().get("coord").getAsJsonObject().get("lon")));
                GeoPoint weatherGeoPoint= new GeoPoint(21.2, ycoor);
                marker.setPosition(weatherGeoPoint);
                marker.setTitle("weather asset3");
                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        asset_detail_info asset_info = asset_detail_info.newInstance(asset2);
                        asset_info.show(getSupportFragmentManager(),"TAG2");
                        return true;
                    }
                });
                mapView.getOverlays().add(marker);

            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {

            }
        });





        requestPermissionsIfNecessary(permissions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions,  grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}