package com.ali.dc.asistencias_uat.UI.Views.Dialogs;

import static com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

public class RestablecerContrasenna extends AppCompatDialogFragment {

    private TextInputLayout etOldPasswordLyt, etNewPasswordLyt;
    private TextInputEditText etOldPassword, etNewPassword;
    private Button btnChangePassword;
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
                .setNegativeButton("Cancelar", null);

        etOldPasswordLyt = view.findViewById(R.id.etOldPasswordLyt);
        etNewPasswordLyt = view.findViewById(R.id.etNewPasswordLyt);
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        tvWrongPassword = view.findViewById(R.id.tvWrongPassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()){
                    String oldPassword = etOldPassword.getText().toString();
                    String newPassword = etNewPassword.getText().toString();
                    Firebase.resetPassword(oldPassword, newPassword, new BooleanCallback() {
                        @Override
                        public void onResponse(boolean value, String message) {
                            Log.d(Constantes.TAG, String.valueOf(value));
                            if (!value){
                                tvWrongPassword.setText(message);
                                tvWrongPassword.setVisibility(View.VISIBLE);
                            } else {
                                MetodosVistas.snackBar((Activity) view.getContext(), message);
                                dismiss();
                            }
                        }
                    });
                }
            }
        });


        return builder.create();
    }

    public Boolean validarCampos() {
        Boolean band = true;

        if (etOldPassword.getText().toString().isEmpty()) {
            etOldPasswordLyt.setError("Contraseña actual necesaria");
            band = false;
        } else {
            etOldPasswordLyt.setError(null);
        }

        if (etNewPassword.getText().toString().isEmpty()) {
            etNewPasswordLyt.setError("Contraseña nueva necesaria");
            band = false;
        } else if (etNewPassword.getText().toString().length() != 8) {
            etNewPasswordLyt.setError("Contraseña debe tener 8 caracteres");
            band = false;
        } else {
            etNewPasswordLyt.setError(null);
        }
        return band;
    }

}
