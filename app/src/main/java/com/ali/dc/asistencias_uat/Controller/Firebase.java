package com.ali.dc.asistencias_uat.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.ali.dc.asistencias_uat.Controller.Callbacks.FirebaseCallback;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Inicio;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Login;
import com.ali.dc.asistencias_uat.UI.Views.Screens.Registrar;
import com.ali.dc.asistencias_uat.UI.Utilities.MetodosVistas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
                        String errorMessageResult = getFirebaseError(errorMessage);
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

    public static void signUp(String correo, String contrasenna, FirebaseCallback callback) {
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
                        String errorMessageResult = getFirebaseError(errorMessage);
                        callback.onFailure(errorMessageResult);
                    } else {
                        callback.onFailure("Error de conexión al servidor.");
                    }
                }
            }
        });
    }

    private static String getFirebaseError(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                return "El formato del token personalizado es incorrecto. Por favor revise la documentación";

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                return "El token personalizado corresponde a una audiencia diferente.";

            case "ERROR_INVALID_CREDENTIAL":
                return "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.";

            case "ERROR_INVALID_EMAIL":
                Login.etMailLyt.setError("La dirección de correo electrónico está mal estructurada.");
                return null;

            case "ERROR_WRONG_PASSWORD":
                Login.etPasswordLyt.setError("La contraseña es incorrecta ");
                Login.etPassword.setText("");
                return null;

            case "ERROR_USER_MISMATCH":
                return "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente.";

            case "ERROR_REQUIRES_RECENT_LOGIN":
                return "Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.";

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                return "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.";

            case "ERROR_EMAIL_ALREADY_IN_USE":
                return "La dirección de correo electrónico ya está siendo utilizada por otra cuenta.";

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                return "Esta credencial ya está asociada con una cuenta de usuario diferente.";

            case "ERROR_USER_DISABLED":
                return "La cuenta de usuario ha sido inhabilitada por un administrador.";

            case "ERROR_USER_TOKEN_EXPIRED":
                return "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.";

            case "ERROR_USER_NOT_FOUND":
                return "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.";

            case "ERROR_INVALID_USER_TOKEN":
                return "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.";

            case "ERROR_OPERATION_NOT_ALLOWED":
                return "Esta operación no está permitida. Debes habilitar este servicio en la consola.";

            case "ERROR_WEAK_PASSWORD":
                Registrar.etPasswordLyt.setError("La contraseña no es válida, debe tener al menos 8 caracteres");
                return null;
        }

        return "Error inesperado.";
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

    public static void resetPassword(Activity activity, String mail) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        MetodosVistas.snackBar(activity, "Tu correo ha sido enviado. Favor de revisar tu bandeja de entrada.");
                        auth.signOut();
                    }
                });
    }

}
