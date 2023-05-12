package com.ali.dc.asistencias_uat.UI.Views.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.DataBase.DocentesDB;
import com.ali.dc.asistencias_uat.DataBase.SalonesDB;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.Models.Salones;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.CaptureActivityPortraint;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Inicio;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

public class Asistencias extends Fragment {

    private String qrResult = "";
    private AlumnosDB alumnosDB;
    private DocentesDB docentesDB;
    private SalonesDB salonesDB;

    private TextInputLayout etAsistenciaMatriculaLyt, etAsistenciaNombreLyt, etAsistenciaApellidoLyt, listSalonesLyt;
    private TextInputEditText etAsistenciaMatricula, etAsistenciaNombre, etAsistenciaApellido;
    private AutoCompleteTextView listSalones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asistencias, container, false);
        Inicio.toolbar.setTitle(R.string.fragment_asistencias_label);

        alumnosDB = new AlumnosDB(getContext());
        docentesDB = new DocentesDB(getContext());
        salonesDB = new SalonesDB(getContext());

        Inicio.fabHome.setVisibility(View.VISIBLE);
        Inicio.fabHome.setImageResource(R.drawable.outline_qr_code);
        Inicio.fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });

        etAsistenciaMatriculaLyt = view.findViewById(R.id.etAsistenciaMatriculaLyt);
        etAsistenciaNombreLyt = view.findViewById(R.id.etAsistenciaNombreLyt);
        etAsistenciaApellidoLyt = view.findViewById(R.id.etAsistenciaApellidoLyt);
        listSalonesLyt = view.findViewById(R.id.listSalonesLyt);
        etAsistenciaMatricula = view.findViewById(R.id.etAsistenciaMatricula);
        etAsistenciaNombre = view.findViewById(R.id.etAsistenciaNombre);
        etAsistenciaApellido = view.findViewById(R.id.etAsistenciaApellido);
        listSalones = view.findViewById(R.id.listSalones);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
    }

    private void scanQR() {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
        options.setPrompt("Lector QR");
        options.setCameraId(0);  // Use a specific camera of the device
        options.setBeepEnabled(false);
        options.setOrientationLocked(false);
        options.setBarcodeImageEnabled(true);
        options.setCaptureActivity(CaptureActivityPortraint.class);
        barcodeLauncher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Log.d("QR RESULT =>", "Cancelado.");
                } else {
                    Log.d("QR RESULT =>", result.getContents());
                    printResult(result.getContents());
                }
            });

    private void printResult(String contents) {
        if (contents.length() == 8){
            alumnosDB.getByMatricula(contents, new VolleyCallback<Alumnos>() {
                @Override
                public void onSuccess(Alumnos alumnos) {
                    etAsistenciaMatricula.setText(contents);
                    etAsistenciaNombre.setText(alumnos.getNombre());
                    etAsistenciaApellido.setText(alumnos.getApellido());
                }
                @Override
                public void onFailure(String errorMessage, int erroCode) {
                    MetodosVistas.snackBar(getActivity(), errorMessage);
                }
            });
        } else {
            docentesDB.getByNumEmplado(contents, new VolleyCallback<Docentes>() {
                @Override
                public void onSuccess(Docentes docentes) {
                    etAsistenciaMatricula.setText(contents);
                    etAsistenciaNombre.setText(docentes.getNombre());
                }

                @Override
                public void onFailure(String errorMessage, int erroCode) {
                    MetodosVistas.snackBar(getActivity(), errorMessage);
                }
            });
        }
    }

}