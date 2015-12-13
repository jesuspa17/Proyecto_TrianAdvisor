package com.dam.salesianostriana.proyecto_trianadvisor.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_adapter.Sitio;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jesús Pallares on 12/12/2015.
 */
public class SitioAdapter extends RecyclerView.Adapter<SitioAdapter.ViewHolder> {

    private List<Sitio> lista_datos;
    Context contexto;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre_sitio, direccion, categoria;
        ImageView imagen_categoria;
        GridView grid_favoritos;

        public ViewHolder(View v) {
            super(v);
            nombre_sitio = (TextView) v.findViewById(R.id.textViewNombre);
            direccion = (TextView) v.findViewById(R.id.textViewCalle);
            categoria = (TextView) v.findViewById(R.id.textViewCategoria);
            grid_favoritos = (GridView) v.findViewById(R.id.gridViewFavoritos);
            imagen_categoria = (ImageView) v.findViewById(R.id.imageViewCategoria);

        }
    }

    public SitioAdapter(List<Sitio> lista_datos) {this.lista_datos = lista_datos;}

    @Override
    public SitioAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_sitio, viewGroup, false);


        contexto = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SitioAdapter.ViewHolder holder, int i) {

        Sitio sitio_actual = lista_datos.get(i);

        holder.nombre_sitio.setText(sitio_actual.getNombre());
        holder.direccion.setText(sitio_actual.getDireccion());
        holder.categoria.setText(sitio_actual.getCategoria());

        if(sitio_actual.getCategoria().equalsIgnoreCase("Bares")){
            holder.imagen_categoria.setImageResource(R.drawable.beer);
        }else{
            holder.imagen_categoria.setImageResource(R.drawable.restaurante);
        }

        ArrayList<Boolean> lista_numeros = new ArrayList<>();

        for(int x = 0; x<sitio_actual.getPuntuacion();x++){
            lista_numeros.add(true);
        }
        for(int x = 0; x<5 - sitio_actual.getPuntuacion();x++){
            lista_numeros.add(false);
        }


        FavoritoAdapter fav_adapter = new FavoritoAdapter(contexto, lista_numeros);
        holder.grid_favoritos.setAdapter(fav_adapter);
        holder.grid_favoritos.setFocusable(false);
        holder.grid_favoritos.setPressed(false);
        holder.grid_favoritos.setVerticalScrollBarEnabled(false);
        holder.grid_favoritos.setSelected(false);

    }

    @Override
    public int getItemCount() {
        return lista_datos.size();
    }


}
