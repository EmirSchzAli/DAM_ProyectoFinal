package com.ali.dc.asistencias_uat.DataBase;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ali.dc.asistencias_uat.Models.Users;
import com.ali.dc.asistencias_uat.Views.Pantallas.Fragments.Menu.Home;
import com.ali.dc.asistencias_uat.Views.Pantallas.Registrar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsersFirebase {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MI ETIQUETA ===>";

    public static void insert(Activity activity, Users object, String userId) {

        db.collection("Users").document(userId)
                .set(object)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.w(TAG, "Exito al agregar al usaurio en Firestore.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error al agregar al usaurio en Firestore: ", e);
                        Registrar.bandSignUp = false;
                    }
                });

    }

    public static void getByUId(String uId){
        DocumentReference docRef = db.collection("Users").document(uId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        Home.printUserInformation();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

}
