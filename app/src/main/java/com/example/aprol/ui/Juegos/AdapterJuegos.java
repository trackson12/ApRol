package com.example.aprol.ui.Juegos;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aprol.R;
import com.example.aprol.objeto.Juego;

import java.io.Serializable;
import java.util.ArrayList;

public class AdapterJuegos extends RecyclerView.Adapter<AdapterJuegos.ViewHolder> implements ListAdapter {
    // Array list que le pasamos
    //private ArrayList<Juegos> juegos;
    private ArrayList<Juego> juego;
    private Context context;
    // Cponstructor
    /**
    public AdapterJuegos(ArrayList<Juegos> juegos, Context context) {
        this.juegos = juegos;
        this.context = context;
    }
**/
    public AdapterJuegos(ArrayList<Juego> juego, Context context) {
        this.juego = juego;
        this.context = context;
    }

    // Le pasamos el ViewHolder y el layout que va a usar
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.lista_games,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    // Publicamos el evento en la posición del holder y lo programamos
    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        final Juego actual=juego.get(position);
        holder.titulo.setText(actual.getNombre());
       // Picasso.with(context).load(actual.getFoto()).transform(new imagenRedonda()).into(holder.imagen);
        holder.parentLayout.setOnClickListener(new  View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Fragment_Detalles_Juegos.class);
                Bundle b=new Bundle();
                final Juego jue=juego.get(position);
                b.putSerializable("juego", (Serializable) jue);
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return juego.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    // Aqui está el holder y lo que va a manejar, es decir la vista para interactuar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // componentes
        TextView titulo,fecha;
        ImageView imagen;
        RelativeLayout  parentLayout;

        public ViewHolder( View itemView) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.tituloJuego);
            fecha=(TextView) itemView.findViewById(R.id.N_jug);
            imagen=(ImageView) itemView.findViewById(R.id.imgJuego);
            parentLayout=itemView.findViewById(R.id.parent_layout);
        }
    }
    //para ver la noticia
    public void verJuego(int position, Juegos juegos){
        Intent intent = new Intent(context, Fragment_Detalles_Juegos.class);
        Bundle pasarNoticia=new Bundle();
        pasarNoticia.putSerializable("juego", juegos);
        intent.putExtras(pasarNoticia);
        context.startActivity(intent);
    }



}
