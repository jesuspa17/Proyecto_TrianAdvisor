package com.dam.salesianostriana.proyecto_trianadvisor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.dam.salesianostriana.proyecto_trianadvisor.adaptadores.FavoritoAdapter;
import com.dam.salesianostriana.proyecto_trianadvisor.adaptadores.ValoracionAdapter;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_adapter.Valoracion;

import java.util.ArrayList;
import java.util.List;

public class DetalleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<Valoracion> lista_valoraciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Título del activity.
        toolbar.setTitle("El Remesal");
        setSupportActionBar(toolbar);

        GridView gridView = (GridView) findViewById(R.id.gridViewFavoritosDetalle);
        ArrayList<Boolean> lista_numeros = new ArrayList<>();
        //número de la valoración obtenida
        for(int x = 0; x<3;x++){
            lista_numeros.add(true);
        }
        //Se resta 5-número de la valoración obtenida para saber cuantas estrellas tienen que
        //aparecer sin marcar.
        for(int x = 0; x<5-3;x++){
            lista_numeros.add(false);
        }


        lista_valoraciones = new ArrayList<>();

        FavoritoAdapter fav_adapter = new FavoritoAdapter(this, lista_numeros);
        gridView.setAdapter(fav_adapter);
        gridView.setFocusable(false);
        gridView.setPressed(false);
        gridView.setVerticalScrollBarEnabled(false);
        gridView.setSelected(false);

        //Inicializo las caractersísticas del recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_valoraciones);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        lista_valoraciones.add(new Valoracion("Jesús Pallares", "Flipa con el sitio socio", "14/3/15"));
        lista_valoraciones.add(new Valoracion("Manué Revuelta", "La tajá que me cogí aqui fue menua chaval", "20/3/15"));
        lista_valoraciones.add(new Valoracion("Rocio Garcia", "Perfectísima noche con mis chicas! Local de 10", "21/3/15"));
        lista_valoraciones.add(new Valoracion("Jesús Pallares", "Flipa con el sitio socio", "14/3/15"));
        lista_valoraciones.add(new Valoracion("Manué Revuelta", "La tajá que me cogí aqui fue menua chaval", "20/3/15"));
        lista_valoraciones.add(new Valoracion("Rocio Garcia", "Perfectísima noche con mis chicas! Local de 10", "21/3/15"));

        adapter = new ValoracionAdapter(lista_valoraciones);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
