package com.example.assignment3;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service {

    FusedLocationProviderClient fusedLocationClient;

    LocationCallback locationCallback;

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        sharedPreferences =
                getSharedPreferences("LocationPrefs", MODE_PRIVATE);

        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(
                    LocationResult locationResult) {

                if (locationResult == null) {
                    return;
                }

                for (Location location :
                        locationResult.getLocations()) {

                    double latitude =
                            location.getLatitude();

                    double longitude =
                            location.getLongitude();

                    String locationData =
                            "Latitude: " + latitude +
                                    "\nLongitude: " + longitude;

                    String savedData =
                            sharedPreferences.getString(
                                    "saved_locations",
                                    "");

                    sharedPreferences.edit()
                            .putString(
                                    "saved_locations",
                                    savedData
                                            + locationData
                                            + "\n\n")
                            .apply();
                }
            }
        };
    }

    @Override
    public int onStartCommand(
            Intent intent,
            int flags,
            int startId) {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            stopSelf();

            return START_NOT_STICKY;
        }

        LocationRequest locationRequest =
                LocationRequest.create();

        locationRequest.setInterval(5000);

        locationRequest.setFastestInterval(3000);

        locationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper());

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        fusedLocationClient.removeLocationUpdates(
                locationCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}