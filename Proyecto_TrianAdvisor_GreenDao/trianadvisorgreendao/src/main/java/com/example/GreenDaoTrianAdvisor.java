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

        Schema schema = new Schema(1000, "com.dam.salesianostriana.proyecto_trianadvisor.greendao");
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


        ////////////////////////////////////////////////////////////////////////////////////////////////////

        //Tabla Usuario.
        Entity usuario = schema.addEntity("Usuario");
        usuario.addIdProperty();
        usuario.addStringProperty("objectId");
        usuario.addStringProperty("nombre");
        usuario.addStringProperty("email");
        usuario.addStringProperty("username");
        usuario.addStringProperty("url_foto");
        usuario.addStringProperty("sessionToken");
        usuario.addStringProperty("updatedAt");

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        //Tabla Sitios.
        Entity sitio = schema.addEntity("Sitio");
        sitio.addIdProperty();
        sitio.addStringProperty("objectId");
        sitio.addStringProperty("nombre");
        sitio.addStringProperty("direccion");
        sitio.addStringProperty("telefono");
        sitio.addStringProperty("descripcion");
        sitio.addStringProperty("categoria");
        sitio.addStringProperty("latitud");
        sitio.addStringProperty("longitud");
        sitio.addStringProperty("url_foto");
        sitio.addStringProperty("updatedAt");


        ////////////////////////////////////////////////////////////////////////////////////////////////////


        //Tabla Valoraciones.
        Entity valoracion = schema.addEntity("Valoracion");
        valoracion.addIdProperty();
        valoracion.addStringProperty("objectId");
        valoracion.addStringProperty("valoracion");
        valoracion.addStringProperty("updatedAt");

        //relación para la tabla valoracion-sitio
        Property sitioId_v = valoracion.addLongProperty("sitioId_v").getProperty();
        valoracion.addToOne(sitio, sitioId_v);

        //relacion tabla valoracion-usuario
        /*Property usuarioId_v = valoracion.addLongProperty("usuarioId_v").getProperty();
        valoracion.addToOne(usuario, usuarioId_v);*/

        //ToMany valoracion-sitio
        ToMany sitioToValoraciones  = usuario.addToMany(valoracion, sitioId_v);
        sitioToValoraciones.setName("Valoraciones_sitio");

        //ToMany valoracion-usuario
        /*ToMany usuarioToValoraciones  = usuario.addToMany(valoracion, usuarioId_v);
        usuarioToValoraciones.setName("Valoraciones_usuario");*/

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        //Tabla Comentarios.
        Entity comentario = schema.addEntity("Comentario");
        comentario.addIdProperty();
        comentario.addStringProperty("objectId");
        comentario.addStringProperty("cuerpo_comentario");
        comentario.addStringProperty("fecha");
        comentario.addStringProperty("updatedAt");


        //relacion usuario-comentario
        Property usuarioId_C = comentario.addLongProperty("usuarioId_c").getProperty();
        comentario.addToOne(usuario, usuarioId_C);

        //relacion sitio-comentario
        Property sitioId_c = comentario.addLongProperty("sitioId_c").getProperty();
        comentario.addToOne(sitio, sitioId_c);


        //ToMany comentario-sitio
        ToMany sitioToComentarios = comentario.addToMany(comentario,usuarioId_C);
        sitioToComentarios.setName("Comentarios_sitio");
        //ToMany comentario-sitio
        ToMany usuarioToComentario = usuario.addToMany(comentario,sitioId_c);
        usuarioToComentario.setName("Comentario_usuario");

        ////////////////////////////////////////////////////////////////////////////////////////////////////


    }
}

