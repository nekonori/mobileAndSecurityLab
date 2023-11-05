package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.btn);
        button.setOnClickListener(e -> getLocation());
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                textView.setText("Your location is " + location.getLatitude() + ", " + location.getLongitude());
            } else {
                textView.setText("Something went wrong");
            }
        });
    }
}