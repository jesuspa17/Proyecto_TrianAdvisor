package com.dam.salesianostriana.proyecto_trianadvisor.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.dam.salesianostriana.proyecto_trianadvisor.Servicio;
import com.dam.salesianostriana.proyecto_trianadvisor.pojos_RetroFit.usuario.Usuario;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;


public class MiPerfilFragment extends Fragment {

    ImageView imageViewPerfil;
    TextView txtUser, txtNombre, txtGmail;

    public MiPerfilFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mi_perfil, container, false);

        //Rescato los elementos de la interfaz.
        imageViewPerfil = (ImageView) v.findViewById(R.id.imageViewMiPerf);
        txtUser = (TextView) v.findViewById(R.id.textViewUserMiPerf);
        txtNombre =(TextView) v.findViewById(R.id.textViewNombreMiPerfil);
        txtGmail = (TextView) v.findViewById(R.id.textViewGmailMiPerfil);

        //Obtengo el sessionToken que se obtiene a partir del MainActivity.
        String sessionToken = getArguments().getString("sessionToken");
        new ObtenerMiPerfilTask().execute(sessionToken,sessionToken);

        return v;
    }


    private class ObtenerMiPerfilTask extends AsyncTask<String, Void, Usuario> {
        @Override
        protected Usuario doInBackground(String... params) {
            Call<Usuario> usuarioCall = Servicio.instanciarServicio().obtenerMisDatos(params[0],params[1]);
            Response<Usuario> response = null;
            try {
                response =  usuarioCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert response != null;
            return response.body();
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            super.onPostExecute(usuario);

            String url_imagen = usuario.getFoto().getUrl();
            String user = usuario.getUsername();
            String nombre = usuario.getNombre();
            String gmail = usuario.getEmail();

            Picasso.with(getActivity()).load(url_imagen).resize(80, 80).into(imageViewPerfil);
            txtUser.setText(user);
            txtNombre.setText(nombre);
            txtGmail.setText(gmail);

        }
    }



}
