package com.example.aprol.ui.Juegos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aprol.R;
import com.example.aprol.objeto.Juego;
import com.example.aprol.rest.APIUtils;
import com.example.aprol.rest.RestJuego;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Juegos extends Fragment {
    private View root;
    private ArrayList<Juego> listar;
    private Context context;
    private ConstraintLayout constr;
    private FloatingActionButton fabAñadir;
    private RecyclerView recyclerView;
    private LinearLayout linla;
    private RestJuego juegoRest;
    private ListView listView;
    private Juego juego;
    private AdapterJuegos adapter;
    private List<Juego> list = new ArrayList<Juego>();
    private FragmentManager fm;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_lista_juegos, container, false);
        constr = (ConstraintLayout) root.findViewById(R.id.LinJuegos);
        recyclerView = (RecyclerView)  root.findViewById(R.id.rvFragmentJuego_recycler);
        fabAñadir=(FloatingActionButton) root.findViewById(R.id.fabAñadirJuego);


        fabAñadir.setOnClickListener(v -> {
            Fragment_Detalles_Juegos dj= Fragment_Detalles_Juegos.newInstance("crear");
            FragmentTransaction transaction = getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,dj);
            transaction.addToBackStack(null);
            transaction.commit();
            /*DetallesJuegos de= DetallesJuegos.newInstance("crear");
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, de);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/
        });


        //setContentView(R.layout.fragment_lista_juegos);

        if(isNetworkAvailable()) {
            juegoRest =  APIUtils.getServiceJuego();
            listarProductos();

        }else{
            Toast.makeText(getContext(), "Es necesaria una conexión a internet", Toast.LENGTH_SHORT).show();
        }


        return root;
    }




    private boolean isNetworkAvailable() {
        /*
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

         */
        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
    private void listarProductos() {

        // Creamos la tarea que llamará al servicio rest y la encolamos
        Call<List<Juego>> call = juegoRest.findAll();
        call.enqueue(new Callback<List<Juego>>() {
            @Override
            public void onResponse(Call<List<Juego>> call, Response<List<Juego>> response) {
                if(response.isSuccessful()){
                    // Si tienes exito nos quedamos con el ResponseBody, listado en JSON
                    // Nos hace el pasrser automáticamente
                    list = response.body();
                   // adapter = new AdapterJuegos(list, getContext(),getActivity(),getFragmentManager());

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
                    recyclerView.setAdapter(new AdapterJuegos((ArrayList<Juego>) list,getContext(),getFragmentManager()));

                    //listView.setAdapter( new AdapterJuegos((ArrayList<Juego>) list,getContext()));
                    //listView.setAdapter( new AdapterJuegos((ArrayList<Juego>) list,getContext()));
                }
            }

            @Override
            public void onFailure(Call<List<Juego>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }
}



