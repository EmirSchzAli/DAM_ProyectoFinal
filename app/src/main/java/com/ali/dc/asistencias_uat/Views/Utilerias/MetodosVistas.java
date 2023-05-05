package com.ali.dc.asistencias_uat.Views.Utilerias;

import static com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.ali.dc.asistencias_uat.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

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


}
