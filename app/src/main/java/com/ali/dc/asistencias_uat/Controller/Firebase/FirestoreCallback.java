package com.ali.dc.asistencias_uat.Controller.Firebase;

public interface FirestoreCallback<T> {
    void onCallback(T object);
    void onFailure(Exception e);
}

