package com.dam.salesianostriana.proyecto_trianadvisor.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.activities.DetalleActivity;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;
import com.dam.salesianostriana.proyecto_trianadvisor.utilidades.RoundedTransformation;
import com.dam.salesianostriana.proyecto_trianadvisor.utilidades.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

/**
 * @author Jesús Pallares on 12/12/2015.
 */
public class SitioAdapter extends RecyclerView.Adapter<SitioAdapter.ViewHolder> {

    private List<ResultSitio> lista_datos;
    Context contexto;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre_sitio, direccion;
        ImageView imagen_categoria, imagen_bar;
        RatingBar ratingBar;
        View mi_vista;

        public ViewHolder(View v) {
            super(v);

            nombre_sitio = (TextView) v.findViewById(R.id.textViewNombre);
            direccion = (TextView) v.findViewById(R.id.textViewCalle);
            imagen_categoria = (ImageView) v.findViewById(R.id.imageViewCategoria);
            imagen_bar = (ImageView) v.findViewById(R.id.imageViewBar);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBarValoracion);
            mi_vista = v;
        }
    }

    public SitioAdapter(List<ResultSitio> lista_datos) {
        this.lista_datos = lista_datos;
    }

    @Override
    public SitioAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_sitio, viewGroup, false);

        contexto = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SitioAdapter.ViewHolder holder, int i) {

        ResultSitio sitio_actual = lista_datos.get(i);

        //Se obtienen los datos del sitio.
        final String obj_id = sitio_actual.getObjectId();
        String nom = sitio_actual.getNombre().replace("Cervecería", "Cerv.").replace("Bodeguita", "Bod.");
        String direc = sitio_actual.getDireccion().replace("Calle", "c/").replace("Sevilla", "");
        String cat = sitio_actual.getCategoria();
        String url_imagen = sitio_actual.getFoto().getUrl();


        String json_base = "{\"sitio\": { \"__type\": \"Pointer\", \"className\": \"sitio\", " +
                "\"objectId\": \""+obj_id+"\" } }";

        /*
        Asyntasck que devuelve las valoraciones del sitio.

        Coje las valoraciones de cada sitio y les hace la media.
        Al finalizar, las muestra dibujadas en un rating bar.

        */

        new ObtenerValoracionesTask() {
            @Override
            protected void onPostExecute(Valoracion valoracion) {
                super.onPostExecute(valoracion);

                if (valoracion != null) {

                    float total_valoraciones = 0;
                    for (int i = 0; i < valoracion.getResults().size(); i++) {
                            total_valoraciones += valoracion.getResults().get(i).getValoracion();
                    }
                    if(valoracion.getResults().size()!=0){
                        total_valoraciones = total_valoraciones / valoracion.getResults().size();
                    }

                    /**
                     * El rating bar personalizado está hecho a través del archivo que está en drawable;
                     * "rating_selector" que establece un estilo personalizado para el rating
                     *
                     * Dicho estilo se asociará al ratingBar a través del archivo Styles
                     * donde se creará un estilo personalizado para este elmento gráfico.
                     * A dicho ratingbar se le establece como progressDrawable el estilo que se ha creado.
                     *
                     * El las 2 siguientes líneas de código, lo que se hace es:
                     *
                     * 1. Establecer en función de la puntuación obtenida el número de
                     * estrellas que queremos que salgan marcadas.
                     *
                     * 2. Desactivar que en el rating se pueda modificar la puntuación,
                     * puesto que este rating se usa de manera informativa.
                     *
                     */

                    holder.ratingBar.setRating(total_valoraciones);
                    holder.ratingBar.setIsIndicator(true);
                }
            }
        }.execute(Utils.encodearCadena(json_base));

        holder.nombre_sitio.setText(nom);
        holder.direccion.setText(direc);

        if (cat.equalsIgnoreCase("Bares")) {
            holder.imagen_categoria.setImageResource(R.drawable.beer);
        } else {
            holder.imagen_categoria.setImageResource(R.drawable.restaurante);
        }

        Picasso.with(contexto).load(url_imagen)
                .resize(120, 120)
                .error(R.drawable.logo_triana)
                .transform(new RoundedTransformation(70,0))
                .placeholder(R.drawable.cargando)
                .into(holder.imagen_bar);

        holder.mi_vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(contexto, DetalleActivity.class);
                i.putExtra("object_id", obj_id);
                contexto.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_datos.size();
    }

    //Realiza la consulta que obtiene todas las valoraciones de un sitio dado su objectId
    //El onPostExecute se realiza en el onViewHolder.
    private class ObtenerValoracionesTask extends AsyncTask<String, Void, Valoracion> {

        @Override
        protected Valoracion doInBackground(String... params) {

            if (params != null) {
                Call<Valoracion> result = Servicio.instanciarServicio().cargarValoracionesUnSitio(params[0]);
                Response<Valoracion> response = null;
                try {
                    response = result.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert response != null;

                if(response.code() == 200 || response.code() == 201){
                    return response.body();
                }else{
                    return null;
                }

            } else {
                return null;
            }
        }

    }


}
