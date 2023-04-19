package com.ali.dc.asistencias_uat.Views.Pantallas.Fragments.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.dc.asistencias_uat.Controller.Firebase.MetodosFirebase;
import com.ali.dc.asistencias_uat.DataBase.UsersFirebase;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Views.Pantallas.Inicio;
import com.google.firebase.auth.FirebaseUser;


public class Home extends Fragment {


    public static void printUserInformation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Inicio.toolbar.setTitle(R.string.fragment_home_label);

        FirebaseUser user = MetodosFirebase.firebaseAuth.getCurrentUser();
        String uId = user.getUid();
        Log.d("MI ETIQUETA ==>", uId);
        UsersFirebase.getByUId(uId);

        return view;
    }
}