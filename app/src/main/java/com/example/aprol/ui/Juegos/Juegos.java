
package com.example.aprol.ui.Juegos;


import java.io.Serializable;

public class Juegos implements Serializable {

    private String nombre;
    private String imagen;
    private int n_jugadores;
    private int id;
    private String descripcion;

    public Juegos() {
    }

    public Juegos(String nombre,String imagen, int n_jugadores,int id,String descripcion) {
        this.nombre = nombre;
        this.imagen=imagen;
        this.n_jugadores = n_jugadores;
        this.id=id;
        this.descripcion=descripcion;
    }

    public int getId() {
        return id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }

    public int getn_jugadores() {
        return n_jugadores;
    }

    public void setn_jugadores(int n_jugadores) {
        this.n_jugadores = n_jugadores;
    }
}