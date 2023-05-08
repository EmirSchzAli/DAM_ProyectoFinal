package com.ali.dc.asistencias_uat.UI.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.dc.asistencias_uat.Controller.Adapters.AlumnosAdapter;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Inicio;

import org.json.JSONArray;

import java.util.List;


public class Alumnos extends Fragment {

    private AlumnosDB alumnoDB;
    private RecyclerView alumnosRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alumnos, container, false);
        Inicio.toolbar.setTitle(R.string.fragment_alumnos_label);

        alumnoDB = new AlumnosDB(getContext());

        alumnosRecyclerView = view.findViewById(R.id.alumnosRecyclerView);
        alumnosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        alumnoDB.getAll(new VolleyCallback<List<com.ali.dc.asistencias_uat.Models.Alumnos>>() {
            @Override
            public void onSuccess(List<com.ali.dc.asistencias_uat.Models.Alumnos> alumnos) {
                alumnosRecyclerView.setAdapter(new AlumnosAdapter(alumnos, getContext()));
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

        return view;
    }
}