package com.example.gpsmapapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast; // Importado
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.maps.CameraUpdateFactory; // Importado
import com.google.android.gms.maps.GoogleMap; // Importado
import com.google.android.gms.maps.OnMapReadyCallback; // Importado
import com.google.android.gms.maps.SupportMapFragment; // Importado
import com.google.android.gms.maps.model.LatLng; // Importado
import com.google.android.gms.maps.model.MarkerOptions; // Importado
import com.google.android.gms.tasks.OnSuccessListener; // Importado
import com.google.android.gms.location.FusedLocationProviderClient; // Importado
import com.google.android.gms.location.LocationServices; // Importado

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Constantes
    private static final int LOCATION_REQUEST_CODE = 101; // Definida

    // Variables de clase
    private GoogleMap mMap; // Renombrada de 'Map' a 'mMap' (práctica estándar)
    private FusedLocationProviderClient fusedLocationClient; // Declarada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Establecer layout

        // 1. Inicialización del SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        // 2. Inicialización del cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // 3. Verificar permisos de ubicación (Lo que preguntaste)
        checkLocationPermission();
    }

    // Método que implementa la interfaz OnMapReadyCallback
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap; // Asignar el mapa

        // Opcional: Habilitar la capa de ubicación (punto azul) si ya hay permiso.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getCurrentLocation(); // Llamar a la ubicación actual
        }

        // 4. Agregar marcador al hacer long-click (Funcionalidad extra del mapa)
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marcador Añadido"));
            }
        });
    }

    // Método para verificar y solicitar permisos (Tu pregunta)
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) { // Verifica permiso

            ActivityCompat.requestPermissions(this, // Solicita permiso
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }

    // 5. Método para obtener la ubicación actual (Basado en el código de la pauta)
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Ubicación Actual"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15)); // Mueve la cámara
                } else {
                    Toast.makeText(MainActivity.this, "No se pudo obtener la ubicación.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 6. Respuesta después de solicitar permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, intenta obtener la ubicación.
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    getCurrentLocation();
                }
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}