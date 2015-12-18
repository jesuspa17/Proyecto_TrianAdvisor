package com.dam.salesianostriana.proyecto_trianadvisor.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.adaptadores.ValoracionAdapter;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.comentarios.Comentario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_adapter.ValoracionPojoAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

public class DetalleActivity extends AppCompatActivity {

    ImageView imageViewDetalle;
    TextView txtDescripcion, txtDireccion, txtNumero, txtLlamarAviso;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    LinearLayout linearLayoutTelefono;
    LinearLayout linearLayoutComoLlegar;
    RatingBar ratingBarDetalle;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    List<ValoracionPojoAdapter> lista_valoraciones;

    String object_id;
    double latitud;
    double longitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Rescato el elemento que está dentro del AppBarLayout
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        imageViewDetalle = (ImageView) appBarLayout.findViewById(R.id.imageViewDetalleBar);

        //Rescato los elementos de la interfaz.
        txtDescripcion = (TextView) findViewById(R.id.textViewDescripcionDetalle);
        txtDireccion = (TextView) findViewById(R.id.textViewDireccionDetalle);
        txtNumero = (TextView) findViewById(R.id.textViewNumeroTlfDetalle);
        txtLlamarAviso = (TextView) findViewById(R.id.textViewLlamarAviso);
        ratingBarDetalle = (RatingBar) findViewById(R.id.ratingBarValoracionDetalle);
        linearLayoutComoLlegar = (LinearLayout) findViewById(R.id.linearComoLlegar);
        linearLayoutTelefono = (LinearLayout) findViewById(R.id.linearTelefono);

