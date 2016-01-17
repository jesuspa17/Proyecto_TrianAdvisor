package com.dam.salesianostriana.proyecto_trianadvisor.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.activities.DetalleActivity;
import com.dam.salesianostriana.proyecto_trianadvisor.greendao.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.greendao.SitioDao;
import com.dam.salesianostriana.proyecto_trianadvisor.greendao.ValoracionDao;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;
import com.dam.salesianostriana.proyecto_trianadvisor.utilidades.RoundedTransformation;
import com.dam.salesianostriana.proyecto_trianadvisor.utilidades.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
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

        //Obtiene el sitio de la iteración.
        final ResultSitio sitio_actual = lista_datos.get(i);

        //Se obtienen los datos del sitio.
        final String obj_id = sitio_actual.getObjectId();
        String nom = sitio_actual.getNombre().replace("Cervecería", "Cerv.").replace("Bodeguita", "Bod.");
        String direc = sitio_actual.getDireccion().replace("Calle", "c/").replace("Sevilla", "");
        String cat = sitio_actual.getCategoria();
        String url_imagen = sitio_actual.getUrl_foto();

        //preferencias de la cual obtendré la fecha que necesito para pasarle al Asyntask que
        //devuelve las valoraciones a partir de una fecha concreta.
        SharedPreferences preferences = contexto.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        //JSON que se le pasará al servicio para realizar la consulta al API.
        String json_desde_fecha = "{\"sitio\":{ \"__type\":\"Pointer\", \"className\":\"sitio\", \"objectId\":\"" + obj_id + "\"},\"updatedAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"" + preferences.getString("fecha_valoraciones", null) + "\"}}}";

        //Inicializo los objetos necesarios para obtener los datos de la BD
        final ValoracionDao valoracionDao = Utils.instanciarBD(contexto).getValoracionDao();
        final SitioDao sitioDao = Utils.instanciarBD(contexto).getSitioDao();
        final Sitio sitio = sitioDao.queryBuilder().where(SitioDao.Properties.ObjectId.eq(obj_id)).unique();


        /*
        Asyntasck que devuelve las valoraciones del sitio.
        Coje las valoraciones de cada sitio y les hace la media.
        Al finalizar, las muestra dibujadas en un rating bar.
        */

        if (Utils.comprobarInternet(contexto)) {

            new ObtenerValoracionesTask() {
                @Override
                protected void onPostExecute(Valoracion valoracion) {
                    super.onPostExecute(valoracion);

                    List<com.dam.salesianostriana.proyecto_trianadvisor.greendao.Valoracion> lista_dao = new ArrayList<>();

                    if (valoracion != null) {
                        float total_valoraciones = 0;
                        for (int i = 0; i < valoracion.getResults().size(); i++) {

                            String objectid = valoracion.getResults().get(i).getObjectId();
                            String val = String.valueOf(valoracion.getResults().get(i).getValoracion());
                            String updatedAt = valoracion.getResults().get(i).getUpdatedAt();

                            Log.i("TAMAÑO ARRAY","TAM: " + valoracion.getResults().size());
                            Log.i("VAL_NUEVA","OB: " + objectid);
                            Log.i("VAL_NUEVA","VAL: " + val);
                            Log.i("VAL_NUEVA","UP: " + updatedAt);

                            total_valoraciones += valoracion.getResults().get(i).getValoracion();

                            if(valoracion.getResults().size()!=0){
                                total_valoraciones = total_valoraciones / valoracion.getResults().size();
                            }

                            if(!valoracion.getResults().isEmpty()){

                                //Guardo en la base de datos las valoraciones de todos los sitios
                                com.dam.salesianostriana.proyecto_trianadvisor.greendao.Valoracion val_dao
                                        = new com.dam.salesianostriana.proyecto_trianadvisor.greendao.Valoracion(
                                        objectid,
                                        val,
                                        updatedAt,
                                        sitio.getId());

                                com.dam.salesianostriana.proyecto_trianadvisor.greendao.Valoracion v = valoracionDao.queryBuilder().where(ValoracionDao.Properties.ObjectId.eq(objectid)).unique();

                                if(v==null){
                                    valoracionDao.insertOrReplace(val_dao);
                                }else{
                                    valoracionDao.delete(v);
                                    valoracionDao.insert(val_dao);
                                }
                            }
                        }

                        //Es la lista de las valoraciones que tiene un sitio almacenadas en GreenDao. Se obtiene a través de la id del sitio.
                        List<com.dam.salesianostriana.proyecto_trianadvisor.greendao.Valoracion> lista_dao_final = valoracionDao.queryBuilder().where(ValoracionDao.Properties.SitioId_v.eq(sitio.getId())).list();
                        holder.ratingBar.setRating(Utils.calcularMediaValoracionesGreenDao(lista_dao_final));

                    }
                }
            }.execute(Utils.encodearCadena(json_desde_fecha));

        } else {

            //Aquí estoy haciendo una lista de valoraciones en función del id del sitio que está recorriendo
            //de este modo, solo hará la media del sitio actual de la iteración.
            List<com.dam.salesianostriana.proyecto_trianadvisor.greendao.Valoracion> lista_valoraciones = valoracionDao.queryBuilder().where(ValoracionDao.Properties.SitioId_v.eq(sitio.getId())).list();
            holder.ratingBar.setRating(Utils.calcularMediaValoracionesGreenDao(lista_valoraciones));

        }

        holder.ratingBar.setIsIndicator(true);
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
                .transform(new RoundedTransformation(70, 0))
                .placeholder(R.drawable.cargando)
                .into(holder.imagen_bar);


        holder.mi_vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(contexto, DetalleActivity.class);
                i.putExtra("object_id", obj_id);
                editor.putString("fecha_valoraciones", Utils.obtenerFechaSistema());
                editor.apply();
                contexto.startActivity(i);

            }
        });

        if (sitio_actual == lista_datos.get(lista_datos.size() - 1)) {
            editor.putString("fecha_valoraciones", Utils.obtenerFechaSistema());
            editor.apply();
        }

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
                Call<Valoracion> result = Servicio.instanciarServicio().obtenerValoracionesDesdeFecha(params[0]);
                Response<Valoracion> response = null;
                try {
                    response = result.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert response != null;

                if (response.code() == 200 || response.code() == 201) {
                    return response.body();
                } else {
                    return null;
                }

            } else {
                return null;
            }
        }

    }


}
