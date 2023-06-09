package com.ali.dc.asistencias_uat.DataBase.DAO;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Models.Docentes;

public interface DAO_Docentes extends DAO<Docentes, String> {
    void getByNumEmplado(String num_empleado, VolleyCallback<Docentes> callback);
}
