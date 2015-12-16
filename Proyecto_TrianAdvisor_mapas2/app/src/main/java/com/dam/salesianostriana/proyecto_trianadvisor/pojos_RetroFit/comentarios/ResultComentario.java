package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.comentarios;

import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Sitio;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesús Pallares on 16/12/2015.
 */
public class ResultComentario {
    @SerializedName("comentario")
    @Expose
    private String comentario;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("fecha")
    @Expose
    private Fecha fecha;
    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("sitio")
    @Expose
    private Sitio sitio;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("usuario")
    @Expose
    private Usuario usuario;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResultComentario() {
    }

    /**
     *
     * @param updatedAt
     * @param sitio
     * @param fecha
     * @param usuario
     * @param objectId
     * @param createdAt
     * @param comentario
     */
    public ResultComentario(String comentario, String createdAt, Fecha fecha, String objectId, Sitio sitio, String updatedAt, Usuario usuario) {
        this.comentario = comentario;
        this.createdAt = createdAt;
        this.fecha = fecha;
        this.objectId = objectId;
        this.sitio = sitio;
        this.updatedAt = updatedAt;
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

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The fecha
     */
    public Fecha getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     * The fecha
     */
    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * @return
     * The objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     *
     * @param objectId
     * The objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

}