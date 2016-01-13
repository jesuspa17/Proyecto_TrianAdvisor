package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario;

import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios.Foto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesús Pallares on 15/12/2015.
 */
public class Usuario {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("foto")
    @Expose
    private Foto foto;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("sessionToken")
    @Expose
    private String sessionToken;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("username")
    @Expose
    private String username;

    /**
     * No args constructor for use in serialization
     */
    public Usuario() {
    }

    /**
     * @param updatedAt
     * @param nombre
     * @param sessionToken
     * @param username
     * @param email
     * @param objectId
     * @param createdAt
     * @param foto
     */
    public Usuario(String createdAt, String email, Foto foto, String nombre, String objectId, String sessionToken, String updatedAt, String username) {
        this.createdAt = createdAt;
        this.email = email;
        this.foto = foto;
        this.nombre = nombre;
        this.objectId = objectId;
        this.sessionToken = sessionToken;
        this.updatedAt = updatedAt;
        this.username = username;
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
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return The sessionToken
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * @param sessionToken The sessionToken
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
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

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

}