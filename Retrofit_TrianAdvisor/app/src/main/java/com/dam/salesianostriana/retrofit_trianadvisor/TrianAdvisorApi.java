package com.dam.salesianostriana.retrofit_trianadvisor;

import com.dam.salesianostriana.retrofit_trianadvisor.pojos_RetroFit.sitios.Sitio;
import com.dam.salesianostriana.retrofit_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by Jesús Pallares on 14/12/2015.
 */
public interface TrianAdvisorApi {

    @Headers({
            "X-Parse-Application-Id: Usqpw9Za6WcJEWQGtjra1JqNWimf1SMPsVwQ2yWy",
            "X-Parse-REST-API-Key: 4sZHPDkPA4NlZAAIVBVzGXIpLk59IpMwKHX4TTqR"
    })

    @GET("/1/classes/sitio")
    Call<Sitio> cargarSitios();

    @Headers("{Content-Type: application/json}")
    @GET("/1/classes/valoracion")
    Call<Valoracion> cargarValoraciones(@Query("objectId") String objectId);

}
