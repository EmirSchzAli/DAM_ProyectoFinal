package com.ali.dc.asistencias_uat.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.DataBase.DocentesDB;
import com.ali.dc.asistencias_uat.DataBase.RegistrosDB;
import com.ali.dc.asistencias_uat.DataBase.SalonesDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.Models.Registros;
import com.ali.dc.asistencias_uat.Models.Salones;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrarAsistencia extends AppCompatDialogFragment {

    private AlumnosDB alumnosDB;
    private DocentesDB docentesDB;
    private SalonesDB salonesDB;
    private RegistrosDB registrosDB;
    private AdministradoresDB adminDB;
    private TextInputLayout etAsistenciaMatriculaLyt, etAsistenciaNombreLyt, etAsistenciaApellidoLyt, etUATMemberLyt, listSalonesLyt;
    private TextInputEditText etAsistenciaMatricula, etAsistenciaNombre, etAsistenciaApellido, etUATMember;;
    private TextView tvWrongMessageRegistro;
    private AutoCompleteTextView listSalones;

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
                .setPositiveButton("Registar asistencia", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setNegativeButton("Cancelar", null);

        alumnosDB = new AlumnosDB(getContext());
        docentesDB = new DocentesDB(getContext());
        salonesDB = new SalonesDB(getContext());
        registrosDB = new RegistrosDB(getContext());
        adminDB = new AdministradoresDB(getContext());

        etAsistenciaMatriculaLyt = view.findViewById(R.id.etAsistenciaMatriculaLyt);
        etAsistenciaNombreLyt = view.findViewById(R.id.etAsistenciaNombreLyt);
        etAsistenciaApellidoLyt = view.findViewById(R.id.etAsistenciaApellidoLyt);
        etUATMemberLyt = view.findViewById(R.id.etUATMemberLyt);
        listSalonesLyt = view.findViewById(R.id.listSalonesLyt);
        etAsistenciaMatricula = view.findViewById(R.id.etAsistenciaMatricula);
        etAsistenciaNombre = view.findViewById(R.id.etAsistenciaNombre);
        etAsistenciaApellido = view.findViewById(R.id.etAsistenciaApellido);
        etUATMember = view.findViewById(R.id.etUATMember);
        tvWrongMessageRegistro = view.findViewById(R.id.tvWrongMessageRegistro);
        listSalones = view.findViewById(R.id.listSalones);

        salonesDB.getAll(new VolleyCallback<List<Salones>>() {
            @Override
            public void onSuccess(List<Salones> salones) {
                ArrayList<String> valores = new ArrayList<String>();
                for (com.ali.dc.asistencias_uat.Models.Salones salon : salones) {
                    String valor = salon.getNombre();
                    valores.add(valor);
                }

                //ArrayList<Salones> myList = new ArrayList<Salones>(salones);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                        valores);
                Log.d("ArrayAdapter = >", valores.toString());
                listSalones.setAdapter(adapter);
            }
            @Override
            public void onFailure(String errorMessage, int erroCode) {}
        });

        if (result.trim().length() == 10){
            alumnosDB.getByMatricula(result, new VolleyCallback<Alumnos>() {
                @Override
                public void onSuccess(Alumnos alumnos) {
                    etAsistenciaMatricula.setText(result);
                    etAsistenciaNombre.setText(alumnos.getNombre());
                    etAsistenciaApellido.setText(alumnos.getApellido());
                    etUATMember.setText("Alumno");
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
                    etUATMember.setText("Docente");
                }

                @Override
                public void onFailure(String errorMessage, int erroCode) {
                    MetodosVistas.snackBar(getActivity(), errorMessage);
                }
            });
        }



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
                    if (validarCampos()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String idAdmin = user.getUid();
                        Log.d(Constantes.TAG, idAdmin);
                        adminDB.getByFirebaseId(idAdmin, new VolleyCallback<Administradores>() {
                            @Override
                            public void onSuccess(Administradores administradores) {
                                Log.d(Constantes.TAG, administradores.toString());
                                crearRegistro(administradores);
                            }
                            @Override
                            public void onFailure(String errorMessage, int erroCode) {
                                //MetodosVistas.snackBar((Activity) getContext(), errorMessage);
                                dismiss();
                            }
                        });
                    }
                }
            });
        }
    }

    private void crearRegistro(Administradores administradores) {
        Registros registro = new Registros();
        registro.setNombre_registrado(etAsistenciaNombre.getText().toString());
        registro.setMiembro(etUATMember.getText().toString());
        registro.setSalon(listSalones.getText().toString());
        registro.setFecha_hora(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        registro.setId_admin(administradores.getId_admin());
        registrosDB.insert(registro, new BooleanCallback() {
            @Override
            public void onResponse(boolean value, String message) {
                if (!value){
                    tvWrongMessageRegistro.setText(message);
                    tvWrongMessageRegistro.setVisibility(View.VISIBLE);
                } else {
                    MetodosVistas.snackBar((Activity) getContext(), message);
                    dismiss();
                }
            }
        });
    }

    public Boolean validarCampos() {
        Boolean band = true;

        if (listSalones.getText().toString().isEmpty()) {
            listSalonesLyt.setError("Seleccione un salón de clase");
            band = false;
        } else {
            listSalonesLyt.setError(null);
        }

        return band;
    }

}
