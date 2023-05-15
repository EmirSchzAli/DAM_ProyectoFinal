package com.ali.dc.asistencias_uat.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ContrasenaOlvidada extends AppCompatDialogFragment {

    TextInputLayout etMailForgotPasswordLyt;
    TextInputEditText etMailForgotPassword;
    Button btnForgotPassword;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_contrsena_olvidada, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_password)
                .setTitle("Restablecer contraseña")
                .setPositiveButton("Enviar solicitud", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setNegativeButton("Cancelar", null);

        etMailForgotPasswordLyt = view.findViewById(R.id.etMailForgotPassLyt);
        etMailForgotPassword = view.findViewById(R.id.etMailForgotPass);

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
                    if (validarCampos()){
                        Firebase.resetPassword(etMailForgotPassword.getText().toString(), new BooleanCallback() {
                            @Override
                            public void onResponse(boolean value, String message) {
                                if (value) {
                                    MetodosVistas.snackBar(d.getOwnerActivity(), "Solicitud para restablecer su contraseña se ha enviado. Por favor, revisa tu bade de entrada.");
                                } else {
                                    MetodosVistas.snackBar(d.getOwnerActivity(), "Error al enviar tu solicitud.");
                                }
                            }
                        });
                        dismiss();
                    }
                }
            });
        }
    }

    public Boolean validarCampos() {
        Boolean band = true;

        if (etMailForgotPassword.getText().toString().isEmpty()) {
            etMailForgotPasswordLyt.setError("Correo electronico necesario.");
            band = false;
        } else {
            etMailForgotPasswordLyt.setError(null);
        }

        return band;
    }

}
