package com.example;

import java.nio.file.Files;
import java.nio.file.Paths;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class GreenDaoTrianAdvisor {

    public static void main(String[] args) {

        Schema schema = new Schema(1000, "");
        crearTablas(schema);
        try {

            if (!Files.isDirectory(Paths.get("./src-gen")))
                Files.createDirectory(Paths.get("./src-gen"));

            new DaoGenerator().generateAll(schema, "./src-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void crearTablas(Schema schema){

        //Tabla Usuario.
        Entity usuario = schema.addEntity("Usuario");
        usuario.addStringProperty("objectId");
        usuario.addStringProperty("nombre");
        usuario.addStringProperty("email");
        usuario.addStringProperty("username");
        usuario.addStringProperty("url_foto");
        usuario.addStringProperty("sessionToken");

        //Tabla Sitios.
        Entity sitio = schema.addEntity("Sitio");
        sitio.addStringProperty("objectId");
        sitio.addStringProperty("nombre");
        sitio.addStringProperty("direccion");
        sitio.addStringProperty("telefono");
        sitio.addStringProperty("descripcion");
        sitio.addStringProperty("categoria");
        sitio.addStringProperty("coordenadas");
        sitio.addStringProperty("url_foto");


        //Tabla Valoraciones.
        Entity valoracion = schema.addEntity("Valoracion");
        Property sitioId = valoracion.addStringProperty("sitioId").getProperty();
        valoracion.addStringProperty("objectId");
        valoracion.addDoubleProperty("valoracion");
        valoracion.addToOne(sitio,sitioId);

        ToMany sitioToValoraciones  = valoracion.addToMany(valoracion,sitioId);


        //Tabla Comentarios.
        Entity comentario = schema.addEntity("Comentario");
        Property usuarioId = comentario.addStringProperty("usuarioId").getProperty();
        Property comentarioId = comentario.addStringProperty("comentarioId").getProperty();
        comentario.addStringProperty("objectId");
        comentario.addStringProperty("cuerpo_comentario");
        comentario.addStringProperty("fecha");
        comentario.addToOne(sitio, sitioId);
        comentario.addToOne(usuario,comentarioId);

        ToMany sitioToComentarios = comentario.addToMany(comentario,usuarioId);
        ToMany usuarioToComentario = usuario.addToMany(comentario,comentarioId);


    }
}

