package com.ali.dc.asistencias_uat.DataBase;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ali.dc.asistencias_uat.Controller.Firebase.FirestoreCallback;
import com.ali.dc.asistencias_uat.Models.Users;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsersFirebase {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MI ETIQUETA ===>";

    public static Users user;


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
                    }
                });

    }


    public static void getByUId(String uId, FirestoreCallback<Users> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Users").document(uId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()){
                    Users user = documentSnapshot.toObject(Users.class);
                    if (user != null) {
                        callback.onCallback(user);
                    } else {
                        callback.onFailure(new Exception("El objeto obtenido es nulo"));
                    }
                } else {
                    callback.onFailure(new Exception("El documento no existe"));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });
    }



}
