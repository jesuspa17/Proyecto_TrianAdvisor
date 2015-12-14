package com.dam.salesianostriana.retrofit_trianadvisor.pojos_RetroFit.sitios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesús Pallares on 14/12/2015.
 */
public class Foto {

    @SerializedName("__type")
    @Expose
    private String Type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * No args constructor for use in serialization
     */
    public Foto() {
    }

    /**
     * @param Type
     * @param name
     * @param url
     */
    public Foto(String Type, String name, String url) {
        this.Type = Type;
        this.name = name;
        this.url = url;
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
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Foto{" +
                "Type='" + Type + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
