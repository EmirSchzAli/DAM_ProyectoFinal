package com.ali.dc.asistencias_uat.Controller.Callbacks;

public interface FirebaseCallback {
    void onSuccess(String s);
    void onFailure(String errorMessage);
}
