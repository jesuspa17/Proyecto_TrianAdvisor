package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesús Pallares on 14/12/2015.
 */
public class Valoracion {

    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<Result>();

    /**
     * No args constructor for use in serialization
     */
    public Valoracion() {
    }

    /**
     * @param results
     */
    public Valoracion(List<Result> results) {
        this.results = results;
    }

    /**
     * @return The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

}