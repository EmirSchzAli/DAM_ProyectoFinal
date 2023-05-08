package com.ali.dc.asistencias_uat.UI.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.ali.dc.asistencias_uat.UI.Views.Fragments.Home;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;

public class EditarPerfil extends AppCompatDialogFragment {

    private TextInputLayout etMatriculaLyt, etNombreLyt;
    private TextInputEditText etMatricula, etNombre;
    private AdministradoresDB adminDB;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        adminDB = new AdministradoresDB(getContext());
        FirebaseUser user = Firebase.firebaseAuth.getCurrentUser();
        String uId = user.getUid();

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editar_perfil, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_emoji_people)
                .setTitle("Editar perfil")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        adminDB.getByFirebaseId(uId, new VolleyCallback<Administradores>() {
                            @Override
                            public void onSuccess(Administradores administradores) {
                                updateAdmin(view, administradores);
                            }

                            @Override
                            public void onFailure(String errorMessage) {}
                        });
                    }
                });

        etMatriculaLyt = view.findViewById(R.id.etMatriculaLyt);
        etMatricula = view.findViewById(R.id.etMatricula);
        etNombreLyt= view.findViewById(R.id.etUserNameLyt);
        etNombre = view.findViewById(R.id.etUserName);

        adminDB.getByFirebaseId(uId, new VolleyCallback<Administradores>() {
            @Override
            public void onSuccess(Administradores administradores) {
                etMatricula.setText(administradores.getNum_empleado());
                etNombre.setText(administradores.getNombre());
            }

            @Override
            public void onFailure(String errorMessage) {}
        });

        return builder.create();
    }

    private void updateAdmin(View view, Administradores administradores) {
        Administradores administradoresReq = new Administradores();
        administradoresReq.setId_admin(administradores.getId_admin());
        administradoresReq.setNombre(etNombre.getText().toString());
        administradoresReq.setNum_empleado(etMatricula.getText().toString());
        adminDB.update(administradoresReq, new VolleyCallback<Administradores>() {
            @Override
            public void onSuccess(Administradores administradores) {
                MetodosVistas.snackBar((Activity) view.getContext(), "Administrador actualizado con exito.");
            }

            @Override
            public void onFailure(String errorMessage) {
                MetodosVistas.snackBar((Activity) view.getContext(), errorMessage);
            }
        });
    }

}
