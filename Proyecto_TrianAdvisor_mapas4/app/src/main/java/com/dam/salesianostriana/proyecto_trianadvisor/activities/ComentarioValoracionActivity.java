package com.dam.salesianostriana.proyecto_trianadvisor.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.NuevoComentario;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Sitio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.valoraciones.Usuario;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ComentarioValoracionActivity extends AppCompatActivity {

    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario_valoracion);

        enviar = (Button) findViewById(R.id.btn_enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario("yE3EafUg3p");
                Sitio sitio = new Sitio("4bwbPRgAIJ");
                NuevoComentario nuevoComentario = new NuevoComentario(sitio, usuario,"Este bar es potente");

                Call<NuevoComentario> nuevoComentarioCall = Servicio.instanciarServicio().almacenarComentario(nuevoComentario);

                nuevoComentarioCall.enqueue(new Callback<NuevoComentario>() {
                    @Override
                    public void onResponse(Response<NuevoComentario> response, Retrofit retrofit) {
                        Toast.makeText(ComentarioValoracionActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(ComentarioValoracionActivity.this, "Error al insertar los datos", Toast.LENGTH_SHORT).show();
                    }
                });
                //new AlmacenarComentario().execute(nuevoComentario);
            }
        });
    }

    private class AlmacenarComentario extends AsyncTask<NuevoComentario, Void, Void>{

        @Override
        protected Void doInBackground(NuevoComentario... params) {
            Call<NuevoComentario> nuevoComentarioCall = Servicio.instanciarServicio().almacenarComentario(params[0]);
            Response<NuevoComentario> nuevoComentarioResponse = null;
            try {
                nuevoComentarioResponse = nuevoComentarioCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(ComentarioValoracionActivity.this,"Su valoración ha sido almacenada",Toast.LENGTH_SHORT).show();
        }
    }
}
