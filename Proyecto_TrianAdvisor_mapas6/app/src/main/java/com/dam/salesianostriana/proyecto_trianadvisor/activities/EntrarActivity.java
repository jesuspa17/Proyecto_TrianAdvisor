package com.dam.salesianostriana.proyecto_trianadvisor.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class EntrarActivity extends AppCompatActivity {

    Intent i;

    Button btn_login;
    EditText txtUsuario,txtPassword;

    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        //Rescato los elementos de la interfaz
        btn_login = (Button) findViewById(R.id.button_login);
        txtUsuario = (EditText) findViewById(R.id.textViewEmail);
        txtPassword = (EditText) findViewById(R.id.textViewPassword);

        //Inicializo las preferencias.
        prefs = getSharedPreferences("preferencias",MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        //Compruebo que el campo sessionToken no está vacio.
        //1. Si está vacio no hace nada.
        //2. Si contiene el sessionToken, lanza el MainActivity directamente.
        if(prefs.getString("sessionToken",null)!=null){
            i = new Intent(EntrarActivity.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recogo lo que está escrito en los EditText's
                final String us = txtUsuario.getText().toString();
                final String pass = txtPassword.getText().toString();

                //Lanzo el asyntasck.
                new ObtenerUsuarioTask(){
                    @Override
                    protected void onPostExecute(Usuario usuario) {
                        super.onPostExecute(usuario);

                        //Compruebo que el usuario obtenido no sea nulo, es decir, que se ha obtenido algún dato.
                        if(usuario!=null){
                            //Almaceno el sessionToken
                            String sessionToken = usuario.getSessionToken();
                                i = new Intent(EntrarActivity.this,MainActivity.class);
                                //Guardo el sessionToken en las preferencias
                                editor.putString("sessionToken",sessionToken);
                                editor.apply();
                                //Inicio el activity.
                                startActivity(i);
                                EntrarActivity.this.finish();
                        }else{
                            Toast.makeText(EntrarActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                        }

                    }
                }.execute(us, pass);


            }
        });


    }

    //Realiza el Asyntasck del login.
    //Devuelve los datos del usuario a través del usuario y contraseña del mismo.

    //El onPostExecute se realiza en el onCreate.
    private class ObtenerUsuarioTask extends AsyncTask<String, Void, Usuario>{
        @Override
        protected Usuario doInBackground(String... params) {

            if(params!=null){
                Call<Usuario> usuarioCall = Servicio.instanciarServicio().obtenerUsuario(params[0], params[1]);
                Response<Usuario> usuarioResponse = null;

                try {
                    usuarioResponse = usuarioCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert usuarioResponse != null;
                if(usuarioResponse.code() == 200 || usuarioResponse.code() == 201){
                    return usuarioResponse.body();
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }

    }
}
