package com.dam.salesianostriana.proyecto_trianadvisor.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.utilidades.Utils;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_adapter.ValoracionPojoAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author Jesús Pallares on 13/12/2015.
 */
public class ValoracionAdapter extends RecyclerView.Adapter<ValoracionAdapter.ViewHolder> {

    private List<ValoracionPojoAdapter> lista_datos;
    Context contexto;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre_usuario, comentario, fecha;
        ImageView imagen_usuario;

        public ViewHolder(View v) {
            super(v);

            nombre_usuario = (TextView) v.findViewById(R.id.textViewNomUsuarioVal);
            comentario = (TextView) v.findViewById(R.id.textViewComentarioVal);
            fecha = (TextView) v.findViewById(R.id.txtFechaVal);
            imagen_usuario = (ImageView) v.findViewById(R.id.imageViewUserValoracion);

        }
    }

    public ValoracionAdapter(List<ValoracionPojoAdapter> lista_datos) {this.lista_datos = lista_datos;}

    @Override
    public ValoracionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_valoraciones, viewGroup, false);


        contexto = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ValoracionPojoAdapter valoracion_actual = lista_datos.get(position);

        holder.nombre_usuario.setText(valoracion_actual.getNombre());
        holder.comentario.setText(valoracion_actual.getComentario());
        holder.fecha.setText(Utils.formatearFechaString(Utils.FORMATO_FECHA,valoracion_actual.getFecha()));
        Picasso.with(contexto).load(valoracion_actual.getImagen()).fit().placeholder(R.drawable.ic_mi_perfil).into(holder.imagen_usuario);

    }


    @Override
    public int getItemCount() {
        return lista_datos.size();
    }


}
