package com.dam.salesianostriana.retrofit_trianadvisor.pojos_RetroFit.valoraciones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesús Pallares on 14/12/2015.
 */
public class Usuario {

    @SerializedName("__type")
    @Expose
    private String Type;
    @SerializedName("className")
    @Expose
    private String className;
    @SerializedName("objectId")
    @Expose
    private String objectId;

    /**
     * No args constructor for use in serialization
     *
     */
    public Usuario() {
    }

    /**
     *
     * @param objectId
     * @param Type
     * @param className
     */
    public Usuario(String Type, String className, String objectId) {
        this.Type = Type;
        this.className = className;
        this.objectId = objectId;
    }

    /**
     *
     * @return
     * The Type
     */
    public String getType() {
        return Type;
    }

    /**
     *
     * @param Type
     * The __type
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     *
     * @return
     * The className
     */
    public String getClassName() {
        return className;
    }

    /**
     *
     * @param className
     * The className
     */
    public void setClassName(String className) {
        this.className = className;
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

}