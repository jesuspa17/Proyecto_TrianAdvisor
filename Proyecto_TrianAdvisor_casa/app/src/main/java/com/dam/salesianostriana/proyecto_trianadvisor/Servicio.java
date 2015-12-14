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

    public static TrianAdvisorApi instanciarServicio() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {

                Request newRequest = chain.request().newBuilder()
                        .addHeader("X-Parse-Application-Id", "Usqpw9Za6WcJEWQGtjra1JqNWimf1SMPsVwQ2yWy")
                        .addHeader("X-Parse-REST-API-Key","4sZHPDkPA4NlZAAIVBVzGXIpLk59IpMwKHX4TTqR")
                        .build();

                return chain.proceed(newRequest);
            }
        };
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.parse.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        TrianAdvisorApi service = retrofit.create(TrianAdvisorApi.class);

        return service;
    }

}
