package com.example.prototipo_fixtrada;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.List;

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

        binding.btnSolicitarServico.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationChangeListener(location -> {
                    adicionarMecanicosProximos(location);
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

    private void adicionarMecanicosProximos(Location cliente) {
        // Limpa marcadores anteriores
        for (Marker marker : marcadoresMecanicos) {
            marker.remove();
        }
        marcadoresMecanicos.clear();

        double lat = cliente.getLatitude();
        double lon = cliente.getLongitude();
        LatLng posCliente = new LatLng(lat, lon);

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        boundsBuilder.include(posCliente);

        List<Marker> novosMarcadores = new ArrayList<>();

        for (int i = 0; i < nomesMecanicos.length; i++) {
            double[] desloc = gerarDeslocamentoAleatorio(5);
            double latMec = lat + desloc[0];
            double lonMec = lon + desloc[1];

            LatLng pos = new LatLng(latMec, lonMec);
            Marker marcador = mMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(nomesMecanicos[i]));

            novosMarcadores.add(marcador);
            marcadoresMecanicos.add(marcador);
            boundsBuilder.include(pos);
        }

        // Ajusta câmera para enquadrar todos
        int padding = 120;
        LatLngBounds bounds = boundsBuilder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

        // Aguarda 400ms e mostra os balões de todos os marcadores
        new android.os.Handler().postDelayed(() -> {
            for (Marker m : novosMarcadores) {
                m.showInfoWindow();
            }
        }, 400); // delay em milissegundos
    }

    private double[] gerarDeslocamentoAleatorio(double raioKm) {
        double raioEmGraus = raioKm / 111.32;
        double u = Math.random();
        double v = Math.random();
        double w = raioEmGraus * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double deslocLat = w * Math.cos(t);
        double deslocLon = w * Math.sin(t) / Math.cos(Math.toRadians(-23.55));
        return new double[]{deslocLat, deslocLon};
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
