package com.example.aprol.objeto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Juego {

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("n_jug")
    @Expose
    private String n_jug;

}
