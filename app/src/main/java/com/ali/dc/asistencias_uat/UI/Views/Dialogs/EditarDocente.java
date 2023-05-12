package com.ali.dc.asistencias_uat.UI.Views.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.DataBase.DocentesDB;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditarDocente extends AppCompatDialogFragment {

    private TextInputLayout etNumeroEmpleadoLyt, etNameLyt;
    private TextInputEditText etNumeroEmpleado, etName;
    private TextView tvWrongMessage;
    private Button btnEditDocente;
    DocentesDB docentesDB;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        docentesDB = new DocentesDB(getContext());

        Bundle mArgs = getArguments();
        String id_docente = mArgs.getString("id_docente");
        Log.d("Id retornado =>", id_docente);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editar_docente, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_edit)
                .setTitle("Editar docente")
                .setNegativeButton("Cancelar", null);

        etNumeroEmpleadoLyt = view.findViewById(R.id.etNumeroEmpleadoLyt);
        etNumeroEmpleado = view.findViewById(R.id.etNumeroEpleado);
        etNameLyt= view.findViewById(R.id.etNameDocenteLyt);
        etName = view.findViewById(R.id.etNameDocente);
        btnEditDocente = view.findViewById(R.id.btnEditDocente);
        tvWrongMessage = view.findViewById(R.id.tvWrongMessage);

        btnEditDocente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docentesDB.getById(id_docente, new VolleyCallback<Docentes>() {
                    @Override
                    public void onSuccess(Docentes docentes) {
                        editDocente(docentes, v);
                    }
                    @Override
                    public void onFailure(String errorMessage, int erroCode) {
                        tvWrongMessage.setText(errorMessage);
                        tvWrongMessage.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


        docentesDB.getById(id_docente, new VolleyCallback<Docentes>() {
            @Override
            public void onSuccess(Docentes docentes) {
                Log.d("Docente capturado =>", docentes.toString());
                etNumeroEmpleado.setText(docentes.getNum_empleado());
                etName.setText(docentes.getNombre());
            }
            @Override
            public void onFailure(String errorMessage, int erroCode) {}
        });

        return builder.create();
    }

    private void editDocente(Docentes docente, View view) {
        if (validarCampos()){
            Docentes docenteReq = new Docentes();
            docenteReq.setId_docente(docente.getId_docente());
            docenteReq.setNombre(etNumeroEmpleado.getText().toString());
            docenteReq.setNombre(etName.getText().toString());

            docentesDB.update(docenteReq, new VolleyCallback<Docentes>() {
                @Override
                public void onSuccess(Docentes docentes) {
                    MetodosVistas.snackBar(getActivity(), "Docente actualizado con exito.");
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
