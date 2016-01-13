package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesús Pallares on 14/12/2015.
 */
public class ResultSitio {

    @SerializedName("categoria")
    @Expose
    private String categoria;
    @SerializedName("coordenadas")
    @Expose
    private Coordenadas coordenadas;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("foto")
    @Expose
    private Foto foto;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    private String url_foto;

    /**
     * No args constructor for use in serialization
     */
    public ResultSitio() {
    }

    public ResultSitio(String objectId, String nombre, String direccion,Foto foto){
        this.objectId = objectId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.foto = foto;
    }
/*
    public ResultSitio(String objectId, String nombre, String direccion, String categoria, Foto foto){
        this.objectId = objectId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.categoria = categoria;
        this.foto = foto;
    }*/

    public ResultSitio(String objectId, String nombre, String direccion, String categoria, String foto){
        this.objectId = objectId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.categoria = categoria;
        this.url_foto = foto;
    }

    /**
     * @param updatedAt
     * @param nombre
     * @param direccion
     * @param categoria
     * @param objectId
     * @param createdAt
     * @param coordenadas
     * @param telefono
     * @param descripcion
     * @param foto
     */

    public ResultSitio(String categoria, Coordenadas coordenadas, String createdAt, String descripcion, String direccion, Foto foto, String nombre, String objectId, String telefono, String updatedAt) {
        this.categoria = categoria;
        this.coordenadas = coordenadas;
        this.createdAt = createdAt;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.foto = foto;
        this.nombre = nombre;
        this.objectId = objectId;
        this.telefono = telefono;
        this.updatedAt = updatedAt;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    /**
     * @return The categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria The categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return The coordenadas
     */
    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    /**
     * @param coordenadas The coordenadas
     */
    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion The descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return The direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion The direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return The foto
     */
    public Foto getFoto() {
        return foto;
    }

    /**
     * @param foto The foto
     */
    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    /**
     * @return The nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre The nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return The objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param objectId The objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * @return The telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono The telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Result{" +
                "categoria='" + categoria + '\'' +
                ", coordenadas=" + coordenadas +
                ", createdAt='" + createdAt + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", foto=" + foto +
                ", nombre='" + nombre + '\'' +
                ", objectId='" + objectId + '\'' +
                ", telefono='" + telefono + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
