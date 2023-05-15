package com.ali.dc.asistencias_uat.Controller;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;
import static com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.ali.dc.asistencias_uat.Views.Dialogs.RegistrarAsistencia;
import com.ali.dc.asistencias_uat.Views.Screens.Login;
import com.ali.dc.asistencias_uat.Views.Screens.Registrar;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MetodosVistas extends AppCompatActivity {

    public static void snackBar(Activity activity, String mensaje) {
        Snackbar.make(activity.findViewById(android.R.id.content), mensaje, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(MaterialColors.getColor(activity, com.google.android.material.R.attr.colorSecondary, null))
                .setAnimationMode(ANIMATION_MODE_SLIDE)
                .setTextColor(MaterialColors.getColor(activity, com.google.android.material.R.attr.colorSecondaryContainer, null))
                .show();
    }

    public static void interactiveDialog(Activity activity,
                                  String title,
                                  String menssage,
                                  String positiveBtnText,
                                  String negativeBtnText,
                                  Drawable icon,
                                  DialogInterface.OnClickListener positiveButtonAction,
                                  DialogInterface.OnClickListener negativeButtonAction) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(menssage)
                .setPositiveButton(positiveBtnText, positiveButtonAction)
                .setNegativeButton(negativeBtnText, negativeButtonAction);
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }



    public static void basicDialog(Activity activity, String title, String menssage, String btnText, Drawable icon) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity)
                .setIcon(icon)
                .setTitle(title)
                .setMessage(menssage)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        builder.create();
        builder.show();
    }

    public static String getFirebaseError(String error) {

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

}
