package com.dam.salesianostriana.proyecto_trianadvisor.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_adapter.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.adaptadores.SitioAdapter;

import java.util.ArrayList;
import java.util.List;


public class SitiosFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<Sitio> lista_sitos;

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
        lista_sitos.add(new Sitio("Las Golondrinas",2,"Calle Antillano Campos 18, Sevilla","Bares"));
        lista_sitos.add(new Sitio("El Remesal",5,"Calle Esperanza De Triana, 10, Sevilla","Restaurante"));
        lista_sitos.add(new Sitio("100 Montaditos",4,"Esquina Calle Condes de Bustillo, Sevilla","Bares"));
        lista_sitos.add(new Sitio("Bar El Neo",3,"Calle Cristobal Colón 14, Sevilla","Bares"));


        adapter = new SitioAdapter(lista_sitos);
        recyclerView.setAdapter(adapter);

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




}
