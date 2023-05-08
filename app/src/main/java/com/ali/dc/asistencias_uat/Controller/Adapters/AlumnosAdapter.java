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

    private TextView tvNombre, tvMatricula;

    private List<Alumnos> retrievedResponses;
    private Context context;

    public AlumnosAdapter(List<Alumnos> retrievedResponses, Context context) {
        this.retrievedResponses = retrievedResponses;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombre, tvMatricula;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvMatricula = itemView.findViewById(R.id.tvMatricula);;
        }

        public void setNombre(String nombre) {
            tvNombre.setText(nombre);
        }

        public void setMatricula(String matricula) {
            tvMatricula.setText(matricula);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_alumnos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setNombre(retrievedResponses.get(position).getNombre());
        holder.setMatricula(retrievedResponses.get(position).getMatricula());
    }

    @Override
    public int getItemCount() {
        return retrievedResponses.size();
    }

}
