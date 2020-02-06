package com.example.aprol.ui.Torneos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aprol.R;
import com.example.aprol.objeto.Juego;
import com.example.aprol.objeto.Torneo;
import com.example.aprol.rest.APIUtils;
import com.example.aprol.rest.RestTorneo;
import com.example.aprol.ui.Juegos.AdapterJuegos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Fragment_Torneos extends Fragment {
    private View root;
    private RecyclerView torneoView;
    private FloatingActionButton fabVoz;
    private ConstraintLayout constr;
    private TextView mEntradaTexto;
    private RestTorneo torneoRest;
    private List<Torneo> list = new ArrayList<Torneo>();
    private ArrayList<Torneo> ordenar = new ArrayList<Torneo>() ;
    private static final int REQ_CODE_SPEECH_INPUT=10;
    private String secuencia = "";
    private static final int NADA = 10;
    private static final int TIPO_ASC = 13;
    private static final int TIPO_DESC = 14;
    private int tipoFiltro = NADA;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_lista_torneos, container, false);
        fabVoz=(FloatingActionButton)root.findViewById(R.id.fabVozTorneo);
        constr = (ConstraintLayout) root.findViewById(R.id.LinTorneos);
        torneoView = (RecyclerView) root.findViewById(R.id.tournament_list);

        fabVoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iniciarEntradaVoz();

            }
        });




        if(isNetworkAvailable()) {
            torneoRest =  APIUtils.getServiceTorneo();
            listarProductos();

        }else{
            Toast.makeText(getContext(), "Es necesaria una conexión a internet", Toast.LENGTH_SHORT).show();
        }



        return root;
    }

    private void iniciarEntradaVoz() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //reconoce en el idioma del telefono
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "¿Cómo quieres ordenar la lista?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (Exception e) {
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
       super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.getActivity().RESULT_CANCELED) {
            return;
        }

        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> voz = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                // Analizamos los que nos puede llegar
                    secuencia = "";
                int tipoFiltro;

                for (String v : voz) {
                    secuencia += " " + v;
                }

                if (secuencia != null) {
                    analizarFiltroVoz(secuencia);
                    ordenarTorneos();

                    torneoView.setHasFixedSize(true);
                    torneoView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
                    torneoView.setAdapter(new AdapterTorneos((ArrayList<Torneo>) ordenar,getContext(),getFragmentManager()));
                }
            }

        }

            }



    private void ordenarTorneos() {
        switch (tipoFiltro) {
            case NADA:
            case TIPO_ASC:
                Collections.sort(this.ordenar, (Torneo l1, Torneo l2) -> l1.getNombre().compareTo(l2.getNombre()));
                break;

            case TIPO_DESC:
                Collections.sort(this.ordenar, (Torneo l1, Torneo l2) -> l2.getNombre().compareTo(l1.getNombre()));
                break;

            default:
                break;

        }
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
        Call<List<Torneo>> call = torneoRest.findAll();
        call.enqueue(new Callback<List<Torneo>>() {
            @Override
            public void onResponse(Call<List<Torneo>> call, Response<List<Torneo>> response) {
                if(response.isSuccessful()){
                    // Si tienes exito nos quedamos con el ResponseBody, listado en JSON
                    // Nos hace el pasrser automáticamente
                    list = response.body();
                    ordenar = (ArrayList<Torneo>) response.body();
                    // adapter = new AdapterJuegos(list, getContext(),getActivity(),getFragmentManager());

                    torneoView.setHasFixedSize(true);
                    torneoView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
                    torneoView.setAdapter(new AdapterTorneos((ArrayList<Torneo>) list,getContext(),getFragmentManager()));

                    //listView.setAdapter( new AdapterJuegos((ArrayList<Juego>) list,getContext()));
                    //listView.setAdapter( new AdapterJuegos((ArrayList<Juego>) list,getContext()));
                }
            }

            @Override
            public void onFailure(Call<List<Torneo>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }


    private void analizarFiltroVoz(String secuencia) {
        // Nombre
        if ((secuencia.contains("tipo")) &&
                ((secuencia.contains("ascendente") || secuencia.contains("normal")))) {
            tipoFiltro = TIPO_ASC;
        } else if ((secuencia.contains("tipo")) &&
                ((secuencia.contains("descendente") || secuencia.contains("inverso")))) {
            tipoFiltro = TIPO_DESC;

            // Lugar = nombre
        } else {
            tipoFiltro = NADA;
        }
    }



}
