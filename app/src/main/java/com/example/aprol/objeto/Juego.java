package com.example.aprol.objeto;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Juego implements Serializable {

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getN_jugadores() {
        return n_jugadores;
    }

    public void setN_jugadores(String n_jugadores) {
        this.n_jugadores = n_jugadores;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
