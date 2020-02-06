package com.example.aprol.rest;



import com.example.aprol.objeto.Cliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestCliente {
    // Obtener todos
    @GET("registro/")
    Call<List<Cliente>> findAll();

    // Obtener un cliente por nombre
    @GET("registro/{usuario}")
    Call<Cliente> findById(@Path("usuario") String usuario);

    // Crear un registro
    @POST("registro/")
    Call<Cliente> create(@Body Cliente registro);

    // Elimina un cliente
    @DELETE("registro/{id}")
    Call<Cliente> delete(@Path("id") Long id);

    // Actualiza un cliente
    @PUT("registro/{usuario}")
    Call<Cliente> update(@Path("usuario") String usuario, @Body Cliente cliente);

    //Comprueba que el cliente este registrado
    @FormUrlEncoded
    @POST("registro")
    Call<Cliente> usuario_registrado(
            //@Field("id") Long id
            @Field("usuario") String usuario
            //@Field("pass")String pass
    );
}
