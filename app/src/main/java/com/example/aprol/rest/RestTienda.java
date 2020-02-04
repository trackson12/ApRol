package com.example.aprol.rest;



import com.example.aprol.objeto.Cliente;
import com.example.aprol.objeto.Juego;
import com.example.aprol.objeto.Tienda;

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

public interface RestTienda {
    // Obtener todos
    // GET: http://localhost:8080/productos
    @GET("tienda/")
    Call<List<Tienda>> findAll();

    // Obtener uno producto por ID
    // GET: http://localhost:8080/productos/{id}
    /*@GET("registro/{id}")
    Call<Tienda> findById(@Path("id") Long id);
*/
    @GET("tienda/{direccion}")
    Call<Tienda> findById(@Path("usuario") String usuario);
    // Crear un producto
    //POST: http://localhost:8080/productos
    @POST("tienda/")
    Call<Tienda> create(@Body Cliente producto);

    // Elimina un productp
    // DELETE: http://localhost:8080/productos/{id}
    @DELETE("tienda/{direccion}")
    Call<Tienda> delete(@Path("id") Long id);

    // Actualiza un producto
    // PUT: http://localhost:8080/productos/{id}
    @PUT("tienda/{direccion}")
    Call<Tienda> update(@Path("id") Long id, @Body Cliente producto);

    //Comprueba que el cliente este registrado
    @FormUrlEncoded
    @POST("juego")
    Call<Tienda> usuario_registrado(
            //@Field("id") Long id
            @Field("nombre") String usuario
            //@Field("pass")String pass
    );
}
