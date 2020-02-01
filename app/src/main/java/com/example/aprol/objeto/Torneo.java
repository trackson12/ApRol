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

    @SerializedName("foto")
    @Expose
    private Bitmap foto;

    @SerializedName("direc")
    @Expose
    private String direc;
}
