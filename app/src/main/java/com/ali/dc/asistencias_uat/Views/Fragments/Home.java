package com.ali.dc.asistencias_uat.Views.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Controller.CaptureActivityPortraint;
import com.ali.dc.asistencias_uat.Controller.Firebase;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.ali.dc.asistencias_uat.Views.Dialogs.EditarPerfil;
import com.ali.dc.asistencias_uat.Views.Dialogs.RegistrarAsistencia;
import com.ali.dc.asistencias_uat.Views.Dialogs.RestablecerContrasenna;
import com.ali.dc.asistencias_uat.Views.Screens.Inicio;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


public class Home extends Fragment {

    private MaterialCardView editUserCard, updatePassword, updateMail;
    private TextView tvName, tvKindUser, tvMail, tvMatricula;
    private AdministradoresDB adminDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Inicio.toolbar.setTitle(R.string.fragment_home_label);
        setHasOptionsMenu(true);

        tvName = view.findViewById(R.id.tvName);
        tvKindUser = view.findViewById(R.id.tvKindUser);
        tvMail = view.findViewById(R.id.tvMail);
        tvMatricula = view.findViewById(R.id.tvMatricula);
        editUserCard = view.findViewById(R.id.editUserCard);
        updatePassword = view.findViewById(R.id.updatePasswordCard);
        updateMail = view.findViewById(R.id.updateMailCard);

        Inicio.fabHome.setImageResource(R.drawable.outline_qr_code);
        Inicio.fabHome. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });

        FirebaseUser user = Firebase.firebaseAuth.getCurrentUser();
        String uId = user.getUid();

        adminDB = new AdministradoresDB(getContext());

        adminDB.getByFirebaseId(uId, new VolleyCallback<Administradores>() {
            @Override
            public void onSuccess(Administradores administrador) {
                printDatas(administrador);
            }

            @Override
            public void onFailure(String errorMessage, int erroCode) {
                if ( erroCode != 404) {
                    MetodosVistas.snackBar((Activity) view.getContext(), errorMessage);
                }
            }
        });

        editUserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarPerfil editarPerfilDialog = new EditarPerfil();
                editarPerfilDialog.setCancelable(false);
                editarPerfilDialog.show(getActivity().getSupportFragmentManager(), "loginDialog");
            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestablecerContrasenna restablecerContrasennaDialog = new RestablecerContrasenna();
                restablecerContrasennaDialog.setCancelable(false);
                restablecerContrasennaDialog.show(getActivity().getSupportFragmentManager(), "passwordDialog");
            }
        });

        return view;

    }

    private void printDatas(Administradores administrador) {
        int tipoUsuario = administrador.getId_tipoAdmin();
        tvName.setText(administrador.getNombre());
        tvMail.setText(administrador.getCorreo());
        tvMatricula.setText(administrador.getNum_empleado());

        if (tipoUsuario == 1) {
            tvKindUser.setText("Administrador");
        } else if (tipoUsuario == 2) {
            tvKindUser.setText("Secretario");
        } else if (tipoUsuario == 3) {
            tvKindUser.setText("Checador");
        }
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

    @Override
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
                transaction.replace(R.id.fragmentContainer, new Home());
                transaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}