package com.dam.salesianostriana.proyecto_trianadvisor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.salesianostriana.proyecto_trianadvisor.R;
import com.squareup.picasso.Picasso;


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

        Bundle extras = getArguments();

        if(extras!=null){

            String username = extras.getString("username");
            String nombre = extras.getString("nombre");
            String gmail = extras.getString("gmail");
            String imagen = extras.getString("imagen");

            Picasso.with(getActivity()).load(imagen).resize(80, 80).into(imageViewPerfil);
            txtUser.setText(username);
            txtNombre.setText(nombre);
            txtGmail.setText(gmail);

        }

        return v;
    }

}
