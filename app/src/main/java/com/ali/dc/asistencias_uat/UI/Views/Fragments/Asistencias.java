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

import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.CaptureActivityPortraint;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Inicio;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Asistencias extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asistencias, container, false);
        Inicio.toolbar.setTitle(R.string.fragment_asistencias_label);

        Inicio.fabHome.setVisibility(View.VISIBLE);
        Inicio.fabHome.setImageResource(R.drawable.outline_qr_code);
        Inicio.fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });

        return view;
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
                    Log.d("QR RESULT =>XX", "Cancelado.");
                } else {
                    Log.d("QR RESULT =>XX", result.getContents());
                }
            });

}