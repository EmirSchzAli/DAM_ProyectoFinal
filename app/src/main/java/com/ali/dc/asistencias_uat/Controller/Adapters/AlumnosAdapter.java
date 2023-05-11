package com.ali.dc.asistencias_uat.Controller.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;


import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;

import java.util.List;

public class AlumnosAdapter extends RecyclerView.Adapter<AlumnosAdapter.ViewHolder> {

    private List<Alumnos> alumnosList;
    private Context mContext;

    public AlumnosAdapter(List<Alumnos> alumnosList, Context mContext) {
        this.alumnosList = alumnosList;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return alumnosList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()). inflate(R.layout.recycler_item_alumnos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alumnos alumno = alumnosList.get(position);
        holder.tvNombre.setText(alumno.getNombre() + " " + alumno.getApellido());
        holder.tvMatricula.setText(String.valueOf(alumno.getMatricula()));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MetodosVistas.interactiveDialog((Activity) mContext,
                        "¿Seguro deseas eliminar a " + alumno.getNombre() + "?",
                        "Estos cambios no podrán ser restaurados.",
                        "Eliminar",
                        "Cancelar",
                        AppCompatResources.getDrawable(mContext, R.drawable.outline_delete),
                        (dialogInterface, i) -> {
                            deleteStudent(alumno.getId_alumno());
                        },
                        null);
            }
        });

    }

    private void deleteStudent(int id_alumno) {
        AlumnosDB alumnoDB = new AlumnosDB(mContext);
        alumnoDB.delete(String.valueOf(id_alumno), new BooleanCallback() {
            @Override
            public void onResponse(boolean value, String message) {
                if (value) {
                    MetodosVistas.snackBar((Activity) mContext, message);
                } else {
                    MetodosVistas.snackBar((Activity) mContext, message);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNombre;
        public TextView tvMatricula;
        public Button btnDelete, btnEdit;

        public ViewHolder(View view) {
            super(view);
            tvNombre = view.findViewById(R.id.tvNombre);
            tvMatricula = view.findViewById(R.id.tvMatricula);
            btnDelete = view.findViewById(R.id.btnDelete);
            btnEdit = view.findViewById(R.id.btnEdit);
        }
    }

}
