package com.dam.salesianostriana.retrofit_trianadvisor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dam.salesianostriana.retrofit_trianadvisor.pojos_RetroFit.sitios.Sitio;
import com.dam.salesianostriana.retrofit_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;
import com.google.gson.Gson;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    TrianAdvisorApi servicio;

    final String URL_BASE = "https://api.parse.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(URL_BASE)
                .build();

        new GetSitiosTask().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Obtendrá la lista de todos los sitios
    private class GetSitiosTask extends AsyncTask<Void, Void, Sitio>{

        @Override
        protected Sitio doInBackground(Void... params) {

            servicio = retrofit.create(TrianAdvisorApi.class);
            Call<Sitio> result = instanciarServicio().cargarSitios();

            Response<Sitio> sitio = null;

            try {
                sitio = result.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert sitio != null;
            return sitio.body();
        }

        @Override
        protected void onPostExecute(Sitio sitio) {
            super.onPostExecute(sitio);

            for(int i = 0;i<sitio.getResults().size();i++){
                if(sitio.getResults().get(i).getNombre()!=null){

                    Log.i("SITIO_ID","SITIO_ID: " + sitio.getResults().get(i).getObjectId());
                    Log.i("SITIO_NOMBRE","SITIO_NOMBRE: " + sitio.getResults().get(i).getNombre());
                    Log.i("SITIO_DESCRIPCION", "SITIO_DESCRIPCION: " + sitio.getResults().get(i).getFoto().getUrl());

                    new GetValoracionesTask().execute(sitio.getResults().get(i).getObjectId());

                }
                Log.i("------------------------------------","------------------------------------------------");
            }
        }
    }


    //peticion con enqueue

    private void obtenerSitios(){

        Call<Sitio> sitio = instanciarServicio().cargarSitios();

        sitio.enqueue(new Callback<Sitio>() {
            @Override
            public void onResponse(Response<Sitio> response, Retrofit retrofit) {
                Sitio sitio = response.body();

                for(int i = 0;i<sitio.getResults().size();i++){
                    if(sitio.getResults().get(i).getNombre()!=null){

                        Log.i("SITIO_ID","SITIO_ID: " + sitio.getResults().get(i).getObjectId());
                        Log.i("SITIO_NOMBRE","SITIO_NOMBRE: " + sitio.getResults().get(i).getNombre());
                        Log.i("SITIO_DESCRIPCION", "SITIO_DESCRIPCION: " + sitio.getResults().get(i).getFoto().getUrl());

                        new GetValoracionesTask().execute(sitio.getResults().get(i).getObjectId());
                    }
                    Log.i("------------------------------------","------------------------------------------------");
                }
            }
            @Override
            public void onFailure(Throwable t) {
            }
        });

    }


    //Obtiene la lista de valoraciones para un sitio.
    private class GetValoracionesTask extends AsyncTask<String, Void, Valoracion>{

        @Override
        protected Valoracion doInBackground(String... params) {

            Call<Valoracion> result = instanciarServicio().cargarValoraciones(params[0]);
            Response<Valoracion> response = null;

            try {
                response = result.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert response != null;
            return response.body();
        }

        @Override
        protected void onPostExecute(Valoracion valoracion) {
            super.onPostExecute(valoracion);
            for(int i = 0;i<valoracion.getResults().size();i++){
                Log.i("SITIO_VALORACIONES","PUNTUACIÓN: "+valoracion.getResults().get(i).getValoracion());
            }
        }
    }
    //AÑADIR X-Parse-Session-Tokenr: nKPWqsgRyqL4xFd0ZesITMgrz


    private TrianAdvisorApi instanciarServicio() {

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
