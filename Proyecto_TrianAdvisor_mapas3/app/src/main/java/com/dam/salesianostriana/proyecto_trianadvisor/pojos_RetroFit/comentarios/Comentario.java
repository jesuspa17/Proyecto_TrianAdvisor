package com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.comentarios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesús Pallares on 16/12/2015.
 */
public class Comentario {

    @SerializedName("results")
    @Expose
    private List<ResultComentario> results = new ArrayList<ResultComentario>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Comentario() {
    }

    /**
     *
     * @param results
     */
    public Comentario(List<ResultComentario> results) {
        this.results = results;
    }

    /**
     *
     * @return
     * The results
     */
    public List<ResultComentario> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<ResultComentario> results) {
        this.results = results;
    }

}