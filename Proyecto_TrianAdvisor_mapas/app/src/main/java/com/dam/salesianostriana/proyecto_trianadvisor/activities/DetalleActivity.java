package com.dam.salesianostriana.proyecto_trianadvisor.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_adapter.ValoracionPojoAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
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

        lista_valoraciones = new ArrayList<>();
        lista_valoraciones.add(new ValoracionPojoAdapter("Jesús Pallares", "Flipa con el sitio socio", "14/3/15"));
        lista_valoraciones.add(new ValoracionPojoAdapter("Manué Revuelta", "La tajá que me cogí aqui fue menua chaval", "20/3/15"));
        lista_valoraciones.add(new ValoracionPojoAdapter("Rocio Garcia", "Perfectísima noche con mis chicas! Local de 10", "21/3/15"));
        lista_valoraciones.add(new ValoracionPojoAdapter("Jesús Pallares", "Flipa con el sitio socio", "14/3/15"));
        lista_valoraciones.add(new ValoracionPojoAdapter("Manué Revuelta", "La tajá que me cogí aqui fue menua chaval", "20/3/15"));
        lista_valoraciones.add(new ValoracionPojoAdapter("Rocio Garcia", "Perfectísima noche con mis chicas! Local de 10", "21/3/15"));

        adapter = new ValoracionAdapter(lista_valoraciones);
        recyclerView.setAdapter(adapter);

        //Este FloatingButton abrirá el activity para mandar una valoración sobre el sitio
        //que se está viendo.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

            //Ejecuto el asyntasck que devuelve la valoración del sitio.
            new ObtenerValoracionUnSitioTask().execute(obj_id);
        }
    }


    //Devuelve las valoraciones de un sitio dado el objectId
    private class ObtenerValoracionUnSitioTask extends AsyncTask<String, Void, Valoracion> {
        @Override
        protected Valoracion doInBackground(String... params) {
            if (params != null) {

                Call<Valoracion> valoracionCall = Servicio.instanciarServicio().cargarValoraciones(params[0]);
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

            //Realizo la media de todas las valoraciones obtenidas.
            float total_valoraciones = 0;
            for (int i = 0; i < valoracion.getResults().size(); i++) {
                total_valoraciones += valoracion.getResults().get(i).getValoracion();
            }
            total_valoraciones = total_valoraciones / valoracion.getResults().size();

            //Le asocio el resultado al ratingBar
            ratingBarDetalle.setIsIndicator(true);
            ratingBarDetalle.setRating(total_valoraciones);

        }
    }
}