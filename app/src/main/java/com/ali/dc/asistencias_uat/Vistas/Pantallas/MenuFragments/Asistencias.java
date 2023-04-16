package com.ali.dc.asistencias_uat.Vistas.Pantallas.MenuFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Vistas.Pantallas.Inicio;

public class Asistencias extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asistencias, container, false);
        Inicio.toolbar.setTitle(R.string.fragment_asistencias_label);
        return view;
    }

}