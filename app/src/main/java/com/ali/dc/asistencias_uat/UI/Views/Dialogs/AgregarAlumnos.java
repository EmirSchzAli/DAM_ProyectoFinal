package com.ali.dc.asistencias_uat.UI.Views.Dialogs;

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
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

public class AgregarAlumnos extends AppCompatDialogFragment {

    private TextInputLayout etMatriculaLyt, etNameLyt, etLastNameLyt;
    private TextInputEditText etMatricula, etName, etLastName;
    private TextView tvWrongMessage;
    private Button btnAddStudent;
    AlumnosDB alumnosDB;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        alumnosDB = new AlumnosDB(getContext());

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_agregar_alumnos, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_students)
                .setTitle("Editar perfil")
                .setNegativeButton("Cancelar", null);

        etMatriculaLyt = view.findViewById(R.id.etMatriculaLyt);
        etMatricula = view.findViewById(R.id.etMatricula);
        etNameLyt= view.findViewById(R.id.etNameLyt);
        etName = view.findViewById(R.id.etName);
        etLastNameLyt = view.findViewById(R.id.etLastNameLyt);
        etLastName = view.findViewById(R.id.etLastName);
        btnAddStudent = view.findViewById(R.id.btnAddStudent);
        tvWrongMessage = view.findViewById(R.id.tvWrongMessage);

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        return builder.create();
    }

    private void addStudent() {
        if (validarCampos()){
            String matricula = etMatricula.getText().toString();
            String name = etName.getText().toString();
            String lastName = etLastName.getText().toString();
            Alumnos alumno = new Alumnos();
            alumno.setMatricula(matricula);
            alumno.setNombre(name);
            alumno.setApellido(lastName);
            alumnosDB.insert(alumno, new BooleanCallback() {
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

        if (etMatricula.getText().toString().isEmpty()) {
            etMatriculaLyt.setError("Matricula del alumno(a) necesario(a)");
            band = false;
        } else {
            etMatriculaLyt.setError(null);
        }

        if (etName.getText().toString().isEmpty()) {
            etNameLyt.setError("Nombre del alumno(a) necesario(a)");
            band = false;
        } else {
            etNameLyt.setError(null);
        }

        if (etLastName.getText().toString().isEmpty()) {
            etLastNameLyt.setError("Apellido del alumno(a) necesario(a)");
            band = false;
        } else {
            etLastNameLyt.setError(null);
        }

        return band;
    }

}
