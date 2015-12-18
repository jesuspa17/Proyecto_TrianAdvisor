package com.dam.salesianostriana.proyecto_trianadvisor.pojos_adapter;

/**
 * @author Jesús Pallares on 12/12/2015.
 */
public class Sitio {

    private String nombre;
    private int puntuacion;
    private String direccion;
    private String categoria;

    public Sitio() {
    }

    public Sitio(String nombre, int puntuacion, String direccion, String categoria) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.direccion = direccion;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
