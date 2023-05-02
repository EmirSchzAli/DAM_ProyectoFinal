package com.ali.dc.asistencias_uat.Views.Pantallas.Fragments.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.dc.asistencias_uat.Controller.Firebase.FirestoreCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase.MetodosFirebase;
import com.ali.dc.asistencias_uat.DataBase.UsersFirebase;
import com.ali.dc.asistencias_uat.Models.Users;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Views.Pantallas.Dialogs.EditarPerfil;
import com.ali.dc.asistencias_uat.Views.Pantallas.Inicio;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseUser;


public class Home extends Fragment {

    MaterialCardView editUserCard;
    private TextView tvName, tvKindUser, tvMail, tvMatricula;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Inicio.toolbar.setTitle(R.string.fragment_home_label);

        tvName = view.findViewById(R.id.tvName);
        tvKindUser = view.findViewById(R.id.tvKindUser);
        tvMail = view.findViewById(R.id.tvMail);
        tvMatricula = view.findViewById(R.id.tvMatricula);

        editUserCard = view.findViewById(R.id.editUserCard);

        FirebaseUser user = MetodosFirebase.firebaseAuth.getCurrentUser();
        String uId = user.getUid();
        UsersFirebase.getByUId(uId, new FirestoreCallback<Users>() {
            @Override
            public void onCallback(Users userCallback) {
                tvName.setText(userCallback.getNombre());
                tvKindUser.setText(userCallback.getTipoUsuario());
                tvMatricula.setText(String.valueOf(userCallback.getMatricula()));
                tvMail.setText(userCallback.getMail());
            }
            @Override
            public void onFailure(Exception e) {}
        });

        editUserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarPerfil editarPerfilDialog = new EditarPerfil();
                editarPerfilDialog.show(getActivity().getSupportFragmentManager(), "loginDialog");
            }
        });

        return view;
    }
}