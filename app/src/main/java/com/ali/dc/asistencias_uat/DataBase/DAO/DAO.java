package com.ali.dc.asistencias_uat.DataBase.DAO;

public interface DAO<Object,K> {

    void insert(Object object);
    Object getById(K id);
    void update(Object object);
    void delete(K id);

}
