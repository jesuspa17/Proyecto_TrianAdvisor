package com.dam.salesianostriana.proyecto_trianadvisor.pojos_adapter;

/**
 * @author Jesús Pallares on 13/12/2015.
 */
public class Valoracion {

    private String imagen;
    private String nombre;
    private String comentario;
    private String fecha;

    public Valoracion() {
    }

    public Valoracion(String nombre, String comentario, String fecha) {
        this.nombre = nombre;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public Valoracion(String imagen, String nombre, String comentario, String fecha) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
