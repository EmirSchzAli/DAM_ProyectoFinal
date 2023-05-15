package com.ali.dc.asistencias_uat.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ali.dc.asistencias_uat.Controller.Adapters.DocentesAdapter;
import com.ali.dc.asistencias_uat.Controller.Adapters.RegistrosAdapter;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.DataBase.DocentesDB;
import com.ali.dc.asistencias_uat.DataBase.RegistrosDB;
import com.ali.dc.asistencias_uat.DataBase.SalonesDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.Models.Registros;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.CaptureActivityPortraint;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.ali.dc.asistencias_uat.Views.Dialogs.RegistrarAsistencia;
import com.ali.dc.asistencias_uat.Views.Screens.Inicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

public class Asistencias extends Fragment {
    private AdministradoresDB administradoresDB;
    private RegistrosDB registrosDB;
    private RecyclerView asistenciasRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asistencias, container, false);
        Inicio.toolbar.setTitle(R.string.fragment_asistencias_label);
        setHasOptionsMenu(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        administradoresDB = new AdministradoresDB(getContext());
        registrosDB = new RegistrosDB(getContext());

        Inicio.fabHome.setVisibility(View.VISIBLE);
        Inicio.fabHome.setImageResource(R.drawable.outline_qr_code);
        Inicio.fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });

        asistenciasRecyclerView = view.findViewById(R.id.asistenciasRecyclerView);
        asistenciasRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        registrosDB.getAll(new VolleyCallback<List<Registros>>() {
            @Override
            public void onSuccess(List<Registros> registros) {
                Log.d(Constantes.TAG, registros.toString());
                asistenciasRecyclerView.setAdapter(new RegistrosAdapter(registros, getContext()));
            }
            @Override
            public void onFailure(String errorMessage, int erroCode) {
                MetodosVistas.snackBar((Activity) view.getContext(), errorMessage);
            }
        });

        return view;
    }

    private void scanQR() {

        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
        options.setPrompt("Escanea el codigo QR de la credencial universitaria");
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
                    FragmentActivity activity = (FragmentActivity)(getContext());
                    FragmentManager fm = activity.getSupportFragmentManager();

                    Bundle args = new Bundle();
                    args.putString("resultQR", result.getContents());

                    RegistrarAsistencia registrarAsistencia = new RegistrarAsistencia();
                    registrarAsistencia.setCancelable(false);
                    registrarAsistencia.setArguments(args);
                    registrarAsistencia.show(fm, "addCheckDialog");
                }
            });

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_inicio, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.actualizar:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new Asistencias());
                transaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}