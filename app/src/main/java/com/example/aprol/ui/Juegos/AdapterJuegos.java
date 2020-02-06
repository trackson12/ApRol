package com.example.aprol.ui.Juegos;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aprol.R;
import com.example.aprol.objeto.Juego;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AdapterJuegos extends RecyclerView.Adapter<AdapterJuegos.ViewHolder> implements ListAdapter {
    // Array list que le pasamos
    //private ArrayList<Juegos> juegos;
    private ArrayList<Juego> lista;
    private Context context;
    private Juego juego;
    private FragmentManager fm;
    private AdapterJuegos adapter;
    private RecyclerView recy;
    // Cponstructor
    /**
    public AdapterJuegos(ArrayList<Juegos> juegos, Context context) {
        this.juegos = juegos;
        this.context = context;
    }
**/
    public AdapterJuegos(ArrayList<Juego> lista, Context context,FragmentManager fm) {
        this.lista = lista;
        this.context = context;
        this.fm = fm;
    }


    // Le pasamos el ViewHolder y el layout que va a usar
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.lista_games,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    // Publicamos el evento en la posición del holder y lo programamos
    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        final Juego actual=lista.get(position);
        holder.titulo.setText(actual.getNombre());
        holder.njug.setText(actual.getN_jugadores());
       // Picasso.with(context).load(actual.getFoto()).transform(new imagenRedonda()).into(holder.imagen);
        holder.parentLayout.setOnClickListener(v -> {


            juego=(Juego)lista.get(position);
            Fragment_Detalles_Juegos dj= Fragment_Detalles_Juegos.newInstance("ver",juego);
            FragmentTransaction transaction = fm.beginTransaction().replace(R.id.nav_host_fragment,dj);
            transaction.addToBackStack(null);
            transaction.commit();

            /*
            Intent intent = new Intent(context, Fragment_Detalles_Juegos.class);
            Bundle b=new Bundle();
            final Juego jue=juego.get(position);
            b.putSerializable("juego", (Serializable) jue);
            intent.putExtras(b);
            context.startActivity(intent);*/
        });

    }
    protected void onPostExecute (Integer re) {
        Log.e("Funciona","bien");

        //progressBar.setVisibility(View.GONE);
        //se carga en el adapadator la lista rellena de juegos y se le pasa el contexto del fragment
        /*adapter = new AdapterJuegos(lista,);
        recy.setHasFixedSize(true);
        // se presenta en formato lineal
        recy.setLayoutManager(new LinearLayoutManager(context));
        //se le aplica el adaptador al recyclerView
        recy.setAdapter(adapter);*/

        // swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public int getItemCount() {
        return lista.size();
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
        TextView titulo,njug;
        ImageView imagen;
        RelativeLayout  parentLayout;

        public ViewHolder( View itemView) {
            super(itemView);
            this.titulo=(TextView) itemView.findViewById(R.id.tituloJuego);
            this.njug=(TextView) itemView.findViewById(R.id.N_jug);
            this.imagen=(ImageView) itemView.findViewById(R.id.imgJuego);
            parentLayout=itemView.findViewById(R.id.parent_layout);
        }
    }
    //para ver la noticia
    public void verJuego(int position, Juego juego){
        Intent intent = new Intent(context, Fragment_Detalles_Juegos.class);
        Bundle pasarNoticia=new Bundle();
        pasarNoticia.putSerializable("juego", (Serializable) juego);
        intent.putExtras(pasarNoticia);
        context.startActivity(intent);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
