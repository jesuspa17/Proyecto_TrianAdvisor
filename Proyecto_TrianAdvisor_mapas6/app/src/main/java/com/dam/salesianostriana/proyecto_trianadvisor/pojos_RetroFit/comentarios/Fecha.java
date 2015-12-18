package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.comentarios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesús Pallares on 16/12/2015.
 */
public class Fecha {

    @SerializedName("__type")
    @Expose
    private String Type;
    @SerializedName("iso")
    @Expose
    private String iso;

    /**
     * No args constructor for use in serialization
     */
    public Fecha() {
    }

    /**
     * @param iso
     * @param Type
     */
    public Fecha(String Type, String iso) {
        this.Type = Type;
        this.iso = iso;
    }

    /**
     * @return The Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type The __type
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * @return The iso
     */
    public String getIso() {
        return iso;
    }

    /**
     * @param iso The iso
     */
    public void setIso(String iso) {
        this.iso = iso;
    }

}