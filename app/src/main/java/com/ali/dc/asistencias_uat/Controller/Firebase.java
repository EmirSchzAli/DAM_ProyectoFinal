package com.ali.dc.asistencias_uat.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Callbacks.FirebaseGetUserCallback;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Inicio;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Login;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Firebase {

    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static void logIn(Activity activity, String mail, String password) {

        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser actualUser = firebaseAuth.getCurrentUser();
                    if (actualUser.isEmailVerified()){
                        Context context = activity.getApplicationContext();
                        Intent intent = new Intent(activity, Inicio.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } else {
                        MetodosVistas.basicDialog(activity,
                                "Verificación pendiente",
                                "Es necesario confirmar tu correo electronico. " +
                                        "Favor de revisar tu bandeja de entrada y sigue las instrucciones que te mencionan.",
                                "De acuerdo",
                                AppCompatResources.getDrawable(activity, R.drawable.outline_mark_email_unread));
                        firebaseAuth.signOut();
                    }

                } else {
                    if(task.getException() instanceof FirebaseAuthException){
                        String errorMessage = ((FirebaseAuthException) task.getException()).getErrorCode();
                        String errorMessageResult = MetodosVistas.getFirebaseError(errorMessage);
                        if (errorMessageResult != null) {
                            MetodosVistas.snackBar(activity, errorMessageResult);
                        }
                    } else {
                        MetodosVistas.snackBar(activity, "Error al conectar con el servidor.");

                    }
                }
            }
        });
    }

    public static void signUp(String correo, String contrasenna, FirebaseGetUserCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(correo,
                contrasenna)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser actualUser = firebaseAuth.getCurrentUser();
                    actualUser.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        callback.onSuccess(actualUser.getUid());
                                        firebaseAuth.signOut();
                                    }
                                }
                            });

                } else {
                    if(task.getException() instanceof FirebaseAuthException){
                        String errorMessage = ((FirebaseAuthException) task.getException()).getErrorCode();
                        String errorMessageResult = MetodosVistas.getFirebaseError(errorMessage);
                        callback.onFailure(errorMessageResult);
                    } else {
                        callback.onFailure("Error de conexión al servidor.");
                    }
                }
            }
        });
    }

    public static void signOut(Activity activity){
        firebaseAuth.signOut();
        Context context = activity.getApplicationContext();
        Intent intent = new Intent(activity, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void updateUserAuthentication(String userName) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        }
                    }
                });
    }

    public static void resetPassword(String oldPassword, String newPassword, BooleanCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        AuthCredential authentication = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
        user.reauthenticate(authentication)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        user.updatePassword(newPassword).
                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        callback.onResponse(true, "Contraseña actualizada.");
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onResponse(false, "Contraseña actual incorrecta.");
                    }
                });
    }

}
