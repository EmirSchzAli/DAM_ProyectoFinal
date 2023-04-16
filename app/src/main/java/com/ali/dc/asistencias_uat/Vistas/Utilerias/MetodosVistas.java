package com.ali.dc.asistencias_uat.Vistas.Utilerias;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.ali.dc.asistencias_uat.R;

public class MetodosVistas {

    public static WindowInsetsControllerCompat initWindowController(Activity activity) {
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView());
        // Configure the behavior of the hidden system bars.
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        return windowInsetsController;
    }

    public static void toast(Activity activity, String mensaje, int status) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.toast_layout));
        TextView text = (TextView) layout.findViewById(R.id.text);
        LottieAnimationView animationView = (LottieAnimationView) layout.findViewById(R.id.animation);
        switch (status){
            case 0:
                animationView.setAnimation(R.raw.error_status);
                break;
            case 1:
                animationView.setAnimation(R.raw.success_status);
                break;
            case 2:
                animationView.setAnimation(R.raw.warning_status);
                break;
            default:
                animationView.setAnimation(R.raw.info_status);
                break;
        }
        animationView.playAnimation();
        text.setText(mensaje);
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static void dialog(Activity activity, String title,String menssage, String positiveMsg, String negativeMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(menssage)
                .setPositiveButton(positiveMsg, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(negativeMsg, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
    }

}
