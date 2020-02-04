package com.example.aprol.objeto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cliente {
    // Indicamos los campos
    // ID, columna id de la tabla productos. Indicamos como se llamara en el JSON
    @SerializedName("id")
    @Expose
    private int id;

    // nombre, columna nombre,
    @SerializedName("correo")
    @Expose
    private String correo;

    @SerializedName("usuario")
    @Expose
    private String usuario;

    @SerializedName("pass")
    @Expose
    private String pass;

    public Cliente() {
    }

    public Cliente(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
    }

    public Cliente(String usuario, String correo, String pass) {
        this.usuario = usuario;
        this.correo = correo;
        this.pass = pass;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return pass;
    }

    public void setPassword(String password) {
        this.pass = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Producto[id=%d, nombre='%s']",
                id);
    }
}
