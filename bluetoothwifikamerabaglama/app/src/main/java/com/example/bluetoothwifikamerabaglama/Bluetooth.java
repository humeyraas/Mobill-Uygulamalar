package com.example.bluetoothwifikamerabaglama;



import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Set;

public class Bluetooth extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private Button btnEnable, btnDisable, btnVisible, btnPairedDevices;
    private ListView listDevices;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DISCOVERABLE_BT = 2;
    private static final int REQUEST_PERMISSION_BT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        btnEnable = findViewById(R.id.btnEnable);
        btnDisable = findViewById(R.id.btnDisable);
        btnVisible = findViewById(R.id.btnVisible);
        btnPairedDevices = findViewById(R.id.btnPairedDevices);
        listDevices = findViewById(R.id.listDevices);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth desteklenmiyor", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // İzin kontrolü yap
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_PERMISSION_BT);
        }

        btnEnable.setOnClickListener(v -> {
            if (!bluetoothAdapter.isEnabled()) {
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
            } else {
                Toast.makeText(this, "Bluetooth zaten açık", Toast.LENGTH_SHORT).show();
            }
        });
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        btnDisable.setOnClickListener(v -> {
            if (bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.disable();
                Toast.makeText(this, "Bluetooth kapatıldı", Toast.LENGTH_SHORT).show();
            }
        });

        btnVisible.setOnClickListener(v -> {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(intent, REQUEST_DISCOVERABLE_BT);
        });

        btnPairedDevices.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                ArrayList<String> deviceList = new ArrayList<>();

                for (BluetoothDevice device : pairedDevices) {
                    deviceList.add(device.getName() + "\n" + device.getAddress());
                }

                listDevices.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceList));
            } else {
                Toast.makeText(this, "Bluetooth izinleri verilmedi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            Toast.makeText(this, resultCode == RESULT_OK ? "Bluetooth açıldı" : "Bluetooth açılmadı", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == REQUEST_DISCOVERABLE_BT) {
            Toast.makeText(this, "Cihaz " + resultCode + " sn görünür olacak", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_BT && (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(this, "Bluetooth izni reddedildi", Toast.LENGTH_SHORT).show();
            finish(); // Uygulama çalışmasın
        }
    }
}

