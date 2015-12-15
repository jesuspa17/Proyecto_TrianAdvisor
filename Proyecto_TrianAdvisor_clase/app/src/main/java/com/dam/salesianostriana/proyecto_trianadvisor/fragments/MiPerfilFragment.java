package com.dam.salesianostriana.proyecto_trianadvisor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dam.salesianostriana.proyecto_trianadvisor.R;


public class MiPerfilFragment extends Fragment {

    public MiPerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mi_perfil, container, false);
    }



}
