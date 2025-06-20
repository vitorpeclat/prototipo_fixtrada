package com.example.prototipo_fixtrada;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototipo_fixtrada.construtores.PrestadorServico;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private final List<Marker> marcadoresMecanicos = new ArrayList<>();

    private LinearLayout bottomSheet;
    private RecyclerView recyclerView;
    private BottomSheetBehavior<View> sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        bottomSheet = findViewById(R.id.bottom_sheet);
        recyclerView = findViewById(R.id.recyclerMecanicos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // Botão Solicitar Serviço
        findViewById(R.id.btnSolicitarServico).setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationChangeListener(location -> {
                    exibirPrestadoresProximos(location);
                    Toast.makeText(this, "Procurando mecânicos próximos...", Toast.LENGTH_SHORT).show();
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    mMap.setOnMyLocationChangeListener(null);
                });
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        });

        // Botão Avaliar
        findViewById(R.id.btnAvaliar).setOnClickListener(v -> avaliarPrestador());

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
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
            Log.e("Maps", "Arquivo de estilo não encontrado.", e);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        LatLng destaque = new LatLng(-23.5505, -46.6333);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destaque, 13));
    }

    private void exibirPrestadoresProximos(Location cliente) {
        Banco db = new Banco(this);
        List<PrestadorServico> prestadores = db.listarPrestadoresComEndereco();
        List<PrestadorServico> proximos = new ArrayList<>();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        for (Marker marker : marcadoresMecanicos) {
            marker.remove();
        }
        marcadoresMecanicos.clear();

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        boundsBuilder.include(new LatLng(cliente.getLatitude(), cliente.getLongitude()));

        for (PrestadorServico p : prestadores) {
            String endereco = p.getPreEndereco();
            if (endereco == null || endereco.trim().isEmpty()) continue;

            try {
                List<Address> resultados = geocoder.getFromLocationName(endereco, 1);
                if (resultados != null && !resultados.isEmpty()) {
                    Address enderecoGeo = resultados.get(0);
                    double lat = enderecoGeo.getLatitude();
                    double lon = enderecoGeo.getLongitude();

                    if (lat == 0 && lon == 0) continue;

                    Location localMecanico = new Location("");
                    localMecanico.setLatitude(lat);
                    localMecanico.setLongitude(lon);

                    float distancia = cliente.distanceTo(localMecanico);
                    if (distancia <= 5000) {
                        LatLng pos = new LatLng(lat, lon);
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(pos)
                                .title(p.getPreNome())
                                .snippet(endereco));
                        if (marker != null) {
                            marker.showInfoWindow();
                            marcadoresMecanicos.add(marker);
                            boundsBuilder.include(pos);
                            proximos.add(p);
                        }
                    }
                }
            } catch (IOException e) {
                Log.e("Geocoder", "Erro ao converter endereço: " + endereco, e);
            }
        }

        if (!proximos.isEmpty()) {
            recyclerView.setAdapter(new MecanicoAdapter(proximos));
            recyclerView.setVisibility(View.VISIBLE);
            if (proximos.size() == 1) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marcadoresMecanicos.get(0).getPosition(), 15));
            } else {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 150));
            }
        } else {
            recyclerView.setVisibility(View.GONE);
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

    public void avaliarPrestador() {
        PopupAvaliacao popup = new PopupAvaliacao(Maps.this);
        popup.show();
    }
}
