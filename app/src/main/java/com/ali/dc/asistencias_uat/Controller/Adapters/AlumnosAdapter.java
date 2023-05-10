package com.ali.dc.asistencias_uat.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.R;

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
        holder.tvNombre.setText(alumno.getNombre());
        holder.tvMatricula.setText(String.valueOf(alumno.getMatricula()));

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNombre;
        public TextView tvMatricula;

        public ViewHolder(View view) {
            super(view);
            tvNombre = view.findViewById(R.id.tvNombre);
            tvMatricula = view.findViewById(R.id.tvMatricula);
        }
    }

}
