package com.example.bluetoothwifikamerabaglama;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Wifi extends AppCompatActivity {

    private static final int REQUEST_CODE_WIFI = 100;
    private ToggleButton toggleWifi;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        toggleWifi = findViewById(R.id.toggleWifi);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Başlangıçta izin kontrolü yap
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_WIFI_STATE}, REQUEST_CODE_WIFI);
        } else {
            initializeToggle();
        }
    }

    private void initializeToggle() {
        toggleWifi.setChecked(wifiManager.isWifiEnabled());

        toggleWifi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
                wifiManager.setWifiEnabled(isChecked);
                Toast.makeText(this, isChecked ? "WiFi Açıldı" : "WiFi Kapatıldı", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "WiFi ayarlarına erişim izni yok", Toast.LENGTH_SHORT).show();
                toggleWifi.setChecked(!isChecked); // izin yoksa toggle geri çevir
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_WIFI) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeToggle();
            } else {
                Toast.makeText(this, "WiFi kontrolü için izin reddedildi", Toast.LENGTH_SHORT).show();
                toggleWifi.setEnabled(false); // izin verilmezse butonu devre dışı bırak
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
