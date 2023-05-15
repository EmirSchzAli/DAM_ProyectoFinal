package com.ali.dc.asistencias_uat.Controller.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AdministradoresAdapter extends RecyclerView.Adapter<AdministradoresAdapter.ViewHolder> {

    private List<Administradores> adminList;
    private Context mContext;

    public AdministradoresAdapter(List<Administradores> adminList, Context mContext) {
        this.adminList = adminList;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()). inflate(R.layout.recycler_item_admins, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdministradoresAdapter.ViewHolder holder, int position) {
        Administradores admin = adminList.get(position);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(Constantes.TAG, user.getUid() + " -> " + admin.getFb_id());

        holder.tvNombre.setText(admin.getNombre());
        holder.tvNumEmpleado.setText(admin.getNum_empleado());
        holder.tvTipoAdmin.setText(admin.getTipoAdmin());

        if (admin.getId_tipoAdmin() == 1 || admin.getFb_id().equals(user.getUid())) holder.btnDelete.setVisibility(View.GONE);
        if (admin.getFb_id().equals(user.getUid())) holder.tvActualUser.setVisibility(View.VISIBLE);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MetodosVistas.interactiveDialog((Activity) mContext,
                        "¿Seguro deseas eliminar a " + admin.getNombre() + "?",
                        "Estos cambios no podrán ser restaurados.",
                        "Eliminar",
                        "Cancelar",
                        AppCompatResources.getDrawable(mContext, R.drawable.outline_delete),
                        (dialogInterface, i) -> {
                            deleteUser(admin.getId_admin());
                        },
                        null);
            }
        });

    }

    private void deleteUser(int id_admin) {
        AdministradoresDB adminDB = new AdministradoresDB(mContext);
        adminDB.delete(String.valueOf(id_admin), new BooleanCallback() {
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
        public TextView tvNumEmpleado;
        public TextView tvTipoAdmin;
        public TextView tvActualUser;
        public Button btnDelete;
        public MaterialCardView adminCardView;

        public ViewHolder(View view) {
            super(view);
            tvNombre = view.findViewById(R.id.tvNombre);
            tvNumEmpleado = view.findViewById(R.id.tvNumEmpleado);
            tvTipoAdmin = view.findViewById(R.id.tvTipoAdmin);
            btnDelete = view.findViewById(R.id.btnDelete);
            adminCardView = view.findViewById(R.id.adminCardView);
            tvActualUser = view.findViewById(R.id.tvActualUser);
        }
    }

}
