package com.dam.salesianostriana.retrofit_trianadvisor.pojos_RetroFit.sitios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesús Pallares on 14/12/2015.
 */
public class Sitio {

    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<Result>();

    /**
     * No args constructor for use in serialization
     */
    public Sitio() {
    }

    /**
     * @param results
     */
    public Sitio(List<Result> results) {
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

    @Override
    public String toString() {
        return "Sitio{" +
                "results=" + results +
                '}';
    }
}