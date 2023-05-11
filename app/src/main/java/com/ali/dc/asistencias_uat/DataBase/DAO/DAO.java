package com.ali.dc.asistencias_uat.DataBase.DAO;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;

import java.util.List;

public interface DAO<Object,K> {

    void insert(Object object, BooleanCallback callback);
    void getAll(VolleyCallback<List<Object>> callback);
    Object getById(K id, VolleyCallback<Object> callback);
    void update(Object object, VolleyCallback<Object> callback);
    void delete(K id, BooleanCallback callback);

}
