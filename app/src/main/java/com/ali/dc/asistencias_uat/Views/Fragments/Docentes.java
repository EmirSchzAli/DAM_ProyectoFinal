package com.ali.dc.asistencias_uat.Views.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ali.dc.asistencias_uat.Controller.Adapters.DocentesAdapter;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.DocentesDB;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.ali.dc.asistencias_uat.Views.Dialogs.AgregarDocente;
import com.ali.dc.asistencias_uat.Views.Screens.Inicio;
import com.ali.dc.asistencias_uat.Utilities.Constantes;

import java.util.List;

public class Docentes extends Fragment {

    private RecyclerView docentesRecyclerView;
    private DocentesDB docentesDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_docentes, container, false);
        Inicio.toolbar.setTitle(R.string.fragment_docentes_label);
        setHasOptionsMenu(true);

        Inicio.fabHome.setVisibility(View.VISIBLE);
        Inicio.fabHome.setImageResource(R.drawable.outline_add);
        Inicio.fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDocente();
            }
        });

        docentesRecyclerView = view.findViewById(R.id.docentesRecyclerView);
        docentesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        docentesDB = new DocentesDB(getContext());

        docentesDB.getAll(new VolleyCallback<List<com.ali.dc.asistencias_uat.Models.Docentes>>() {
            @Override
            public void onSuccess(List<com.ali.dc.asistencias_uat.Models.Docentes> docentes) {
                Log.d(Constantes.TAG, docentes.toString());
                docentesRecyclerView.setAdapter(new DocentesAdapter(docentes, getContext()));
            }
            @Override
            public void onFailure(String errorMessage, int erroCode) {
                MetodosVistas.snackBar((Activity) view.getContext(), errorMessage);
            }
        });

        return view;
    }

    private void addDocente() {
        AgregarDocente agregarDocente = new AgregarDocente();
        agregarDocente.setCancelable(false);
        agregarDocente.show(getActivity().getSupportFragmentManager(), "addTeachDialog");
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_inicio, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.actualizar:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new Docentes());
                transaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}