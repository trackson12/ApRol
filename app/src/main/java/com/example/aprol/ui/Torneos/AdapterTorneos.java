package com.example.aprol.ui.Torneos;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aprol.R;
import com.example.aprol.ui.Juegos.Fragment_Detalles_Juegos;
import com.example.aprol.ui.Juegos.Juegos;
import com.example.aprol.ui.Juegos.imagenRedonda;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class AdapterTorneos extends RecyclerView.Adapter<AdapterTorneos.ViewHolder> {
    // Array list que le pasamos
    private ArrayList<Torneos> torneos;

    private Context context;
    // Cponstructor
    public AdapterTorneos(ArrayList<Torneos> torneos, Context context) {
        this.torneos = torneos;
        this.context = context;
    }


    // Le pasamos el ViewHolder y el layout que va a usar
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.lista_torneos,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    // Publicamos el evento en la posición del holder y lo programamos
    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {


    }


    @Override
    public int getItemCount() {
        return torneos.size();
    }
    // Aqui está el holder y lo que va a manejar, es decir la vista para interactuar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // componentes
        TextView titulo,fecha;
        RelativeLayout  parentLayout;

        public ViewHolder( View itemView) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.nombreTorneo);
            fecha=(TextView) itemView.findViewById(R.id.fechaTorneo);
            parentLayout=itemView.findViewById(R.id.parent_layout);
        }
    }
    //para ver la torneo
    public void verTorneo(int position, Torneos torneos){
        Intent intent = new Intent(context, Fragment_Detalle_Torneos.class);
        Bundle pasarNoticia=new Bundle();
        pasarNoticia.putSerializable("torneo", (Serializable) torneos);
        intent.putExtras(pasarNoticia);
        context.startActivity(intent);
    }



}
