package com.ali.dc.asistencias_uat.Views.Dialogs;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

public class EditarPerfil extends AppCompatDialogFragment {

    private TextInputLayout etMatriculaLyt, etNombreLyt;
    private TextInputEditText etMatricula, etNombre;
    private AdministradoresDB adminDB;
    String uId;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        adminDB = new AdministradoresDB(getContext());
        FirebaseUser user = Firebase.firebaseAuth.getCurrentUser();
        uId = user.getUid();

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editar_perfil, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_emoji_people)
                .setTitle("Editar perfil")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Actualizar perfil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
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
            public void onFailure(String errorMessage, int erroCode) {}
        });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog d = (AlertDialog) getDialog();
        if(d != null) {
            Button positiveButton = (Button) d.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adminDB.getByFirebaseId(uId, new VolleyCallback<Administradores>() {
                        @Override
                        public void onSuccess(Administradores administradores) {
                            updateAdmin(v, administradores);
                        }

                        @Override
                        public void onFailure(String errorMessage, int erroCode) {}
                    });
                }
            });
        }
    }

    private void updateAdmin(View view, Administradores administradores) {
        if (validarCampos()){
            Administradores administradoresReq = new Administradores();
            administradoresReq.setId_admin(administradores.getId_admin());
            administradoresReq.setNombre(etNombre.getText().toString());
            administradoresReq.setNum_empleado(etMatricula.getText().toString());
            adminDB.update(administradoresReq, new VolleyCallback<Administradores>() {
                @Override
                public void onSuccess(Administradores administradores) {
                    MetodosVistas.snackBar(getActivity(), "Administrador actualizado con exito.");
                    dismiss();
                }
                @Override
                public void onFailure(String errorMessage, int erroCode) {
                    MetodosVistas.snackBar(getActivity(), errorMessage);
                }
            });
        }
    }

    public Boolean validarCampos() {
        Boolean band = true;

        if (etMatricula.getText().toString().isEmpty()) {
            etMatriculaLyt.setError("Número de empleado necesario");
            band = false;
        } else {
            etMatriculaLyt.setError(null);
        }

        if (etNombre.getText().toString().isEmpty()) {
            etNombreLyt.setError("Nombre del docente necesario");
            band = false;
        } else {
            etNombreLyt.setError(null);
        }

        return band;
    }

}
