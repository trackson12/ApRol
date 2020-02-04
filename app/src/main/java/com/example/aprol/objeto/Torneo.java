package com.example.aprol.objeto;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Torneo {

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("direc")
    @Expose
    private String direc;

    @SerializedName("fecha")
    @Expose
    private String fecha;

}
