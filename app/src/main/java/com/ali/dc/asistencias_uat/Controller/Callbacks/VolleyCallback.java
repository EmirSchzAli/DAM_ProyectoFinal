package com.ali.dc.asistencias_uat.Controller.Callbacks;

import org.json.JSONArray;

public interface VolleyCallback<T> {

    void onSuccess(T t);
    void onFailure(String errorMessage);

}
