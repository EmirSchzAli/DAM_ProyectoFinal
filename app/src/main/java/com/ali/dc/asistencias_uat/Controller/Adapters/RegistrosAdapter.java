package com.ali.dc.asistencias_uat.Controller.Adapters;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.Models.Registros;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Views.Dialogs.EditarAlumno;

import java.util.List;

public class RegistrosAdapter extends RecyclerView.Adapter<RegistrosAdapter.ViewHolder> {

    private List<Registros> registrosList;
    private Context mContext;

    public RegistrosAdapter(List<Registros> registrosList, Context mContext) {
        this.registrosList = registrosList;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return registrosList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()). inflate(R.layout.recycler_item_registros, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Registros registros = registrosList.get(position);
        holder.tvRegNombre.setText(registros.getNombre_registrado());
        holder.tvRegMiembro.setText(registros.getMiembro());
        holder.tvRegFecha.setText(String.valueOf(registros.getFecha_hora()));
        holder.tvRegSalon.setText(registros.getSalon());

        AdministradoresDB administradoresDB = new AdministradoresDB(mContext.getApplicationContext());
        administradoresDB.getById(String.valueOf(registros.getId_admin()), new VolleyCallback<Administradores>() {
            @Override
            public void onSuccess(Administradores administradores) {
                holder.tvRegAdmin.setText(administradores.getNombre());
            }

            @Override
            public void onFailure(String errorMessage, int erroCode) {

            }
        });


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRegNombre, tvRegMiembro, tvRegFecha, tvRegSalon, tvRegAdmin;

        public ViewHolder(View view) {
            super(view);
            tvRegNombre = view.findViewById(R.id.tvRegNombre);
            tvRegMiembro = view.findViewById(R.id.tvRegMiembro);
            tvRegFecha = view.findViewById(R.id.tvRegHora);
            tvRegSalon = view.findViewById(R.id.tvRegSalon);
            tvRegAdmin = view.findViewById(R.id.tvRegAdmin);
        }
    }

}
