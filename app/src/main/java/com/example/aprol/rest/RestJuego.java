package com.example.aprol.rest;



import com.example.aprol.objeto.Cliente;
import com.example.aprol.objeto.Juego;

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

public interface RestJuego {
    // Obtener todos
    // GET: http://localhost:8080/productos
    @GET("juego/")
    Call<List<Juego>> findAll();

    // Obtener uno producto por ID
    // GET: http://localhost:8080/productos/{id}
    /*@GET("registro/{id}")
    Call<Cliente> findById(@Path("id") Long id);
*/
    @GET("juego/{nombre}")
    Call<Juego> findById(@Path("nombre") String nombre);
    // Crear un producto
    //POST: http://localhost:8080/productos
    @POST("juego/")
    Call<Juego> create(@Body Juego juego);

    // Elimina un productp
    // DELETE: http://localhost:8080/productos/{id}
    @DELETE("juego/{nombre}")
    Call<Juego> delete(@Path("id") Long id);

    // Actualiza un producto
    // PUT: http://localhost:8080/productos/{id}
    @PUT("juego/{nombre}")
    Call<Juego> update(@Path("id") Long id, @Body Cliente producto);

    //Comprueba que el cliente este registrado
    @FormUrlEncoded
    @POST("juego")
    Call<Juego> usuario_registrado(
            //@Field("id") Long id
            @Field("nombre") String usuario
            //@Field("pass")String pass
    );
}
