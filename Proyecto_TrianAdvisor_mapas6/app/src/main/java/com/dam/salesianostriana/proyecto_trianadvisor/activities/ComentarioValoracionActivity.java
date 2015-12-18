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
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.NuevaValoracion;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.NuevoComentario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Usuario;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario_valoracion);


        enviar = (Button) findViewById(R.id.btn_enviar);
        editNuevoComentario = (EditText) findViewById(R.id.editTextNuevoComentario);
        rbNuevaValoracion = (RatingBar) findViewById(R.id.ratingBarInsertValoracion);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            obj_id_sitio = bundle.getString("object_id");
        }

        prefs = getSharedPreferences("preferencias",MODE_PRIVATE);
        sessionToken = prefs.getString("sessionToken", null);

        Log.i("VAL_SESSION_TOKEN", sessionToken);
        Log.i("VAL_OBJECT_ID_SITIO", obj_id_sitio);

        new ObtenerMisDatosTask().execute(sessionToken);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario usuario = new Usuario("Pointer","_User",obj_id_usuario);
                Log.i("VAL_OBJECT_ID_USER",""+usuario.getObjectId());
                Sitio sitio = new Sitio("Pointer","sitio",obj_id_sitio);
                String contenido_comentario = editNuevoComentario.getText().toString();

                if(!contenido_comentario.isEmpty()){

                    NuevoComentario nuevoComentario = new NuevoComentario();
                    nuevoComentario.setSitio(sitio);
                    nuevoComentario.setUsuario(usuario);
                    nuevoComentario.setComentario(contenido_comentario);

                    Log.i("COMENTARIO_ENVIAR", nuevoComentario.getComentario());
                    new AlmacenarComentario().execute(nuevoComentario);

                }else{
                    Toast.makeText(ComentarioValoracionActivity.this,"Debe rellenar los campos",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private class AlmacenarComentario extends AsyncTask<NuevoComentario, Void, String>{

        @Override
        protected String doInBackground(NuevoComentario... params) {

            String msg_ok = "Su valoración ha sido almacenada";
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
            if(nuevoComentarioResponse.code() == 200 || nuevoComentarioResponse.code() == 201){
                return  msg_ok;
            }else{
                return msg_error;
            }

        }

        @Override
        protected void onPostExecute(String msg) {
            super.onPostExecute(msg);
            Intent i = new Intent(ComentarioValoracionActivity.this,DetalleActivity.class);
            i.putExtra("object_id",obj_id_sitio);
            startActivity(i);
            ComentarioValoracionActivity.this.finish();
            Toast.makeText(ComentarioValoracionActivity.this,"Su valoración ha sido almacenada",Toast.LENGTH_SHORT).show();
        }
    }

    //
    private class ComprobarSiHeValorado extends AsyncTask<Void, Void, NuevaValoracion>{

        @Override
        protected NuevaValoracion doInBackground(Void... params) {
            return null;
        }
    }


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

            }else{
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


}
