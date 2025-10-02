package com.example.labiot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.example.labiot.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener( view -> {
            if (tengoInternet()){
                Intent intent = new Intent(this, AppActivity.class);
                startActivity(intent);
            }
            else {
                showInternetDialog();
            }

        });
    }

    public boolean tengoInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean tieneInternet = false;
        if (connectivityManager != null) {
            NetworkCapabilities capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("msg-Internet", "NetworkCapabilities.TRANSPORT_CELLULAR");
                    tieneInternet = true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("msg-Internet", "NetworkCapabilities.TRANSPORT_WIFI");
                    tieneInternet = true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("msg-Internet", "NetworkCapabilities.TRANSPORT_ETHERNET");
                    tieneInternet = true;
                }
            }
        }
        return tieneInternet;
    }

    private void showInternetDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sin conexión a internet")
                .setMessage("Para ingresar a la aplicación necesitas conexión a internet. ¿Deseas ir a configuración?")
                .setPositiveButton("Configuración", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("Cancelar", (dialog, which) ->
                    dialog.dismiss())
                    .show();
    }
}