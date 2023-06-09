package com.ali.dc.asistencias_uat.Views.Dialogs;

import android.app.Dialog;
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

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.SalonesDB;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.Models.Salones;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditarSalon extends AppCompatDialogFragment {

    private TextInputLayout etNombreSalonLyt;
    private TextInputEditText etNombreSalon;
    private TextView tvWrongMessage;
    private Button btnEditSalon;
    private SalonesDB salonesDB;
    private String id_salon;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        salonesDB = new SalonesDB(getContext());

        Bundle mArgs = getArguments();
        id_salon = mArgs.getString("id_salon");
        Log.d("Id retornado =>", id_salon);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editar_salon, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_edit)
                .setTitle("Editar salón de clase")
                .setPositiveButton("Guardar cambios", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setNegativeButton("Cancelar", null);

        etNombreSalonLyt = view.findViewById(R.id.etNombreSalonLyt);
        etNombreSalon = view.findViewById(R.id.etNombreSalon);
        tvWrongMessage = view.findViewById(R.id.tvWrongMessageSalon);


        salonesDB.getById(id_salon, new VolleyCallback<Salones>() {
            @Override
            public void onSuccess(Salones salones) {
                Log.d("Docente capturado =>", salones.toString());
                etNombreSalon.setText(salones.getNombre());
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
                    salonesDB.getById(id_salon, new VolleyCallback<Salones>() {
                        @Override
                        public void onSuccess(Salones salones) {
                            editSalon(salones, v);
                        }
                        @Override
                        public void onFailure(String errorMessage, int erroCode) {
                            tvWrongMessage.setText(errorMessage);
                            tvWrongMessage.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
        }
    }

    private void editSalon(Salones salones, View view) {
        if (validarCampos()){
            Salones salonReq = new Salones();
            salonReq.setId_salon(salones.getId_salon());
            salonReq.setNombre(etNombreSalon.getText().toString());

            salonesDB.update(salonReq, new VolleyCallback<Salones>() {
                @Override
                public void onSuccess(Salones salones) {
                    MetodosVistas.snackBar(getActivity(), "Salón actualizado con exito.");
                    dismiss();
                }

                @Override
                public void onFailure(String errorMessage, int erroCode) {
                    tvWrongMessage.setText(errorMessage);
                    tvWrongMessage.setVisibility(View.VISIBLE);
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
