package com.dam.salesianostriana.proyecto_trianadvisor.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.utilidades.Utils;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.insert.ActuValoracion;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.insert.Actualizado;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.insert.NuevaValoracion;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.insert.NuevoComentario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.insert.ParaActualizar;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Usuario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Valoracion;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class ComentarioValoracionActivity extends AppCompatActivity {

    Button enviar;
    EditText editNuevoComentario;
    RatingBar rbNuevaValoracion;

    SharedPreferences prefs;
    String sessionToken;
    String obj_id_usuario;
    String obj_id_sitio;

    Usuario usuario;
    Sitio sitio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario_valoracion);

        //Rescato los elementos de la interfaz.
        enviar = (Button) findViewById(R.id.btn_enviar);
        editNuevoComentario = (EditText) findViewById(R.id.editTextNuevoComentario);
        rbNuevaValoracion = (RatingBar) findViewById(R.id.ratingBarInsertValoracion);

        //Se recoje el objectId del sitio a comentar obtenido
        //del otro activity.
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            obj_id_sitio = bundle.getString("object_id");
        }

        //Se abren las preferencias de donde extraeré el sessionToken del usuario.
        prefs = getSharedPreferences("preferencias", MODE_PRIVATE);
        sessionToken = prefs.getString("sessionToken", null);
        Log.i("VAL_SESSION_TOKEN", sessionToken);

        //Ejecuto el asyntasck que me devuelve los datos del usuario en función
        //del sessiónToken
        new ObtenerMisDatosTask().execute(sessionToken);

        //Funciones asociadas al ratingBar:
        rbNuevaValoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {

                //Se crean los objetos que tendrá el cuerpo de la petición para la valoración.
                usuario = new Usuario("Pointer", "_User", obj_id_usuario);
                sitio = new Sitio("Pointer", "sitio", obj_id_sitio);

                //Almaceno la valoración del rating.
                float valoracion = rating;

                Log.i("VAL_OBJECT_ID_USER", "" + usuario.getObjectId());
                Log.i("VAL_OBJECT_ID_SITIO", "" + sitio.getObjectId());

                //Este objeto solo se usará en el caso de que el sitio no estuviese valorado.
                final NuevaValoracion nueva_val = new NuevaValoracion();
                nueva_val.setSitio(sitio);
                nueva_val.setUsuario(usuario);
                nueva_val.setValoracion(valoracion);

                //JSON que hará de consulta para comprobar si se ha valorado un sitio.
                //Esta cadena se le pasará encodeada al asyntasck que se ejecuta en la siguiente linea.
                String JSON_BASE = "{\"sitio\": { \"__type\": \"Pointer\", \"className\": \"sitio\", \"objectId\": \"" + obj_id_sitio + "\" }, \"usuario\": {\"__type\": \"Pointer\", \"className\": \"_User\", \"objectId\": \"" + obj_id_usuario + "\"} }";

                new ComprobarSiHeValorado() {

                    @Override
                    protected void onPostExecute(Valoracion valoracion) {
                        super.onPostExecute(valoracion);

                        //Comprueba el resultado de la lista obtenida:

                        //1. Si no está vacia, querrá decir que el usuario ya ha valorado el sitio
                        //por lo tanto se actualizará su valoración.

                        //2. Si lo está, querrá decir que el usuario todavía no ha valorado el sitio
                        //por lo que se lanzará el asyntask que almacena una nueva valoración.

                        if (valoracion.getResults().size() != 0) {

                            //Se instancia el objeto necesario para actualizar la valoración.
                            ActuValoracion actuValoracion = new ActuValoracion();
                            actuValoracion.setValoracion(rating);

                            //Se instancia el objeto que se le debe pasar al asynasck que contendrá
                            //el la clase pojo con la nueva valoración y el objectId de la valoración.
                            Log.i("VAL_ID_VALORACION", valoracion.getResults().get(0).getObjectId());

                            ParaActualizar paraActualizar = new ParaActualizar();
                            paraActualizar.setActuValoracion(actuValoracion);
                            paraActualizar.setObjectId(valoracion.getResults().get(0).getObjectId());

                            //Se lanza el asynasck que actualiza la valoración.
                            new ActualizarValoracion().execute(paraActualizar);

                        } else {
                            //Se lanza el asynask que almacena la valoración.
                            new AlmacenarValoracion().execute(nueva_val);
                        }
                    }

                }.execute(Utils.encodearCadena(JSON_BASE));

            }
        });

        //Funciones asociadas al botón enviar.
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se crean los objetos que tendrá el cuerpo de la petición para almacenar el comentario..
                usuario = new Usuario("Pointer", "_User", obj_id_usuario);
                sitio = new Sitio("Pointer", "sitio", obj_id_sitio);
                String contenido_comentario = editNuevoComentario.getText().toString();

                if (!contenido_comentario.isEmpty()) {
                    //Se crea el cuerpo cuerpo de la petición, con los objetos creados anteriormente.
                    NuevoComentario nuevoComentario = new NuevoComentario();
                    nuevoComentario.setSitio(sitio);
                    nuevoComentario.setUsuario(usuario);
                    nuevoComentario.setComentario(contenido_comentario);

                    //Se lanza el asynasck que envia el nuevo comentario.
                    new AlmacenarComentario().execute(nuevoComentario);

                } else {
                    Toast.makeText(ComentarioValoracionActivity.this, "Debe rellenar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //AsyncTask que se encarga de ACTUALIZAR una valoración dado el cuerpo de la misma y el objectId de la valoración
    //La clase "ParaActualizar" la cual se pasa como parámetro se usa
    //para pasarle los parámetros necesarios al servicio.
    private class ActualizarValoracion extends AsyncTask<ParaActualizar, Void, Actualizado> {
        @Override
        protected Actualizado doInBackground(ParaActualizar... params) {

            if (params != null) {
                ActuValoracion actuValoracion = params[0].getActuValoracion();
                String object = params[0].getObjectId();
                Call<Actualizado> call = Servicio.instanciarServicio().actualizarValoracion(actuValoracion, object);

                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Actualizado actualizado) {
            super.onPostExecute(actualizado);

            Log.i("VALORACIÓN ACTUALIZADA", "VALORACIÓN ACTUALIZADA");
        }
    }

    //AsyncTask que comprueba si se ha valorado un sitio.
    //El onPostExecute de este asyntask se realiza en en setOnRatingBarChangeListener.
    private class ComprobarSiHeValorado extends AsyncTask<String, Void, Valoracion> {
        @Override
        protected Valoracion doInBackground(String... params) {
            if (params != null) {

                Call<Valoracion> nuevaValoracionCall = Servicio.instanciarServicio().comprobarSiSeHaValorado(params[0]);
                Response<Valoracion> res = null;

                try {
                    res = nuevaValoracionCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert res != null;
                if (res.code() == 200 || res.code() == 201) {
                    return res.body();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    //AsyncTask que realiza el servicio de ALMACENAR una NUEVA valoración de un sitio.
    private class AlmacenarValoracion extends AsyncTask<NuevaValoracion, Void, Void> {
        @Override
        protected Void doInBackground(NuevaValoracion... params) {
            if (params != null) {
                Call<NuevaValoracion> call = Servicio.instanciarServicio().almacenarValoracion(params[0]);
                Response<NuevaValoracion> res = null;
                try {
                    res = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            } else {
                return null;
            }
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("VALORACIÓN ALMACENADA","VALORACIÓN ALMACENADA");
        }
    }

    //AsyncTask que realiza el servicio de almacenar un nuevo comentario de un sitio.
    private class AlmacenarComentario extends AsyncTask<NuevoComentario, Void, String> {

        @Override
        protected String doInBackground(NuevoComentario... params) {

            String msg_ok = "Su opinión ha sido almacenada";
            String msg_error = "Error interno, estamos intentando solucionarlo";
            Log.i("COMENTARIO_ASYNTASCK", params[0].getComentario());

            Call<NuevoComentario> nuevoComentarioCall = Servicio.instanciarServicio().almacenarComentario(params[0]);
            Response<NuevoComentario> nuevoComentarioResponse = null;
            try {
                nuevoComentarioResponse = nuevoComentarioCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert nuevoComentarioResponse != null;
            if (nuevoComentarioResponse.code() == 200 || nuevoComentarioResponse.code() == 201) {
                return msg_ok;
            } else {
                return msg_error;
            }

        }

        @Override
        protected void onPostExecute(String msg) {
            super.onPostExecute(msg);
            Intent i = new Intent(ComentarioValoracionActivity.this, DetalleActivity.class);
            i.putExtra("object_id", obj_id_sitio);
            startActivity(i);
            ComentarioValoracionActivity.this.finish();
            Toast.makeText(ComentarioValoracionActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    //AsyncTask que obtiene los datos del usuario a través de su sessionToken.
    private class ObtenerMisDatosTask extends AsyncTask<String, Void, com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario> {
        @Override
        protected com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario doInBackground(String... params) {

            if (params != null) {
                Call<com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario> usuarioCall = Servicio.instanciarServicio().obtenerMisDatos(params[0]);
                Response<com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario> response = null;
                try {
                    response = usuarioCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert response != null;

                if (response.code() == 200 || response.code() == 201) {
                    return response.body();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario usuario) {
            super.onPostExecute(usuario);
            if (usuario != null) {
                obj_id_usuario = usuario.getObjectId();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ComentarioValoracionActivity.this, DetalleActivity.class);
        i.putExtra("object_id", obj_id_sitio);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
