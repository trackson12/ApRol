package com.example.aprol.objeto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tienda {
    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("direccion")
    @Expose
    private String direccion;

    @SerializedName("h_lunes")
    @Expose
    private String h_lunes;

    @SerializedName("h_martes")
    @Expose
    private String h_martes;

    @SerializedName("h_miercoles")
    @Expose
    private String h_miercoles;

    @SerializedName("h_jueves")
    @Expose
    private String h_jueves;

    @SerializedName("h_viernes")
    @Expose
    private String h_viernes;

    @SerializedName("h_sabado")
    @Expose
    private String h_sabado;

    @SerializedName("h_domingo")
    @Expose
    private String h_domingo;

    @SerializedName("puntuacion")
    @Expose
    private int puntuacion;

    @SerializedName("longitud")
    @Expose
    private Double longitud;

    @SerializedName("latitud")
    @Expose
    private Double latitud;


    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getH_lunes() {
        return h_lunes;
    }

    public String getH_martes() {
        return h_martes;
    }

    public String getH_miercoles() {
        return h_miercoles;
    }

    public String getH_jueves() {
        return h_jueves;
    }

    public String getH_viernes() {
        return h_viernes;
    }

    public String getH_sabado() {
        return h_sabado;
    }

    public String getH_domingo() {
        return h_domingo;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Double getLatitud() {
        return latitud;
    }
}
