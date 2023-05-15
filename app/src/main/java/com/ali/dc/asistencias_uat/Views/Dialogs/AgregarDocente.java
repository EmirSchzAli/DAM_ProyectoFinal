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
import com.ali.dc.asistencias_uat.DataBase.DocentesDB;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AgregarDocente extends AppCompatDialogFragment {

    private TextInputLayout etNumeroEmpleadoLyt, etNameLyt;
    private TextInputEditText etNumeroEmpleado, etName;
    private TextView tvWrongMessage;
    DocentesDB docentesDB;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        docentesDB = new DocentesDB(getContext());

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_agregar_docente, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_school)
                .setTitle("Agregar docente")
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setNegativeButton("Cancelar", null);

        etNumeroEmpleadoLyt = view.findViewById(R.id.etNumeroEmpleadoLyt);
        etNumeroEmpleado = view.findViewById(R.id.etNumeroEpleado);
        etNameLyt= view.findViewById(R.id.etNameDocenteLyt);
        etName = view.findViewById(R.id.etNameDocente);
        tvWrongMessage = view.findViewById(R.id.tvWrongMessage);

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
                        String numeroEmpleado = etNumeroEmpleado.getText().toString();
                        String name = etName.getText().toString();
                        Docentes docente = new Docentes();
                        docente.setNum_empleado(numeroEmpleado);
                        docente.setNombre(name);
                        docentesDB.insert(docente, new BooleanCallback() {
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

        if (etNumeroEmpleado.getText().toString().isEmpty()) {
            etNumeroEmpleadoLyt.setError("Número de empleado necesario");
            band = false;
        } else {
            etNumeroEmpleadoLyt.setError(null);
        }

        if (etName.getText().toString().isEmpty()) {
            etNameLyt.setError("Nombre del docente necesario");
            band = false;
        } else {
            etNameLyt.setError(null);
        }

        return band;
    }

}
