package com.example.aprol.objeto;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Juego {

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("foto")
    @Expose
    private Bitmap foto;

    @SerializedName("n_jugadores")
    @Expose
    private String n_jugadores;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

}
