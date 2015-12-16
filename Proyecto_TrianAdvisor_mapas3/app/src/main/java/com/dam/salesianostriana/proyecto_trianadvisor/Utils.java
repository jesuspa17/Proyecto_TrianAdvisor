package com.dam.salesianostriana.proyecto_trianadvisor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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

    public static BitmapDescriptor obtenerBitmapDescriptor(String imagen){
        BitmapDescriptor bd = null;
        URL url = null;
        Bitmap b;
        try {
            url = new URL(imagen);
            InputStream is = url.openStream();
            b = BitmapFactory.decodeStream(is);
            Bitmap redim = Bitmap.createScaledBitmap(b, b.getWidth() / 2, b.getHeight() / 2, false);
            bd = BitmapDescriptorFactory.fromBitmap(redim);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bd;
    }


}
