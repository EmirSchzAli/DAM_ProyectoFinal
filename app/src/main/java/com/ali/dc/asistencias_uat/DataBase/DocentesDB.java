package com.ali.dc.asistencias_uat.DataBase;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.DAO.DAO_Docentes;
import com.ali.dc.asistencias_uat.Models.Docentes;

import java.util.List;

public class DocentesDB implements DAO_Docentes {

    @Override
    public void insert(Docentes object, BooleanCallback callback) {

    }

    @Override
    public void getAll(VolleyCallback<List<Docentes>> callback) {

    }

    @Override
    public Docentes getById(String id, VolleyCallback<Docentes> callback) {
        return null;
    }

    @Override
    public void update(Docentes object, VolleyCallback<Docentes> callback) {

    }

    @Override
    public void delete(String id, BooleanCallback callback) {

    }
}
