package com.example.aprol;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.aprol.objeto.Juego;
import com.example.aprol.objeto.Tienda;
import com.example.aprol.rest.APIUtils;
import com.example.aprol.rest.RestTienda;
import com.example.aprol.ui.Juegos.AdapterJuegos;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RestTienda tiendaRest;
    private Tienda comercio;
    List<Tienda> list = new ArrayList<Tienda>();
    LatLng tiendas = new LatLng(38.690265, -4.106939);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);





        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if(isNetworkAvailable()) {
            tiendaRest =  APIUtils.getServiceTienda();
            obtenerTiendas();

        }else{
            Toast.makeText(this, "Es necesaria una conexión a internet", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);




        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void obtenerTiendas() {

        // Creamos la tarea que llamará al servicio rest y la encolamos
        Call<List<Tienda>> call = tiendaRest.findAll();
        call.enqueue(new Callback<List<Tienda>>() {
            @Override
            public void onResponse(Call<List<Tienda>> call, Response<List<Tienda>> response) {
                if(response.isSuccessful()){

                    list = response.body();

                    for (int i = 0 ; i<list.size() ; i++ ){
                        comercio = list.get(i);

                        mMap.addMarker(new MarkerOptions().position(tiendas).title("Marker in Sydney"));
                    }






                }
            }

            @Override
            public void onFailure(Call<List<Tienda>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();



    }
}
