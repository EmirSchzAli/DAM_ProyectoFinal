package com.ali.dc.asistencias_uat.DataBase.DAO;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Models.Registros;

import java.util.List;

public interface DAO_Registros extends DAO<Registros, String> {

    void getAllbyId(String id_admin,VolleyCallback<List<Registros>> callback);

}
