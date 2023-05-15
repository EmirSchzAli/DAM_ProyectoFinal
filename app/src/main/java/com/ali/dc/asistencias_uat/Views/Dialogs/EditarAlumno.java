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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditarAlumno extends AppCompatDialogFragment {

    private TextInputLayout etMatriculaLyt, etNameLyt, etLastNameLyt;
    private TextInputEditText etMatricula, etName, etLastName;
    private TextView tvWrongMessage;
    AlumnosDB alumnosDB;
    String id_alumno;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        alumnosDB = new AlumnosDB(getContext());

        Bundle mArgs = getArguments();
        id_alumno = mArgs.getString("id_alumno");
        Log.d("Id retornado =>", id_alumno);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editar_alumno, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_edit)
                .setTitle("Editar alumno")
                .setPositiveButton("Guardar cambios", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setNegativeButton("Cancelar", null);

        etMatriculaLyt = view.findViewById(R.id.etMatriculaLyt);
        etMatricula = view.findViewById(R.id.etMatricula);
        etNameLyt= view.findViewById(R.id.etNameLyt);
        etName = view.findViewById(R.id.etName);
        etLastNameLyt = view.findViewById(R.id.etLastNameLyt);
        etLastName = view.findViewById(R.id.etLastName);
        tvWrongMessage = view.findViewById(R.id.tvWrongMessage);

        alumnosDB.getById(id_alumno, new VolleyCallback<Alumnos>() {
            @Override
            public void onSuccess(Alumnos alumnos) {
                Log.d("Alumno capturado =>", alumnos.toString());
                etMatricula.setText(alumnos.getMatricula());
                etName.setText(alumnos.getNombre());
                etLastName.setText(alumnos.getApellido());
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
                    alumnosDB.getById(id_alumno, new VolleyCallback<Alumnos>() {
                        @Override
                        public void onSuccess(Alumnos alumnos) {
                            editStudent(alumnos, v);
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

    private void editStudent(Alumnos alumno, View view) {
        if (validarCampos()){
            Alumnos alumnoReq = new Alumnos();
            alumnoReq.setId_alumno(alumno.getId_alumno());
            alumnoReq.setMatricula(etMatricula.getText().toString());
            alumnoReq.setNombre(etName.getText().toString());
            alumnoReq.setApellido(etLastName.getText().toString());

            alumnosDB.update(alumnoReq, new VolleyCallback<Alumnos>() {
                @Override
                public void onSuccess(Alumnos alumnos) {
                    MetodosVistas.snackBar(getActivity(), "Alumno(a) actualizado(a) con exito.");
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

        if (etMatricula.getText().toString().isEmpty()) {
            etMatriculaLyt.setError("Matricula del alumno(a) necesario");
            band = false;
        } else {
            etMatriculaLyt.setError(null);
        }

        if (etName.getText().toString().isEmpty()) {
            etNameLyt.setError("Nombre del alumno(a) necesario");
            band = false;
        } else {
            etNameLyt.setError(null);
        }

        if (etLastName.getText().toString().isEmpty()) {
            etLastNameLyt.setError("Apellido del alumno(a) necesario");
            band = false;
        } else {
            etLastNameLyt.setError(null);
        }

        return band;
    }

}
