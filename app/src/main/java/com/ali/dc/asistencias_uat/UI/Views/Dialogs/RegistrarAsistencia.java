package com.ali.dc.asistencias_uat.UI.Views.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.DataBase.DocentesDB;
import com.ali.dc.asistencias_uat.DataBase.SalonesDB;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.Models.Salones;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class RegistrarAsistencia extends AppCompatDialogFragment {

    private AlumnosDB alumnosDB;
    private DocentesDB docentesDB;
    private SalonesDB salonesDB;

    private TextInputLayout etAsistenciaMatriculaLyt, etAsistenciaNombreLyt, etAsistenciaApellidoLyt, listSalonesLyt;
    private TextInputEditText etAsistenciaMatricula, etAsistenciaNombre, etAsistenciaApellido;
    private AutoCompleteTextView listSalones;
    private Button btnRegistarAsistencia;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle mArgs = getArguments();
        String result = mArgs.getString("resultQR");
        Log.d("QR retornado =>", result);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_registrar_asistencia, null);
        builder.setView(view)
                .setIcon(R.drawable.outline_checklist)
                .setTitle("Registar asistencia")
                .setNegativeButton("Cancelar", null);

        alumnosDB = new AlumnosDB(getContext());
        docentesDB = new DocentesDB(getContext());
        salonesDB = new SalonesDB(getContext());

        etAsistenciaMatriculaLyt = view.findViewById(R.id.etAsistenciaMatriculaLyt);
        etAsistenciaNombreLyt = view.findViewById(R.id.etAsistenciaNombreLyt);
        etAsistenciaApellidoLyt = view.findViewById(R.id.etAsistenciaApellidoLyt);
        listSalonesLyt = view.findViewById(R.id.listSalonesLyt);
        etAsistenciaMatricula = view.findViewById(R.id.etAsistenciaMatricula);
        etAsistenciaNombre = view.findViewById(R.id.etAsistenciaNombre);
        etAsistenciaApellido = view.findViewById(R.id.etAsistenciaApellido);
        listSalones = view.findViewById(R.id.listSalones);
        btnRegistarAsistencia = view.findViewById(R.id.btnRegistarAsistencia);

        /*btnEditSalon.setOnClickListener(new View.OnClickListener() {
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
        });*/

        if (result.length() == 10){
            alumnosDB.getByMatricula(result, new VolleyCallback<Alumnos>() {
                @Override
                public void onSuccess(Alumnos alumnos) {
                    etAsistenciaMatricula.setText(result);
                    etAsistenciaNombre.setText(alumnos.getNombre());
                    etAsistenciaApellido.setText(alumnos.getApellido());
                }
                @Override
                public void onFailure(String errorMessage, int erroCode) {
                    MetodosVistas.snackBar(getActivity(), errorMessage);
                }
            });
        } else {
            docentesDB.getByNumEmplado(result, new VolleyCallback<Docentes>() {
                @Override
                public void onSuccess(Docentes docentes) {
                    etAsistenciaMatricula.setText(result);
                    etAsistenciaNombre.setText(docentes.getNombre());
                }

                @Override
                public void onFailure(String errorMessage, int erroCode) {
                    MetodosVistas.snackBar(getActivity(), errorMessage);
                }
            });
        }

        salonesDB.getAll(new VolleyCallback<List<Salones>>() {
            @Override
            public void onSuccess(List<Salones> salones) {
                ArrayList<String> valores = new ArrayList<String>();
                for (com.ali.dc.asistencias_uat.Models.Salones salon : salones) {
                    String valor = salon.getNombre();
                    valores.add(valor);
                }

                ArrayList<Salones> myList = new ArrayList<Salones>(salones);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                        valores);
                listSalones.setAdapter(adapter);
            }

            @Override
            public void onFailure(String errorMessage, int erroCode) {

            }
        });

        return builder.create();
    }



}
