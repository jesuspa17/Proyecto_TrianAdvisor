package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesús Pallares on 14/12/2015.
 */
public class ResultValoracion {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
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
    @SerializedName("valoracion")
    @Expose
    private float valoracion;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResultValoracion() {
    }

    /**
     *
     * @param updatedAt
     * @param sitio
     * @param valoracion
     * @param usuario
     * @param objectId
     * @param createdAt
     */
    public ResultValoracion(String createdAt, String objectId, Sitio sitio, String updatedAt, Usuario usuario, float valoracion) {
        this.createdAt = createdAt;
        this.objectId = objectId;
        this.sitio = sitio;
        this.updatedAt = updatedAt;
        this.usuario = usuario;
        this.valoracion = valoracion;
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
