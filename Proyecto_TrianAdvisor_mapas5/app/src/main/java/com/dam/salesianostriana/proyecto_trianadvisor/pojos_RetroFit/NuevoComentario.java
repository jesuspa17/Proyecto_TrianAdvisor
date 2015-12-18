package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit;

import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Usuario;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesús Pallares on 17/12/2015.
 */
public class NuevoComentario {

    @SerializedName("sitio")
    @Expose
    private Sitio sitio;
    @SerializedName("usuario")
    @Expose
    private Usuario usuario;
    @SerializedName("comentario")
    @Expose
    private String comentario;

    /**
     * No args constructor for use in serialization
     *
     */
    public NuevoComentario() {
    }

    /**
     *
     * @param sitio
     * @param usuario
     * @param comentario
     */
    public NuevoComentario(Sitio sitio, Usuario usuario, String comentario) {
        this.sitio = sitio;
        this.usuario = usuario;
        this.comentario = comentario;
    }

    /**
     *
     * @return
     * The sitio
     */
    public Sitio getSitio() {
        return sitio;
    }

    /**
     *
     * @param sitio
     * The sitio
     */
    public void setSitio(Sitio sitio) {
        this.sitio = sitio;
    }

    /**
     *
     * @return
     * The usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     *
     * @param usuario
     * The usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return
     * The comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     *
     * @param comentario
     * The comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}