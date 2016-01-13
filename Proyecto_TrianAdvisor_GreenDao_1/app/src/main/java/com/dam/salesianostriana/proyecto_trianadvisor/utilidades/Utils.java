package com.dam.salesianostriana.proyecto_trianadvisor.utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dam.salesianostriana.proyecto_trianadvisor.greendao.DaoMaster;
import com.dam.salesianostriana.proyecto_trianadvisor.greendao.DaoSession;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    public static String encodearCadena(String cadena){
        String cadena_encodeada = "";
        try {
            cadena_encodeada = URLEncoder.encode(cadena, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cadena_encodeada;
    }

    public static boolean comprobarInternet(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        return !(i == null || !i.isConnected() || !i.isAvailable());

    }

    public static DaoSession instanciarBD(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "TrianAdvisorBD", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession;
    }
}
