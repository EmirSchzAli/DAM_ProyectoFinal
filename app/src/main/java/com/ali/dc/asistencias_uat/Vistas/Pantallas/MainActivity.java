package com.ali.dc.asistencias_uat.Vistas.Pantallas;

import static com.ali.dc.asistencias_uat.Vistas.Utilerias.MetodosVistas.initWindowController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.Window;

import com.ali.dc.asistencias_uat.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        // Set an exit transition
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_main);
        initWindowController(MainActivity.this)
                .hide(WindowInsetsCompat.Type.systemBars());;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 3000);
    }
}