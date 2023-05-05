package com.ali.dc.asistencias_uat.Views.Pantallas.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ali.dc.asistencias_uat.Controller.Firebase.FirestoreCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase.MetodosFirebase;
import com.ali.dc.asistencias_uat.DataBase.UsersFirebase;
import com.ali.dc.asistencias_uat.Models.Users;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Views.Pantallas.Login;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

public class RestablecerContrasenna extends AppCompatDialogFragment {

    private TextInputLayout etMailLyt;
    private TextInputEditText etMail;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_restablecer_pass, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_password)
                .setTitle("Restablecer Contraseña")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MetodosFirebase.resetPassword(getActivity(), etMail.getText().toString());
                    }
                });

        etMailLyt= view.findViewById(R.id.etMailLyt);
        etMail = view.findViewById(R.id.etMail);

        FirebaseUser user = MetodosFirebase.firebaseAuth.getCurrentUser();

        return builder.create();
    }
}
