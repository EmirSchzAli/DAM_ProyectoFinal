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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

public class EditarPerfil extends AppCompatDialogFragment {

    private TextInputLayout etMatriculaLyt, etNombreLyt;
    private TextInputEditText etMatricula, etNombre;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
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
                    public void onClick(DialogInterface dialog, int which) {}
                });

        etMatriculaLyt = view.findViewById(R.id.etMatriculaLyt);
        etMatricula = view.findViewById(R.id.etMatricula);
        etNombreLyt= view.findViewById(R.id.etUserNameLyt);
        etNombre = view.findViewById(R.id.etUserName);

        FirebaseUser user = MetodosFirebase.firebaseAuth.getCurrentUser();
        String uId = user.getUid();

        UsersFirebase.getByUId(uId, new FirestoreCallback<Users>() {
            @Override
            public void onCallback(Users userCallback) {
                etMatricula.setText(String.valueOf(userCallback.getMatricula()));
                etNombre.setText(userCallback.getNombre());
            }
            @Override
            public void onFailure(Exception e) {}
        });

        return builder.create();
    }
}
