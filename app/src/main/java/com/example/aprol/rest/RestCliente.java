package com.example.aprol.rest;



import com.example.aprol.objeto.Cliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestCliente {
    // Obtener todos
    // GET: http://localhost:8080/productos
    @GET("registro/")
    Call<List<Cliente>> findAll();

    // Obtener uno producto por ID
    // GET: http://localhost:8080/productos/{id}
    @GET("registro/{id}")
    Call<Cliente> findById(@Path("id") Long id);

    // Crear un producto
    //POST: http://localhost:8080/productos
    @POST("registro/")
    Call<Cliente> create(@Body Cliente producto);

    // Elimina un productp
    // DELETE: http://localhost:8080/productos/{id}
    @DELETE("registro/{id}")
    Call<Cliente> delete(@Path("id") Long id);

    // Actualiza un producto
    // PUT: http://localhost:8080/productos/{id}
    @PUT("registro/{id}")
    Call<Cliente> update(@Path("id") Long id, @Body Cliente producto);

}
