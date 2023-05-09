package com.ali.dc.asistencias_uat.UI.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.ali.dc.asistencias_uat.UI.Views.Dialogs.EditarPerfil;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Inicio;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Login;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;


public class Home extends Fragment {

    MaterialCardView editUserCard;
    private TextView tvName, tvKindUser, tvMail, tvMatricula;
    private AdministradoresDB adminDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Inicio.toolbar.setTitle(R.string.fragment_home_label);

        tvName = view.findViewById(R.id.tvName);
        tvKindUser = view.findViewById(R.id.tvKindUser);
        tvMail = view.findViewById(R.id.tvMail);
        tvMatricula = view.findViewById(R.id.tvMatricula);
        editUserCard = view.findViewById(R.id.editUserCard);

        FirebaseUser user = Firebase.firebaseAuth.getCurrentUser();
        String uId = user.getUid();

        adminDB = new AdministradoresDB(getContext());

        adminDB.getByFirebaseId(uId, new VolleyCallback<Administradores>() {
            @Override
            public void onSuccess(Administradores administrador) {
                printDatas(administrador);
            }

            @Override
            public void onFailure(String errorMessage) {}
        });

        editUserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarPerfil editarPerfilDialog = new EditarPerfil();
                editarPerfilDialog.setCancelable(false);
                editarPerfilDialog.show(getActivity().getSupportFragmentManager(), "loginDialog");
            }
        });

        return view;

    }

    private void printDatas(Administradores administrador) {
        int tipoUsuario = administrador.getId_tipoAdmin();
        tvName.setText(administrador.getNombre());
        tvMail.setText(administrador.getCorreo());
        tvMatricula.setText(administrador.getNum_empleado());

        if (tipoUsuario == 1) {
            tvKindUser.setText("Administrador");
        } else if (tipoUsuario == 2) {
            tvKindUser.setText("Secretario");
        } else if (tipoUsuario == 3) {
            tvKindUser.setText("Checador");
        }
    }

}