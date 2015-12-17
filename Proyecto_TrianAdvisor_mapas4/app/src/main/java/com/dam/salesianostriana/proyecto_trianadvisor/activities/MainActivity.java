package com.dam.salesianostriana.proyecto_trianadvisor.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.fragments.MiPerfilFragment;
import com.dam.salesianostriana.proyecto_trianadvisor.fragments.SitiosFragment;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences prefs;

    ImageView imageViewUsuario;
    TextView txtUsuarioNav;

    //Almacenará el sessionToken obtenido de las preferencias.
    String sessionToken;

    //En estas variables almacenaré los datos que mostraré en el fragment de mi perfil.
    //Lo haré de esta manera para no hacer otra consulta en el fragment.
    String username;
    String nombre;
    String gmail;
    String imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Rescato los elementos del navigationDrawer.
        View cabecera = navigationView.getHeaderView(0);
        imageViewUsuario = (ImageView) cabecera.findViewById(R.id.imageViewNavUsuario);
        txtUsuarioNav = (TextView) cabecera.findViewById(R.id.textViewNavUsuario);

        //Inicializo el fragment de los sitios al iniciar el activity.
        Fragment f = new SitiosFragment();
        transicionFragment(f);

        //Se obtiene la sessionToken que hay guardada  en las preferencias
        prefs = getSharedPreferences("preferencias", MODE_PRIVATE);
        sessionToken = prefs.getString("sessionToken", null);
        Log.i("SESSION_TOKEN_OBTENIDA", sessionToken);

        //Se realiza el asyntasck que devuelve mis datos a través del sessionToken
        new ObtenerMisDatosTask().execute(sessionToken, sessionToken);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Eventos asociados a cada uno de los botones del navigation.

        int id = item.getItemId();
        Fragment f = null;

        //Abre la lista de todos los bares
        if (id == R.id.action_bares) {

            f = new SitiosFragment();

            //Abre el MapsActivity
        } else if (id == R.id.nav_cerca_mi) {

            Intent i = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(i);

            //Abre el fragment que muestra los datos de mi perfil.
        } else if (id == R.id.nav_mi_perfil) {

            //Se la pasa al fragment los datos del usuario.
            f = new MiPerfilFragment();

            Bundle extras = new Bundle();
            extras.putString("username", username);
            extras.putString("nombre", nombre);
            extras.putString("gmail", gmail);
            extras.putString("imagen", imagen);

            f.setArguments(extras);

            //Cierra el activity y vacia las preferencias.
        } else if (id == R.id.nav_cerrar) {

            new CerrarSesionTask().execute();
            Intent i = new Intent(MainActivity.this, EntrarActivity.class);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            startActivity(i);
            this.finish();
        }

        if (f != null) {
            transicionFragment(f);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void transicionFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor, f).commit();
    }

    //Obtiene mis datos a partir del sessionToken dado.
    private class ObtenerMisDatosTask extends AsyncTask<String, Void, Usuario> {
        @Override
        protected Usuario doInBackground(String... params) {

            if (params != null) {
                Call<Usuario> usuarioCall = Servicio.instanciarServicio().obtenerMisDatos(params[0], params[1]);
                Response<Usuario> response = null;
                try {
                    response = usuarioCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert response != null;
                return response.body();
            } else {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            super.onPostExecute(usuario);

            if (usuario != null) {

                imagen = usuario.getFoto().getUrl();
                username = usuario.getUsername();
                nombre = usuario.getNombre();
                gmail = usuario.getEmail();

                Picasso.with(MainActivity.this).load(imagen).placeholder(R.drawable.user).resize(100, 100).into(imageViewUsuario);
                txtUsuarioNav.setText(username);

                Log.i("NOMBRE USUARIO", "NOMBRE_USUARIO: " + usuario.getNombre());
            }
        }
    }

    private class CerrarSesionTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Call<Usuario> usuarioCall = Servicio.instanciarServicio().cerrarSesion(sessionToken, sessionToken);
            try {
                usuarioCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        }
    }
}
