package com.dam.salesianostriana.proyecto_trianadvisor.utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.dam.salesianostriana.proyecto_trianadvisor.greendao.DaoMaster;
import com.dam.salesianostriana.proyecto_trianadvisor.greendao.DaoSession;
import com.dam.salesianostriana.proyecto_trianadvisor.greendao.Valoracion;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public static float calcularMediaValoracionesGreenDao(List<Valoracion> lista_valoraciones){

        float total_valoraciones = 0;
        for (int x = 0; x < lista_valoraciones.size(); x++) {
            total_valoraciones += Float.parseFloat(lista_valoraciones.get(x).getValoracion());
        }
        if(lista_valoraciones.size()!=0){
            total_valoraciones = total_valoraciones / lista_valoraciones.size();
        }
        return total_valoraciones;
    }


    public static String obtenerFechaSistema() {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, 1);
        Date date = cal.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat(Utils.FORMATO_FECHA);
        Log.i("FECHA_FORMATEADA",format1.format(date));
        return format1.format(date);
    }

}
