package com.ali.dc.asistencias_uat.DataBase.DAO;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Models.Administradores;

public interface DAO_Administradores extends DAO<Administradores, String>{

    void getByFirebaseId(String id, VolleyCallback<Administradores> callback);
}
