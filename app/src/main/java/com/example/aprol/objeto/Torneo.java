package com.example.aprol.objeto;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Torneo {

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("direc")
    @Expose
    private String direc;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDirec() {
        return direc;
    }

    public void setDirec(String direc) {
        this.direc = direc;
    }
}
