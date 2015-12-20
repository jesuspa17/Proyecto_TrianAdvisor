package com.dam.salesianostriana.proyecto_trianadvisor.pojos_adapter;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * @author Jesús Pallares on 16/12/2015.
 */
public class MyItem implements ClusterItem{

    private final LatLng mPosition;
    private String nombre;

    public MyItem(double lat, double lng, String nombre) {
        mPosition = new LatLng(lat, lng);
        this.nombre= nombre;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public LatLng getmPosition() {
        return mPosition;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}