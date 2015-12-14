package com.dam.salesianostriana.retrofit_trianadvisor.pojos_RetroFit.sitios;

/**
 * Created by Jesús Pallares on 14/12/2015.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordenadas {

    @SerializedName("__type")
    @Expose
    private String Type;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    /**
     * No args constructor for use in serialization
     */
    public Coordenadas() {
    }

    /**
     * @param Type
     * @param longitude
     * @param latitude
     */
    public Coordenadas(String Type, Double latitude, Double longitude) {
        this.Type = Type;
        this.latitude = latitude;
        this.longitude = longitude;
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
     * @return The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Coordenadas{" +
                "Type='" + Type + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}