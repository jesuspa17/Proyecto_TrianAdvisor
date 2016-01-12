package com.example;

import java.nio.file.Files;
import java.nio.file.Paths;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

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
        //preguntar si hay que hacerlo con una clase aparte.
        usuario.addStringProperty("url_foto");
        usuario.addStringProperty("sessionToken");
        usuario.addStringProperty("updatedAt"); // ????

        //Tabla Sitios.
        Entity sitio = schema.addEntity("Sitio");
        sitio.addStringProperty("objectId");
        sitio.addStringProperty("nombre");
        sitio.addStringProperty("direccion");
        sitio.addStringProperty("telefono");
        sitio.addStringProperty("descripcion");
        sitio.addStringProperty("categoria");
        //preguntar si hay que hacerlo con una clase aparte.
        sitio.addStringProperty("coordenadas");
        sitio.addStringProperty("url_foto");
        sitio.addStringProperty("updatedAt");


        //Tabla Valoraciones.
        Entity valoracion = schema.addEntity("Valoracion");
        valoracion.addStringProperty("objectId");
        valoracion.addDoubleProperty("valoracion");

        //updateAt?
        //añadir sitio a la tabla.


        //Tabla Comentarios.
        Entity comentario = schema.addEntity("Comentario");
        comentario.addStringProperty("objectId");
        comentario.addStringProperty("cuerpo_comentario");
        comentario.addStringProperty("fecha");
        //updateAt?
        //añadir sitio a la tabla.
        //añadir usuario a la tabla.
        
        //Tabla alimento
        Entity alimento = schema.addEntity("Alimento");
        alimento.addIdProperty().autoincrement();
        alimento.addStringProperty("nombre").notNull();

        //Tabla caja
        Entity caja = schema.addEntity("Caja");
        caja.addIdProperty().autoincrement();
        caja.addIntProperty("numero").notNull();

        //Tabla alimCaja
        Entity alimCaja = schema.addEntity("AlimCaja");
        alimCaja.addIntProperty("cantidad").notNull();

        Property alimento_id = alimCaja.addLongProperty("alimento_id").notNull().getProperty();
        alimCaja.addToOne(alimento, alimento_id);

        Property caja_id = alimCaja.addLongProperty("caja_id").notNull().getProperty();
        alimCaja.addToOne(caja, caja_id);

    }
}

