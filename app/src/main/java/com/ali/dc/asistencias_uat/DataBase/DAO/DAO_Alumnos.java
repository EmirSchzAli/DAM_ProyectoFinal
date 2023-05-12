package com.ali.dc.asistencias_uat.DataBase.DAO;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.Models.Alumnos;

public interface DAO_Alumnos extends DAO<Alumnos, String> {
    void getByMatricula(String matricula, VolleyCallback<Alumnos> callback);
}
