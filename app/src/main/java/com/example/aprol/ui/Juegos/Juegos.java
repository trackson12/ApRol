
package com.example.aprol.ui.Juegos;


import java.io.Serializable;

public class Juegos implements Serializable {

    private String nombre;
    private int n_jugadores;
    private int id;

    public Juegos() {
    }

    public Juegos(String nombre, int n_jugadores,int id) {
        this.nombre = nombre;
        this.n_jugadores = n_jugadores;
        this.id=id;
    }

    public int getId() {
        return id;
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