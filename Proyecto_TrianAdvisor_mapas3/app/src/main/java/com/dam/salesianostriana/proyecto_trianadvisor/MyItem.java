package com.dam.salesianostriana.proyecto_trianadvisor;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * @author Jesús Pallares on 16/12/2015.
 */
public class MyItem implements ClusterItem{

    private final LatLng mPosition;

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }


}