        //Rescato de la vista anterior el objectId del sitio
        //para realizar la consulta con la que obtendré los detalles.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            object_id = extras.getString("object_id");
            Log.i("OBJECT_RECIBIDO", object_id);
            new ObtenerDatosDeUnSitioTask().execute(object_id);
        }

        //Inicializo los elementos del recyler que contendrá las valoraciones
        recyclerView = (RecyclerView) findViewById(R.id.recycler_valoraciones);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //Este FloatingButton abrirá el activity para mandar una valoración sobre el sitio
        //que se está viendo.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetalleActivity.this,ComentarioValoracionActivity.class)  ;
                i.putExtra("object_id",object_id);
                startActivity(i);
            }
        });

        //Realiza el intent implicito que nos lleva a la ubicación del sitio.
        linearLayoutComoLlegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + latitud + "," + longitud + "");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        //Realiza el intent que abre el número marcado en nuestro tlf.
        linearLayoutTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri tlf = Uri.parse("tel:" + txtNumero.getText().toString());
                Intent i = new Intent(Intent.ACTION_DIAL, tlf);
                startActivity(i);
            }
        });
    }


    //Este asyntasck se encarga de obtener los detalles de un sitio a través de su objecId
    private class ObtenerDatosDeUnSitioTask extends AsyncTask<String, Void, ResultSitio> {
        @Override
        protected ResultSitio doInBackground(String... params) {

            if (params != null) {
                Call<ResultSitio> resultSitioCall = Servicio.instanciarServicio().cargarUnSitio(params[0]);
                Response<ResultSitio> result = null;
                try {
                    result = resultSitioCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert result != null;
                return result.body();

            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResultSitio resultSitio) {
            super.onPostExecute(resultSitio);

            if (resultSitio != null) {

                //Recogo los datos del sitio en variables.
                String obj_id = resultSitio.getObjectId();
                String nom = resultSitio.getNombre().replace("Cervecería", "Cerv.").replace("Bodeguita", "Bod.");
                String direc = resultSitio.getDireccion().replace("Calle", "c/").replace("Sevilla", "");
                String tlf = resultSitio.getTelefono();
                String url_imagen = resultSitio.getFoto().getUrl();

                //Actualizo los elementos de la interfaz
                toolbar.setTitle(nom);

                txtDescripcion.setText(resultSitio.getDescripcion());
                txtDireccion.setText(direc);

                if (resultSitio.getTelefono() == null) {
                    txtLlamarAviso.setText("No se dispone del número");
                    txtNumero.setText("");
                } else {
                    txtNumero.setText(tlf);
                }

                Picasso.with(DetalleActivity.this).load(url_imagen).fit().into(imageViewDetalle);

                //Guardo la latitud y la longitud en variables locales para
                //poder pasárselas al intent que nos abre el mapa con la
                //geolocalización del mapa.
                latitud = resultSitio.getCoordenadas().getLatitude();
                longitud = resultSitio.getCoordenadas().getLongitude();

                String URL_BASE = "{\"sitio\": { \"__type\": \"Pointer\", \"className\": \"sitio\", " +
                        "\"objectId\": \""+obj_id+"\" } }";

                String url_valoraciones = "";

                try {
                    url_valoraciones = URLEncoder.encode(URL_BASE, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //Ejecuto el asyntasck que devuelve la valoración del sitio.
                new ObtenerValoracionUnSitioTask().execute(url_valoraciones);

                String url_comentarios = "";

                try {
                    url_comentarios = URLEncoder.encode(URL_BASE, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //Ejecuto el asyntasck que devuelve los comentarios del sitio.
                new ObtenerComentariosTask().execute(url_comentarios);
            }
        }
    }

    //Devuelve las valoraciones de un sitio dado el objectId
    private class ObtenerValoracionUnSitioTask extends AsyncTask<String, Void, Valoracion> {
        @Override
        protected Valoracion doInBackground(String... params) {
            if (params != null) {
                Call<Valoracion> valoracionCall = Servicio.instanciarServicio().cargarValoracionesUnSitio(params[0]);
                Response<Valoracion> result = null;

                try {
                    result = valoracionCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert result != null;
                return result.body();
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Valoracion valoracion) {
            super.onPostExecute(valoracion);

            if (valoracion != null) {

                //Realizo la media de todas las valoraciones obtenidas.
                float total_valoraciones = 0;
                for (int i = 0; i < valoracion.getResults().size(); i++) {
                    if(valoracion.getResults().get(i).getValoracion()!=null){
                        total_valoraciones += valoracion.getResults().get(i).getValoracion();
                    }
                }
                total_valoraciones = total_valoraciones / valoracion.getResults().size();

                //Le asocio el resultado al ratingBar
                ratingBarDetalle.setIsIndicator(true);
                ratingBarDetalle.setRating(total_valoraciones);
            }
        }
    }

    private class ObtenerComentariosTask extends AsyncTask<String, Void, Comentario> {

        @Override
        protected Comentario doInBackground(String... params) {
            if (params != null) {
                Call<Comentario> comentarioCall = Servicio.instanciarServicio().cargarComentariosUnSitio(params[0]);
                Response<Comentario> comentarioResponse = null;

                try {
                    comentarioResponse = comentarioCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert comentarioResponse != null;
                return comentarioResponse.body();
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Comentario comentario) {
            super.onPostExecute(comentario);

            if(comentario!=null) {
                lista_valoraciones = new ArrayList<>();
                for (int i = 0; i < comentario.getResults().size(); i++) {
                    if (comentario.getResults() != null) {

                        String nom_user_comentario = comentario.getResults().get(i).getUsuario().getNombre();
                        String coment = comentario.getResults().get(i).getComentario();
                        String foto = "";
                        if(comentario.getResults().get(i).getUsuario().getFoto().getUrl()  != null){
                            foto = comentario.getResults().get(i).getUsuario().getFoto().getUrl();
                        }else{
                            foto ="";
                        }
                        String fecha = comentario.getResults().get(i).getCreatedAt();
                        lista_valoraciones.add(new ValoracionPojoAdapter(foto, nom_user_comentario, coment, fecha));
                    }
                }

                adapter = new ValoracionAdapter(lista_valoraciones);
                recyclerView.setAdapter(adapter);
            }
        }
    }

}
