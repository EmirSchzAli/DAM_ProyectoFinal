package com.ali.dc.asistencias_uat.Controller.Callbacks;

public interface VolleyCallback<T> {

    void onSuccess(T t);
    void onFailure(String errorMessage, int erroCode);

}
