package com.example.prototipo_fixtrada;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.prototipo_fixtrada.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private final List<Marker> marcadoresMecanicos = new ArrayList<>();

    private final String[] nomesMecanicos = {
            "Mecânica do Zé",
            "Auto Center Paulista",
            "Oficina Rápida SP",
            "Mecânica 24h",
            "Garage Master"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        binding.btnChat.setOnClickListener(v -> {
            Intent intent = new Intent(Maps.this, ChatActivity.class);
            startActivity(intent);
        });

        binding.btnSolicitarServico.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationChangeListener(location -> {
                    exibirPrestadoresProximos(location);
                    Toast.makeText(this, "Serviço solicitado! Procurando mecânicos próximos...", Toast.LENGTH_SHORT).show();
                    // Remove listener após uso único
                    mMap.setOnMyLocationChangeListener(null);
                });
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

            if (!success) {
                Log.e("Maps", "Falha ao aplicar o estilo do mapa.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Maps", "Arquivo de estilo não encontrado. ", e);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        // Opcional: ponto fixo como destaque inicial
        LatLng destaque = new LatLng(-23.5505, -46.6333);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destaque, 13));
    }

    private void exibirPrestadoresProximos(Location cliente) {
        Banco db = new Banco(this);
        List<PrestadorServico> prestadores = db.listarPrestadoresComEndereco();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        // Limpa os marcadores anteriores
        for (Marker marker : marcadoresMecanicos) {
            marker.remove();
        }
        marcadoresMecanicos.clear();

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        boundsBuilder.include(new LatLng(cliente.getLatitude(), cliente.getLongitude()));

        for (PrestadorServico p : prestadores) {
            try {
                List<Address> results = geocoder.getFromLocationName(p.getPreEndereco(), 1);
                if (results != null && !results.isEmpty()) {
                    double lat = results.get(0).getLatitude();
                    double lon = results.get(0).getLongitude();
                    Location localMecanico = new Location("");
                    localMecanico.setLatitude(lat);
                    localMecanico.setLongitude(lon);

                    float distancia = cliente.distanceTo(localMecanico); // em metros

                    if (distancia <= 5000) { // 5km
                        LatLng pos = new LatLng(lat, lon);
                        Marker marcador = mMap.addMarker(new MarkerOptions()
                                .position(pos)
                                .title(p.getPreNome())
                                .snippet(p.getPreEndereco()));

                        marcador.showInfoWindow();
                        marcadoresMecanicos.add(marcador);
                        boundsBuilder.include(pos);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (marcadoresMecanicos.size() >= 2) {
            LatLngBounds bounds = boundsBuilder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
        } else if (marcadoresMecanicos.size() == 1) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    marcadoresMecanicos.get(0).getPosition(), 15));
        } else {
            Toast.makeText(this, "Nenhum mecânico encontrado num raio de 5 km", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
