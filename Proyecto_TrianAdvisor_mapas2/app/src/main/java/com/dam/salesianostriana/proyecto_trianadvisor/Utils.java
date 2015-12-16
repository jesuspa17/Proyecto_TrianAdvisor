package com.dam.salesianostriana.proyecto_trianadvisor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jesús Pallares on 16/12/2015.
 */
public class Utils {

    public static String FORMATO_FECHA = "yyyy-MM-dd'T'HH:mm:ss";

    public static String formatearFechaString(String formato, String fecha) {
        //formato de entrada
        SimpleDateFormat f = new SimpleDateFormat(formato);
        //formato de salida
        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
        String fecha_formateada = "";
        try {
            Date date = f.parse(fecha);
            fecha_formateada = f1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fecha_formateada;
    }


}
