package com.ali.dc.asistencias_uat.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RestablecerContrasenna extends AppCompatDialogFragment {

    private TextInputLayout etOldPasswordLyt, etNewPasswordLyt;
    private TextInputEditText etOldPassword, etNewPassword;
    private TextView tvWrongPassword;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_restablecer_pass, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_password)
                .setTitle("Restablecer Contraseña")
                .setPositiveButton("Cambiar contraseña", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setNegativeButton("Cancelar", null);

        etOldPasswordLyt = view.findViewById(R.id.etOldPasswordLyt);
        etNewPasswordLyt = view.findViewById(R.id.etNewPasswordLyt);
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        tvWrongPassword = view.findViewById(R.id.tvWrongPassword);


        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog d = (AlertDialog) getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validarCampos()){
                        String oldPassword = etOldPassword.getText().toString();
                        String newPassword = etNewPassword.getText().toString();
                        Firebase.changePassword(oldPassword, newPassword, new BooleanCallback() {
                            @Override
                            public void onResponse(boolean value, String message) {
                                Log.d(Constantes.TAG, String.valueOf(value));
                                if (!value){
                                    tvWrongPassword.setText(message);
                                    tvWrongPassword.setVisibility(View.VISIBLE);
                                } else {
                                    MetodosVistas.snackBar(getActivity(), message);
                                    dismiss();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public Boolean validarCampos() {
        Boolean band = true;

        if (etOldPassword.getText().toString().isEmpty()) {
            etOldPasswordLyt.setError("Contraseña actual necesaria");
            band = false;
        } else {
            etOldPasswordLyt.setError(null);
        }

        if (etNewPassword.getText().toString().length() != 8) {
            etNewPasswordLyt.setError("Contraseña debe tener 8 caracteres");
            band = false;
        } else {
            etNewPasswordLyt.setError(null);
        }

        if (etNewPassword.getText().toString().isEmpty()) {
            etNewPasswordLyt.setError("Contraseña nueva necesaria");
            band = false;
        } else {
            etNewPasswordLyt.setError(null);
        }

        if (etOldPassword.getText().toString().equals(etNewPassword.getText().toString())){
            etNewPasswordLyt.setError("Contraseña identica al actual.");
            band = false;
        } else {
            etNewPasswordLyt.setError(null);
        }

        return band;
    }

}
