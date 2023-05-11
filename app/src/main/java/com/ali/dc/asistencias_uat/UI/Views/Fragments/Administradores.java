package com.ali.dc.asistencias_uat.UI.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.dc.asistencias_uat.Controller.Adapters.AdministradoresAdapter;
import com.ali.dc.asistencias_uat.Controller.Adapters.AlumnosAdapter;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Inicio;
import com.ali.dc.asistencias_uat.Utilities.Constantes;

import java.util.List;

public class Administradores extends Fragment {

    private RecyclerView adminsRecyclerView;
    private AdministradoresDB adminDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_administradores, container, false);
        Inicio.toolbar.setTitle("Administradores");

        Inicio.fabHome.setVisibility(View.GONE);

        adminsRecyclerView = view.findViewById(R.id.adminsRecyclerView);
        adminsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adminDB = new AdministradoresDB(getContext());
        adminDB.getAll(new VolleyCallback<List<com.ali.dc.asistencias_uat.Models.Administradores>>() {
            @Override
            public void onSuccess(List<com.ali.dc.asistencias_uat.Models.Administradores> administradores) {
                Log.d(Constantes.TAG, administradores.toString());
                adminsRecyclerView.setAdapter(new AdministradoresAdapter(administradores, getContext()));
            }

            @Override
            public void onFailure(String errorMessage, int erroCode) {

            }
        });

        return view;
    }
}