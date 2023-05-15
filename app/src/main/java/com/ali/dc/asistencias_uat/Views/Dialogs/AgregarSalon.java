package com.ali.dc.asistencias_uat.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.DataBase.SalonesDB;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.Models.Salones;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AgregarSalon extends AppCompatDialogFragment {

    private TextInputLayout etNombreSalonLyt;
    private TextInputEditText etNombreSalon;
    private TextView tvWrongMessage;
    private SalonesDB salonesDB;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        salonesDB = new SalonesDB(getContext());

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_agregar_salon, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_meeting_room)
                .setTitle("Agregar salón de clase")
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setNegativeButton("Cancelar", null);

        etNombreSalonLyt = view.findViewById(R.id.etNombreSalonLyt);
        etNombreSalon = view.findViewById(R.id.etNombreSalon);
        tvWrongMessage = view.findViewById(R.id.tvWrongMessageSalon);

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
                        String nombre = etNombreSalon.getText().toString();
                        Salones salones = new Salones();
                        salones.setNombre(nombre);
                        salonesDB.insert(salones, new BooleanCallback() {
                            @Override
                            public void onResponse(boolean value, String message) {
                                if (!value){
                                    tvWrongMessage.setText(message);
                                    tvWrongMessage.setVisibility(View.VISIBLE);
                                } else {
                                    MetodosVistas.snackBar((Activity) getContext(), message);
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

        if (etNombreSalon.getText().toString().isEmpty()) {
            etNombreSalonLyt.setError("Nombre del salón necesario");
            band = false;
        } else {
            etNombreSalon.setError(null);
        }

        return band;
    }
}
