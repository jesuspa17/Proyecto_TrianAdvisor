package com.dam.salesianostriana.proyecto_trianadvisor;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import retrofit.Call;
import retrofit.Response;

/**
 * @author Jesús Pallares on 16/12/2015.
 */
public class PopupAdapter implements GoogleMap.InfoWindowAdapter{

    private View popup=null;
    private LayoutInflater inflater=null;
    private Map<Marker, ResultSitio> ciudades;
    Context context;
    float valoracion;

    public PopupAdapter(Context context, LayoutInflater inflater, Map<Marker,ResultSitio> ciudades) {
        this.context = context;
        this.inflater = inflater;
        this.ciudades = ciudades;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (popup == null) {
            popup=inflater.inflate(R.layout.popup_marker, null);
        }

        ResultSitio ciudadActual = ciudades.get(marker);

        if(ciudadActual!=null){


            TextView tv=(TextView)popup.findViewById(R.id.textViewNombreInfoWindow);
            tv.setText(ciudadActual.getNombre());

            tv=(TextView)popup.findViewById(R.id.textViewDireccionInfoWindow);
            tv.setText(ciudadActual.getDireccion());

            ImageView imageView = (ImageView)popup.findViewById(R.id.imageViewInfoWindow);
            Picasso.with(context).load(ciudadActual.getFoto().getUrl()).resize(45,45).into(imageView);

            final RatingBar ratingBar = (RatingBar) popup.findViewById(R.id.ratingBarValoracionInfoWindow);
            Log.i("VALORACION_OBTENIDA", "VALORACION: " + valoracion);


            String url = "{\"sitio\": { \"__type\": \"Pointer\", \"className\": \"sitio\", " +
                    "\"objectId\": \""+ciudadActual.getObjectId()+"\" } }";

            String url_valoraciones = "";
            try {
                url_valoraciones = URLEncoder.encode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            new ObtenerValoracionesTask() {

                @Override
                protected void onPostExecute(Valoracion valoracion) {
                    super.onPostExecute(valoracion);

                    if (valoracion != null) {

                        float total_valoraciones = 0;
                        for (int i = 0; i < valoracion.getResults().size(); i++) {

                            if(valoracion.getResults().get(i).getValoracion()!=null){
                                total_valoraciones += valoracion.getResults().get(i).getValoracion();
                            }
                        }
                        total_valoraciones = total_valoraciones / valoracion.getResults().size();

                        ratingBar.setRating(total_valoraciones);
                        ratingBar.setIsIndicator(true);

                    }

                }
            }.execute(url_valoraciones);

        }

        return(popup);
    }


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
                return response.body();

            } else {
                return null;
            }
        }

    }
}
