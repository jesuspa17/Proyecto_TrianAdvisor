package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit;

import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Usuario;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Jesús Pallares on 18/12/2015.
 */
public class NuevaValoracion {

    @SerializedName("sitio")
    @Expose
    private Sitio sitio;
    @SerializedName("usuario")
    @Expose
    private Usuario usuario;
    @SerializedName("valoracion")
    @Expose
    private Integer valoracion;

    /**
     * No args constructor for use in serialization
     */
    public NuevaValoracion() {
    }

    /**
     * @param sitio
     * @param valoracion
     * @param usuario
     */
    public NuevaValoracion(Sitio sitio, Usuario usuario, Integer valoracion) {
        this.sitio = sitio;
        this.usuario = usuario;
        this.valoracion = valoracion;
    }

    /**
     * @return The sitio
     */
    public Sitio getSitio() {
        return sitio;
    }

    /**
     * @param sitio The sitio
     */
    public void setSitio(Sitio sitio) {
        this.sitio = sitio;
    }

    /**
     * @return The usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario The usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return The valoracion
     */
    public Integer getValoracion() {
        return valoracion;
    }

    /**
     * @param valoracion The valoracion
     */
    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

}