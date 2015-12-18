package com.dam.salesianostriana.proyecto_trianadvisor;


import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.NuevaValoracion;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.NuevoComentario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.comentarios.Comentario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
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
    Call<Usuario> obtenerMisDatos(@Header("X-Parse-Session-Token") String header);

    /**
     * Cerrar sesion, elimina el sessionToken
     */
    @POST("/1/logout")
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
    @GET("/1/classes/valoracion?")
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


    /**
     * Almacenar un comentario.
     * @param resultComentario
     */
    @Headers("{Content-Type: application/json}")
    @POST("/1/classes/comentario")
    Call<NuevoComentario> almacenarComentario(@Body NuevoComentario resultComentario);


    /**
     * Almacenar una valoracion.
     * @param resultValoracion
     */
    @Headers("{Content-Type: application/json}")
    @POST("/1/classes/valoracion")
    Call<NuevaValoracion> almacenarComentario(@Body NuevaValoracion resultValoracion);

    /**
     * Obtiene todas los comentarios de un sitio.
     * @param json
     * @return
     */
    @GET("/1/classes/comentario/?&include=usuario")
    Call<Comentario> cargarComentariosUnSitio(@Query(value = "where", encoded = true) String json);


    /**
     * Obtiene todas las valoraciones de un sitio.
     * @param json
     * @return
     */
    @GET("/1/classes/valoracion/?")
    Call<Valoracion> cargarValoracionesUnSitio(@Query(value = "where", encoded = true) String json);


}
