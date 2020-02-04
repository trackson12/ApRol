package com.example.aprol.rest;

public class APIUtils {
    // IP del servidor
    private static final String server = "192.168.1.20";
    // Puerto del microservicio
    private static final String port = "8080";
    //Servicio, si usamos otro punto de partida, pero lo hemos definido en el ProuctoRest
    private static final String servicio = "registro";
    // IP del servicio
    public static final String API_URL = "http://"+server+":"+port+"/";

    private APIUtils() {
    }

    // Constructor del servicio con los elementos de la interfaz
    public static RestCliente getService() {
        return RetrofitClient.getClient(API_URL).create(RestCliente.class);
    }

    public static RestJuego getServiceJuego() {
        return RetrofitClient.getJuego(API_URL).create(RestJuego.class);
    }

    public static RestTienda getServiceTienda() {
        return  RetrofitClient.getTienda(API_URL).create(RestTienda.class);
    }



}