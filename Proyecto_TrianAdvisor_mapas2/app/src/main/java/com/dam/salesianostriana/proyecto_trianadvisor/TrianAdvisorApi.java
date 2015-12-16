package com.dam.salesianostriana.proyecto_trianadvisor;


import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.comentarios.Comentario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Jesús Pallares on 14/12/2015.
 */
public interface TrianAdvisorApi {

    /**
     * Obtiene todos los sitios
     */
    @GET("/1/classes/sitio")
    Call<Sitio> cargarSitios();

    /**
     * Obtiene los datos de un usuario.
     */
    @GET("/1/login")
    Call<Usuario> obtenerUsuario(@Query("username") String user, @Query("password") String password);


    /**
     * Obtiene mis datos a partir del sessionToken
     */
    @GET("/1/users/me")
    Call<Usuario> obtenerMisDatos(@Header("X-Parse-Session-Token") String header, @Query("sessionToken") String session);

    /**
     * Cerrar sesion, elimina el sessionToken
     */

    @GET("/1/logout")
    Call<Usuario> cerrarSesion(@Header("X-Parse-Session-Token") String header, @Query("sessionToken") String session);

    /**
     * Obtiene los datos de un sitio dado el id.
     * @param object_id
     * @return
     */
    @GET("/1/classes/sitio/{objectId}")
    Call<ResultSitio> cargarUnSitio(@Path("objectId") String object_id);

    /**
     * Obtiene la valoración de un sitio
     * @param objectId
     * @return
     */
    @GET("/1/classes/valoracion")
    Call<Valoracion> cargarValoraciones(@Query("objectId") String objectId);

    /**
     * Obtiene los comentarios sobre un sitio.
     */
    @GET("/1/classes/comentario?&include=usuario")
    Call<Comentario> cargarComentarios(@Query("objectId") String objectId);


    /**
     * Obtiene los 10 sitiso mas cercanos.
     */

    @GET("/1/classes/sitio?limit=10")
    Call<Sitio> obtenerSitiosCercanos(@Query("latitude") double latitud,@Query("longitude") double longitud);


}
