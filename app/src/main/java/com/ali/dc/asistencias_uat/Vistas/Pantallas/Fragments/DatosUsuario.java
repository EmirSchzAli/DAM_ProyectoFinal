package com.ali.dc.asistencias_uat.Vistas.Pantallas.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.dc.asistencias_uat.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class DatosUsuario extends Fragment implements View.OnClickListener {

    private TextInputLayout etUserNameLyt;
    private MaterialButton btnFinish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_datos_usuario, container, false);

        etUserNameLyt = (TextInputLayout) view.findViewById(R.id.etUserNameLyt);
        btnFinish = (MaterialButton) view.findViewById(R.id.btnFinish);

        btnFinish.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (etUserNameLyt.getEditText().getText().toString().isEmpty()){
            etUserNameLyt.setError("El nombre de usuario es obligatorio");
        } else {
            etUserNameLyt.setError(null);
        }
    }
}