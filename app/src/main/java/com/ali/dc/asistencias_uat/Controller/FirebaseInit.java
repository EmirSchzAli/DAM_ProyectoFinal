package com.ali.dc.asistencias_uat.Controller;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseInit extends  android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //Inicializar Firebase en la aplicación
        FirebaseApp.initializeApp(this);
    }
}
