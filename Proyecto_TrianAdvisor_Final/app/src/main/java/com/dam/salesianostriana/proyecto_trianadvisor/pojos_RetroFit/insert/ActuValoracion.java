package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.insert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Jesús Pallares on 19/12/2015.
 */
public class ActuValoracion {

    @SerializedName("valoracion")
    @Expose
    private float valoracion;

    /**
     * No args constructor for use in serialization
     *
     */
    public ActuValoracion() {
    }

    /**
     *
     * @param valoracion
     */
    public ActuValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    /**
     *
     * @return
     * The valoracion
     */
    public float getValoracion() {
        return valoracion;
    }

    /**
     *
     * @param valoracion
     * The valoracion
     */
    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

}
