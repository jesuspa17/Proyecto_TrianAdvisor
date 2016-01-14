package com.dam.salesianostriana.proyecto_trianadvisor.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.adaptadores.SitioAdapter;
import com.dam.salesianostriana.proyecto_trianadvisor.greendao.SitioDao;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Foto;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.utilidades.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Response;


public class SitiosFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<ResultSitio> lista_sitos;

    SitioDao sitioDao;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public SitiosFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sitios, container, false);

        //Inicializo las caractersísticas del recyclerView
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_sitios);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        sitioDao = Utils.instanciarBD(getActivity()).getSitioDao();

        preferences = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        editor = preferences.edit();

        String fecha = preferences.getString("fecha_sitios",null);

        String JSON_BASE = "{\"updatedAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\""+fecha+"\"}}}";

        lista_sitos = new ArrayList<>();
        //Compruebo si tengo internet.
        if (Utils.comprobarInternet(getActivity())) {

            //Ejecuto el asyntasck que obtiene los datos de cada sitio.
            new ObtenerSitiosDesdeFecha().execute(Utils.encodearCadena(JSON_BASE));

        } else {

            List<com.dam.salesianostriana.proyecto_trianadvisor.greendao.Sitio> sitioDaos = sitioDao.queryBuilder().list();
            List<ResultSitio> lista = new ArrayList<>();

            for (int i = 0; i < sitioDaos.size(); i++) {

                String objectid = sitioDaos.get(i).getObjectId();
                String nombre = sitioDaos.get(i).getNombre();
                String direccion = sitioDaos.get(i).getDireccion();
                String categoria = sitioDaos.get(i).getCategoria();
                String foto = sitioDaos.get(i).getUrl_foto();

                lista.add(new ResultSitio(objectid, nombre, direccion, categoria, foto));

            }

            adapter = new SitioAdapter(lista);
            recyclerView.setAdapter(adapter);

        }


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    List<com.dam.salesianostriana.proyecto_trianadvisor.greendao.Sitio> lista_dao;


    private class ObtenerSitiosDesdeFecha extends AsyncTask<String, Void, Sitio>{

        @Override
        protected Sitio doInBackground(String... params) {

            if (params != null) {

                Call<Sitio> result = Servicio.instanciarServicio().obtenerSitosDesdeFecha(params[0]);
                Response<Sitio> sitio = null;

                try {
                    sitio = result.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert sitio != null;
                if (sitio.code() == 200 || sitio.code() == 201) {
                    return sitio.body();
                } else {
                    return null;
                }


            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Sitio sitio) {
            super.onPostExecute(sitio);

            List<ResultSitio> lista = new ArrayList<>();
            lista_dao = new ArrayList<>();

            if (sitio != null) {

                for (int i = 0; i < sitio.getResultSitioses().size(); i++) {
                    if (sitio.getResultSitioses().get(i).getNombre() != null) {

                        String objectid = sitio.getResultSitioses().get(i).getObjectId();
                        String nombre = sitio.getResultSitioses().get(i).getNombre();
                        String direccion = sitio.getResultSitioses().get(i).getDireccion();
                        String telefono = sitio.getResultSitioses().get(i).getTelefono();
                        String descripcion = sitio.getResultSitioses().get(i).getDescripcion();
                        String categoria = sitio.getResultSitioses().get(i).getCategoria();
                        String latitud = String.valueOf(sitio.getResultSitioses().get(i).getCoordenadas().getLatitude());
                        String longitud = String.valueOf(sitio.getResultSitioses().get(i).getCoordenadas().getLongitude());
                        String url_foto = sitio.getResultSitioses().get(i).getFoto().getUrl();
                        String updatedAt = sitio.getResultSitioses().get(i).getUpdatedAt();
                        Foto foto = sitio.getResultSitioses().get(i).getFoto();

                        Log.i("SITIO_ID", "SITIO_ID: " + sitio.getResultSitioses().get(i).getObjectId());
                        Log.i("SITIO_NOMBRE", "SITIO_NOMBRE: " + sitio.getResultSitioses().get(i).getNombre());
                        Log.i("SITIO_DESCRIPCION", "SITIO_DESCRIPCION: " + sitio.getResultSitioses().get(i).getFoto().getUrl());

                        lista.add(new ResultSitio(objectid, nombre, direccion, categoria, url_foto));
                        lista_dao.add(new com.dam.salesianostriana.proyecto_trianadvisor.greendao.Sitio(objectid, nombre, direccion, telefono, descripcion, categoria, latitud, longitud, url_foto, updatedAt));
                    }
                    Log.i("------------------", "---------------------");

                }

                //aquí es donde añado los sitios a la base de datos, para no hacerlo todo en el mismo for, ya que
                //me ha causado problemas de insercción, dejaba datos atrás.
                for (int i = 0; i < lista_dao.size(); i++) {
                    sitioDao.insertOrReplaceInTx(lista_dao.get(i));
                }

                List<ResultSitio> lista_final = new ArrayList<>();
                List<com.dam.salesianostriana.proyecto_trianadvisor.greendao.Sitio> lista_dao_final = sitioDao.queryBuilder().list();

                for(int i = 0;i<lista_dao_final.size();i++){

                    String objectid = lista_dao_final.get(i).getObjectId();
                    String nombre = lista_dao_final.get(i).getNombre();
                    String direccion  = lista_dao_final.get(i).getDireccion();
                    String categoria = lista_dao_final.get(i).getCategoria();
                    String url_foto = lista_dao_final.get(i).getUrl_foto();

                    lista_final.add(new ResultSitio(objectid, nombre, direccion, categoria, url_foto));

                }

                editor.putString("fecha_sitios", Utils.obtenerFechaSistema());
                editor.apply();

                adapter = new SitioAdapter(lista_final);
                recyclerView.setAdapter(adapter);


            }
        }
    }


    //AsyncTask que obtiene todos los datos de un sitio, y los pinta en el recycler.
    private class ObtenerSitiosTask extends AsyncTask<Void, Void, Sitio> {

        @Override
        protected Sitio doInBackground(Void... params) {
            if (params != null) {

                Call<Sitio> result = Servicio.instanciarServicio().cargarSitios();
                Response<Sitio> sitio = null;

                try {
                    sitio = result.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert sitio != null;
                if (sitio.code() == 200 || sitio.code() == 201) {
                    return sitio.body();
                } else {
                    return null;
                }


            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Sitio sitio) {
            super.onPostExecute(sitio);

            List<ResultSitio> lista = new ArrayList<>();
            lista_dao = new ArrayList<>();

            if (sitio != null) {

                for (int i = 0; i < sitio.getResultSitioses().size(); i++) {
                    if (sitio.getResultSitioses().get(i).getNombre() != null) {

                        String objectid = sitio.getResultSitioses().get(i).getObjectId();
                        String nombre = sitio.getResultSitioses().get(i).getNombre();
                        String direccion = sitio.getResultSitioses().get(i).getDireccion();
                        String telefono = sitio.getResultSitioses().get(i).getTelefono();
                        String descripcion = sitio.getResultSitioses().get(i).getDescripcion();
                        String categoria = sitio.getResultSitioses().get(i).getCategoria();
                        String latitud = String.valueOf(sitio.getResultSitioses().get(i).getCoordenadas().getLatitude());
                        String longitud = String.valueOf(sitio.getResultSitioses().get(i).getCoordenadas().getLongitude());
                        String url_foto = sitio.getResultSitioses().get(i).getFoto().getUrl();
                        String updatedAt = sitio.getResultSitioses().get(i).getUpdatedAt();
                        Foto foto = sitio.getResultSitioses().get(i).getFoto();

                        Log.i("SITIO_ID", "SITIO_ID: " + sitio.getResultSitioses().get(i).getObjectId());
                        Log.i("SITIO_NOMBRE", "SITIO_NOMBRE: " + sitio.getResultSitioses().get(i).getNombre());
                        Log.i("SITIO_DESCRIPCION", "SITIO_DESCRIPCION: " + sitio.getResultSitioses().get(i).getFoto().getUrl());

                        lista.add(new ResultSitio(objectid, nombre, direccion, categoria, url_foto));
                        lista_dao.add(new com.dam.salesianostriana.proyecto_trianadvisor.greendao.Sitio(objectid, nombre, direccion, telefono, descripcion, categoria, latitud, longitud, url_foto, updatedAt));
                    }
                    Log.i("------------------", "---------------------");

                }
                adapter = new SitioAdapter(lista);
                recyclerView.setAdapter(adapter);

                //aquí es donde añado los sitios a la base de datos, para no hacerlo todo en el mismo for, ya que
                //me ha causado problemas de insercción, dejaba datos atrás.

                for (int i = 0; i < lista_dao.size(); i++) {
                        sitioDao.insertOrReplaceInTx(lista_dao.get(i));
                }

            }

        }
    }

}
