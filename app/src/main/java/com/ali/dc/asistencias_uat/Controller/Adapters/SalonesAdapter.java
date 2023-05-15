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
import com.ali.dc.asistencias_uat.DataBase.SalonesDB;
import com.ali.dc.asistencias_uat.Models.Salones;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.ali.dc.asistencias_uat.Views.Dialogs.EditarSalon;

import java.util.List;

public class SalonesAdapter extends RecyclerView.Adapter<SalonesAdapter.ViewHolder> {

    private List<Salones> salonesList;
    private Context mContext;

    public SalonesAdapter(List<Salones> salonesList, Context mContext) {
        this.salonesList = salonesList;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return salonesList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()). inflate(R.layout.recycler_item_salones, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Salones salones = salonesList.get(position);
        holder.tvNombre.setText(salones.getNombre());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentActivity activity = (FragmentActivity)(mContext);
                FragmentManager fm = activity.getSupportFragmentManager();

                Bundle args = new Bundle();
                args.putString("id_salon", String.valueOf(salones.getId_salon()));

                EditarSalon editarSalon = new EditarSalon();
                editarSalon.setCancelable(false);
                editarSalon.setArguments(args);
                editarSalon.show(fm, "editCRoomDialog");
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MetodosVistas.interactiveDialog((Activity) mContext,
                        "¿Seguro deseas eliminar el salón '" + salones.getNombre() + "'?",
                        "Estos cambios no podrán ser restaurados.",
                        "Eliminar",
                        "Cancelar",
                        AppCompatResources.getDrawable(mContext, R.drawable.outline_delete),
                        (dialogInterface, i) -> {
                            deleteSalon(salones.getId_salon());
                        },
                        null);
            }
        });

    }

    private void deleteSalon(int id_salon) {
        SalonesDB salonesDB = new SalonesDB(mContext);
        salonesDB.delete(String.valueOf(id_salon), new BooleanCallback() {
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
        public Button btnDelete, btnEdit;

        public ViewHolder(View view) {
            super(view);
            tvNombre = view.findViewById(R.id.tvSalonNombre);
            btnDelete = view.findViewById(R.id.btnDeleteSalon);
            btnEdit = view.findViewById(R.id.btnEditSalon);
        }
    }

}
