package com.dam.salesianostriana.proyecto_trianadvisor.fragments;


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
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Sitio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Response;


public class SitiosFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<ResultSitio> lista_sitos;

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

        lista_sitos = new ArrayList<>();

        //Ejecuto el asyntasck que obtiene los datos de cada sitio.
        new ObtenerSitiosTask().execute();

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

    private class ObtenerSitiosTask extends AsyncTask<Void, Void, Sitio> {

        @Override
        protected Sitio doInBackground(Void... params) {

            Call<Sitio> result = Servicio.instanciarServicio().cargarSitios();
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

            List<ResultSitio> lista = new ArrayList<>();

            if (sitio != null) {

                for (int i = 0; i < sitio.getResultSitioses().size(); i++) {
                    if (sitio.getResultSitioses().get(i).getNombre() != null) {

                        Log.i("SITIO_ID", "SITIO_ID: " + sitio.getResultSitioses().get(i).getObjectId());
                        Log.i("SITIO_NOMBRE", "SITIO_NOMBRE: " + sitio.getResultSitioses().get(i).getNombre());
                        Log.i("SITIO_DESCRIPCION", "SITIO_DESCRIPCION: " + sitio.getResultSitioses().get(i).getFoto().getUrl());

                        lista.add(new ResultSitio(
                                sitio.getResultSitioses().get(i).getObjectId(),
                                sitio.getResultSitioses().get(i).getNombre(),
                                sitio.getResultSitioses().get(i).getDireccion(),
                                sitio.getResultSitioses().get(i).getCategoria(),
                                sitio.getResultSitioses().get(i).getFoto()));

                    }
                    Log.i("------------------", "---------------------");
                }
                adapter = new SitioAdapter(lista);
                recyclerView.setAdapter(adapter);
            }
        }
    }

}
