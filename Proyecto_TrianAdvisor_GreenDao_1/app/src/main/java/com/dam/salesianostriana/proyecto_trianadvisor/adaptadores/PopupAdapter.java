package com.dam.salesianostriana.proyecto_trianadvisor.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.ResultSitio;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * @author Jesús Pallares on 16/12/2015.
 */
public class PopupAdapter implements GoogleMap.InfoWindowAdapter{

    private View popup=null;
    private LayoutInflater inflater=null;
    private Map<Marker, ResultSitio> ciudades;
    Context context;

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

        }

        return(popup);
    }

}
