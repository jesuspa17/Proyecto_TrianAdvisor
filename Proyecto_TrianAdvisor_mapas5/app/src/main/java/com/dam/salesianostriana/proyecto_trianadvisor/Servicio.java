package com.dam.salesianostriana.proyecto_trianadvisor;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * @author Jesús Pallares on 14/12/2015.
 */
public class Servicio {

    final public static String URL_BASE = "https://api.parse.com";

    final public static String APLICATION_ID = "Usqpw9Za6WcJEWQGtjra1JqNWimf1SMPsVwQ2yWy";
    final public static String API_KEY = "4sZHPDkPA4NlZAAIVBVzGXIpLk59IpMwKHX4TTqR";

    /**
     * Servirá para inicializar el servicio que nos permitirá conectarnos a la API.
     * @return el serivicio creado.
     */
    public static TrianAdvisorApi instanciarServicio() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("X-Parse-Application-Id", APLICATION_ID)
                        .addHeader("X-Parse-REST-API-Key",API_KEY)
                        .build();

                return chain.proceed(newRequest);
            }
        };
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        TrianAdvisorApi service = retrofit.create(TrianAdvisorApi.class);

        return service;
    }

}
