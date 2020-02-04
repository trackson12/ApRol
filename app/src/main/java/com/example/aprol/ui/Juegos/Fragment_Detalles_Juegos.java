package com.example.aprol.ui.Juegos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aprol.R;
import com.example.aprol.objeto.Juego;
import com.example.aprol.rest.RestJuego;

public class Fragment_Detalles_Juegos extends Fragment {
    private View root;
    TextView tvNombreJuegosDetalle,tvNumeroJugadoresDetalle,tvDescripcionJuegosDetalle;
    ImageView ivFotoJuegosDetalle;
    RestJuego juegoRest;
    private Juego juego;

    public static Fragment_Detalles_Juegos newInstance(String tipo) {

        Bundle b = new Bundle();
        b.putString("tipo", tipo);

        Fragment_Detalles_Juegos f= new Fragment_Detalles_Juegos();
        f.setArguments(b);



        return f;
    }
    public static Fragment_Detalles_Juegos newInstance(String tipo, Juego j) {

        Bundle b = new Bundle();
        b.putString("tipo", tipo);
        b.putSerializable("Juego", j);

        Fragment_Detalles_Juegos f= new Fragment_Detalles_Juegos();
        f.setArguments(b);



        return f;
    }
    public View onCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container){
        root=inflater.inflate(R.layout.fragment_detalle_juego, container, false);
        tvNombreJuegosDetalle=(TextView)root.findViewById(R.id.tvNombreDetJuego);
        tvNumeroJugadoresDetalle=(TextView) root.findViewById(R.id.tvNjugDetJuego);
        tvDescripcionJuegosDetalle=(TextView) root.findViewById(R.id.tvDescripcionDetJuego);
        ivFotoJuegosDetalle=(ImageView)root.findViewById(R.id.ivImgDetJuego);


        Bundle b = getArguments();
         Juego juego = (Juego) b.getSerializable("juego");


        tvNombreJuegosDetalle.setText(juego.getNombre());
        tvNumeroJugadoresDetalle.setText(juego.getN_jugadores());
        tvDescripcionJuegosDetalle.setText(juego.getDescripcion());
        Log.e("mecagoentodojoder","pedo"+ juego.getNombre());
        //Picasso.with(this).load(juegos.getImagen()).into(ivFotoJuegosDetalle);

        return root;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        root=inflater.inflate(R.layout.fragment_detalle_juego, container, false);
        tvNombreJuegosDetalle=(TextView)root.findViewById(R.id.tvNombreDetJuego);
        tvNumeroJugadoresDetalle=(TextView) root.findViewById(R.id.tvNumeroDetJuego);
        tvDescripcionJuegosDetalle=(TextView) root.findViewById(R.id.tvDescripcionDetJuego);
        ivFotoJuegosDetalle=(ImageView)root.findViewById(R.id.ivImgDetJuego);


        Bundle b = getArguments();
        Juego juego = (Juego) b.getSerializable("Juego");
        if(juego ==null){
            Log.e("joder","aaaaa");
        }

        tvNombreJuegosDetalle.setText(juego.getNombre());
        tvNumeroJugadoresDetalle.setText(juego.getN_jugadores());
        tvDescripcionJuegosDetalle.setText(juego.getDescripcion());
        Log.e("mecagoentodojoder","pedo"+ juego.getNombre());
        //Picasso.with(this).load(juegos.getImagen()).into(ivFotoJuegosDetalle);




        return  root;
    }



}
