package com.example.assignment3;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST = 100;

    TextView statusText;
    TextView locationText;
    TextView savedLocationsText;

    Button startButton;
    Button stopButton;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        locationText = findViewById(R.id.locationText);
        savedLocationsText = findViewById(R.id.savedLocationsText);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        sharedPreferences =
                getSharedPreferences(
                        "LocationPrefs",
                        MODE_PRIVATE);

        loadSavedLocations();

        startButton.setOnClickListener(view -> {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        },
                        LOCATION_PERMISSION_REQUEST);

                return;
            }

            startTracking();
        });

        stopButton.setOnClickListener(view -> {

            statusText.setText("Status: Tracking Stopped");

            Intent serviceIntent =
                    new Intent(this, LocationService.class);

            stopService(serviceIntent);
        });
    }

    private void startTracking() {

        statusText.setText("Status: Tracking Active");

        Intent serviceIntent =
                new Intent(this, LocationService.class);

        startService(serviceIntent);
    }

    private void loadSavedLocations() {

        String savedData =
                sharedPreferences.getString(
                        "saved_locations",
                        "No saved locations yet.");

        savedLocationsText.setText(savedData);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadSavedLocations();

        SharedPreferences sharedPreferences =
                getSharedPreferences(
                        "LocationPrefs",
                        MODE_PRIVATE);

        String savedData =
                sharedPreferences.getString(
                        "saved_locations",
                        "No saved locations yet.");

        savedLocationsText.setText(savedData);

        if (!savedData.equals("No saved locations yet.")) {

            String[] entries =
                    savedData.split("\n\n");

            if (entries.length > 0) {

                locationText.setText(
                        entries[entries.length - 1]);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {

            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {

                statusText.setText("Permission Granted");

                startTracking();
            }
            else {

                statusText.setText("Permission Denied");
            }
        }
    }
}