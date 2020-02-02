package com.example.aprol.rest;



import com.example.aprol.objeto.Cliente;
import com.example.aprol.objeto.Tienda;
import com.example.aprol.objeto.Torneo;

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

public interface RestTorneo {
    // Obtener todos
    // GET: http://localhost:8080/productos
    @GET("torneo/")
    Call<List<Torneo>> findAll();

    // Obtener uno producto por ID
    // GET: http://localhost:8080/productos/{id}
    /*@GET("registro/{id}")
    Call<Tienda> findById(@Path("id") Long id);
*/
    @GET("torneo/{direccion}")
    Call<Torneo> findById(@Path("usuario") String usuario);
    // Crear un producto
    //POST: http://localhost:8080/productos
    @POST("torneo/")
    Call<Torneo> create(@Body Cliente producto);

    // Elimina un productp
    // DELETE: http://localhost:8080/productos/{id}
    @DELETE("torneo/{direccion}")
    Call<Torneo> delete(@Path("id") Long id);

    // Actualiza un producto
    // PUT: http://localhost:8080/productos/{id}
    @PUT("torneo/{direccion}")
    Call<Torneo> update(@Path("id") Long id, @Body Cliente producto);

    //Comprueba que el cliente este registrado
    @FormUrlEncoded
    @POST("juego")
    Call<Torneo> usuario_registrado(
            //@Field("id") Long id
            @Field("nombre") String usuario
            //@Field("pass")String pass
    );
}
