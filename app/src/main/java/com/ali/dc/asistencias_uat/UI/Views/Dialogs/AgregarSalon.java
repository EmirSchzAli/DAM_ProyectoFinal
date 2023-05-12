package com.ali.dc.asistencias_uat.UI.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.DataBase.DocentesDB;
import com.ali.dc.asistencias_uat.DataBase.SalonesDB;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.Models.Salones;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AgregarSalon extends AppCompatDialogFragment {

    private TextInputLayout etNombreSalonLyt;
    private TextInputEditText etNombreSalon;
    private TextView tvWrongMessage;
    private Button btnAddSalon;
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
                .setNegativeButton("Cancelar", null);

        etNombreSalonLyt = view.findViewById(R.id.etNombreSalonLyt);
        etNombreSalon = view.findViewById(R.id.etNombreSalon);
        btnAddSalon = view.findViewById(R.id.btnAddSalon);
        tvWrongMessage = view.findViewById(R.id.tvWrongMessageSalon);

        btnAddSalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSalon();
            }
        });

        return builder.create();
    }

    private void addSalon() {
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
