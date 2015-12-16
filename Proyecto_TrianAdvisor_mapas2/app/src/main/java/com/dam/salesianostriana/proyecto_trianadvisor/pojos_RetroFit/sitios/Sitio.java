package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.sitios;

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
    private List<ResultSitio> resultSitioses = new ArrayList<ResultSitio>();

    /**
     * No args constructor for use in serialization
     */
    public Sitio() {
    }

    /**
     * @param resultSitioses
     */
    public Sitio(List<ResultSitio> resultSitioses) {
        this.resultSitioses = resultSitioses;
    }

    /**
     * @return The results
     */
    public List<ResultSitio> getResultSitioses() {
        return resultSitioses;
    }

    /**
     * @param resultSitioses The results
     */
    public void setResultSitioses(List<ResultSitio> resultSitioses) {
        this.resultSitioses = resultSitioses;
    }

    @Override
    public String toString() {
        return "Sitio{" +
                "results=" + resultSitioses +
                '}';
    }
}