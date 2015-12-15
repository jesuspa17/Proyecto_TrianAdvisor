package com.dam.salesianostriana.proyecto_trianadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class EntrarActivity extends AppCompatActivity {

    Button btn_login;
    EditText txtUsuario,txtPassword;

    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        btn_login = (Button) findViewById(R.id.button_login);
        txtUsuario = (EditText) findViewById(R.id.textViewEmail);
        txtPassword = (EditText) findViewById(R.id.textViewPassword);

        prefs = getSharedPreferences("preferencias",MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String us = txtUsuario.getText().toString();
                final String pass = txtPassword.getText().toString();

                new ObtenerUsuarioTask(){
                    @Override
                    protected void onPostExecute(Usuario usuario) {
                        super.onPostExecute(usuario);

                        if(usuario!=null){
                            String username = usuario.getUsername();
                            String sessionToken = usuario.getSessionToken();

                            if(username.equals(us) && pass.equals("12345")){
                                Intent i = new Intent(EntrarActivity.this,MainActivity.class);

                                editor.putString("sessionToken",sessionToken);
                                editor.apply();

                                startActivity(i);
                                EntrarActivity.this.finish();
                            }else{
                                Toast.makeText(EntrarActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(EntrarActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                        }

                    }
                }.execute(us, pass);


            }
        });


    }


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
                return usuarioResponse.body();
            }else{
                return null;
            }
        }

    }
}
