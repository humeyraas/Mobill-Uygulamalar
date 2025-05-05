package com.example.bluetoothwifikamerabaglama;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button bluetoothBtn, wifiBtn, cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothBtn = findViewById(R.id.bluetoothBtn);
        wifiBtn = findViewById(R.id.wifiBtn);
        cameraBtn = findViewById(R.id.cameraBtn);

        bluetoothBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Bluetooth.class);
            startActivity(intent);
        });

        wifiBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Wifi.class);
            startActivity(intent);
        });

        cameraBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Camera.class);
            startActivity(intent);
        });
    }
}
