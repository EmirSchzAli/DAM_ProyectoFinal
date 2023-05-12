package com.ali.dc.asistencias_uat.Controller.Adapters;

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
import com.ali.dc.asistencias_uat.DataBase.AlumnosDB;
import com.ali.dc.asistencias_uat.DataBase.DocentesDB;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.Models.Docentes;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.ali.dc.asistencias_uat.UI.Views.Dialogs.EditarAlumno;

import java.util.List;

public class DocentesAdapter extends RecyclerView.Adapter<DocentesAdapter.ViewHolder> {

    private List<Docentes> docentesList;
    private Context mContext;

    public DocentesAdapter(List<Docentes> docentesList, Context mContext) {
        this.docentesList = docentesList;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return docentesList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()). inflate(R.layout.recycler_item_docentes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Docentes docente = docentesList.get(position);
        holder.tvNombre.setText(docente.getNombre());
        holder.tvNumeroEmpleado.setText(docente.getNum_empleado());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(mContext);
                FragmentManager fm = activity.getSupportFragmentManager();

                Bundle args = new Bundle();
                args.putString("id_docente", String.valueOf(docente.getId_docente()));

                EditarAlumno editarAlumno = new EditarAlumno();
                editarAlumno.setCancelable(false);
                editarAlumno.setArguments(args);
                editarAlumno.show(fm, "editTeachDialog");
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MetodosVistas.interactiveDialog((Activity) mContext,
                        "¿Seguro deseas eliminar a " + docente.getNombre() + "?",
                        "Estos cambios no podrán ser restaurados.",
                        "Eliminar",
                        "Cancelar",
                        AppCompatResources.getDrawable(mContext, R.drawable.outline_delete),
                        (dialogInterface, i) -> {
                            deleteTeacher(docente.getId_docente());
                        },
                        null);
            }
        });

    }

    private void deleteTeacher(int id_docente) {
        DocentesDB docentesDB = new DocentesDB(mContext);
        docentesDB.delete(String.valueOf(id_docente), new BooleanCallback() {
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
        public TextView tvNumeroEmpleado;
        public Button btnDelete, btnEdit;

        public ViewHolder(View view) {
            super(view);
            tvNombre = view.findViewById(R.id.tvDocenteNombre);
            tvNumeroEmpleado = view.findViewById(R.id.tvDocenteNumemEmpleados);
            btnDelete = view.findViewById(R.id.btnDeleteDocente);
            btnEdit = view.findViewById(R.id.btnEditDocente);
        }
    }

}
