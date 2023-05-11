package com.ali.dc.asistencias_uat.UI.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Views.Dialogs.EditarPerfil;
import com.ali.dc.asistencias_uat.UI.Views.Dialogs.RestablecerContrasenna;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Inicio;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseUser;


public class Home extends Fragment {

    private MaterialCardView editUserCard, updatePassword, updateMail;
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
        updatePassword = view.findViewById(R.id.updatePasswordCard);
        updateMail = view.findViewById(R.id.updateMailCard);

        Inicio.fabHome.setVisibility(View.GONE);

        FirebaseUser user = Firebase.firebaseAuth.getCurrentUser();
        String uId = user.getUid();

        adminDB = new AdministradoresDB(getContext());

        adminDB.getByFirebaseId(uId, new VolleyCallback<Administradores>() {
            @Override
            public void onSuccess(Administradores administrador) {
                printDatas(administrador);
            }

            @Override
            public void onFailure(String errorMessage, int erroCode) {}
        });

        editUserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarPerfil editarPerfilDialog = new EditarPerfil();
                editarPerfilDialog.setCancelable(false);
                editarPerfilDialog.show(getActivity().getSupportFragmentManager(), "loginDialog");
            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestablecerContrasenna restablecerContrasennaDialog = new RestablecerContrasenna();
                restablecerContrasennaDialog.setCancelable(false);
                restablecerContrasennaDialog.show(getActivity().getSupportFragmentManager(), "passwordDialog");
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