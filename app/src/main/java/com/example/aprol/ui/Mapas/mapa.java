package com.example.aprol.ui.Mapas;

import android.Manifest;
import android.app.AlertDialog;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.aprol.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class mapa extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private View root;
    private GoogleMap mMap;
    private Bundle mBundle;
    private static final int LOCATION_REQUEST_CODE = 1; // Para los permisos
    private boolean permisos = false;

    // Marcador actual
    private Marker marcadorActual = null;

    // Marcador marcadorTouch
    private Marker marcadorTouch = null;
    private Marker marcadorInicio;
    private Marker marcadorFinal;

    // Posición actual con eventos
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private Button btnstart;
    private Button btnstop;
    private TextView tvMapaMetros;
    private Button btnMapaGuardar;
    private ArrayList<LatLng> recorrido;
    private Timer timer = null;
    private Chronometer chronometer;
    private  String fichero_xml=null;
    private Marker avPuntos = null;

    // Para obtener el punto actual (no es necesario para el mapa)
    // Pero si para obtener las latitud y la longitud
    private FusedLocationProviderClient mPosicion;
    private Location miUltimaLocalizacion;
    private LatLng posicionDefecto = new LatLng(38.6901212, -4.1086075);
    private LatLng posActual = posicionDefecto;

    public mapa() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //se inician los componentes
        root = inflater.inflate(R.layout.fragment_mapa, container, false);
        mPosicion = LocationServices.getFusedLocationProviderClient(getActivity());



        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return root;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Solicitamos permisos de Localización
        solicitarPermisos();
        // Configurar IU Mapa
        configurarIUMapa();
        // activa el evento de marcadores Touch
        activarEventosMarcdores();
        // Obtenemos la posición GPS
        obtenerPosicion();
        // Situar la camara inicialmente a una posición determinada
        situarCamaraMapa();
        // Para usar eventos
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        // Crear el LocationRequest
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 segundos en milisegundos
                .setFastestInterval(1 * 1000); // 1 segundo en milisegundos
    }


    private void activarEventosMarcdores() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // Creamos el marcador
                // Borramos el marcador Touch si está puesto
                if (marcadorTouch != null) {
                    marcadorTouch.remove();
                }
                marcadorTouch = mMap.addMarker(new MarkerOptions()
                        // Posición
                        .position(point)
                        // Título
                        .title("Marcador Touch")
                        // Subtitulo
                        .snippet("Pinchaste Aquí")
                        // Color o tipo d icono
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                );
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));

            }
        });


    }
    private void situarCamaraMapa() {
        //se mueve la camara al usuario
        mMap.moveCamera(CameraUpdateFactory.newLatLng(posActual));
    }
    private void configurarIUMapa() {
        //se activan los eventos del marcador
        mMap.setOnMarkerClickListener(this);

        // Activar Boton de Posición actual
        if (permisos) {
            // Si tenemos permisos pintamos el botón de la localización actual
            // Esta posición la obtiene google automaticamente
            mMap.setMyLocationEnabled(true);
        }

        // Mapa híbrido, lo normal es usar el
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Que se vea la interfaz y la brújula por ejemplo
        // También podemos quitar gestos
        UiSettings uiSettings = mMap.getUiSettings();
        // Activamos los gestos
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        // Activamos la brújula
        uiSettings.setCompassEnabled(true);
        // Activamos los controles de zoom
        uiSettings.setZoomControlsEnabled(true);
        // Activamos la brújula
        uiSettings.setCompassEnabled(true);
        // Actiovamos la barra de herramientas
        uiSettings.setMapToolbarEnabled(true);

        // Hacemos el zoom por defecto mínimo
        mMap.setMinZoomPreference(13.0f);
        // Señalamos el tráfico
        mMap.setTrafficEnabled(true);
    }
    // Evento de procesar o hacer click en un marker
    @Override
    public boolean onMarkerClick(Marker marker) {
        // al pulsar en el marcador te pondra la latitud y longitud de ese lugar
        String titulo = marker.getTitle();
        switch (titulo) {

            case "Marcador Touch":
                Toast.makeText(getContext(), "Estás en: " + marker.getPosition().latitude + "," + marker.getPosition().longitude,
                        Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return false;
    }

    // Obtenermos y leemos directamente el GPS
    private void obtenerPosicion() {
        try {
            if (permisos) {
                // Lo lanzamos como tarea concurrente
                Task<Location> local = mPosicion.getLastLocation();
                local.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Actualizamos la última posición conocida
                            miUltimaLocalizacion = task.getResult();
                            posActual = new LatLng(miUltimaLocalizacion.getLatitude(),
                                    miUltimaLocalizacion.getLongitude());
                            // Añadimos un marcador especial para poder operar con esto
                            marcadorPosicionActual();


                        } else {
                            Log.d("GPS", "No se encuetra la última posición.");
                            Log.e("GPS", "Exception: %s", task.getException());
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        } catch (Exception e) {

        }
    }

    // Para dibujar el marcador actual
    private void marcadorPosicionActual() {
        // Borramos el arcador actual si está puesto
        if (marcadorActual != null) {
            marcadorActual.remove();
        }
        // añadimos el marcador actual
        marcadorActual = mMap.addMarker(new MarkerOptions()
                // Posición
                .position(posActual)
                // Título
                .title("Mi Localización")
                // Subtitulo
                .snippet("Localización actual")
                // Color o tipo d icono
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );
    }
    // solicitamos los permisos para leer de algo
    public void solicitarPermisos() {
        // Si tenemos los permisos
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Activamos el botón de lalocalización
            permisos = true;
        } else {
            // Si no
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
    }

    // Para los permisos, implementamos este método
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permisos = false;
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permisos = true;
            } else {
                Toast.makeText(getContext(), "Error de permisos", Toast.LENGTH_LONG).show();
            }
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permisos = true;
            } else {
                Toast.makeText(getContext(), "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }

    
    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private void handleNewLocation(Location location) {
        Log.d("Mapa", location.toString());
        miUltimaLocalizacion = location;
        posActual = new LatLng(miUltimaLocalizacion.getLatitude(),
                miUltimaLocalizacion.getLongitude());
        // Añadimos un marcador especial para poder operar con esto
        marcadorPosicionActual();

        situarCamaraMapa();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        } else {
            handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Mapa", "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }
}
