package com.ali.dc.asistencias_uat.Controller.Callbacks;

public interface FirebaseGetUserCallback {
    void onSuccess(String s);
    void onFailure(String errorMessage);
}
