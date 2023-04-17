package com.ali.dc.asistencias_uat.Vistas.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.ali.dc.asistencias_uat.Controlador.Firebase.MetodosFirebase;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Vistas.Pantallas.Fragments.Menu.Asistencias;
import com.ali.dc.asistencias_uat.Vistas.Pantallas.Fragments.Menu.Home;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

public class Inicio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public static MaterialToolbar toolbar;
    public static WindowInsetsControllerCompat windowInsetsController;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        user = MetodosFirebase.firebaseAuth.getCurrentUser();
        setContentView(R.layout.tool_nav_drawer_layout);
        toolbar = (MaterialToolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        setTitle(user.getDisplayName());

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navView);


        navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Home()).commit();
            navView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Home()).commit();
                break;

            case R.id.nav_asistencia:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Asistencias()).commit();
                break;

            case R.id.nav_signout:
                signOutDialog();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOutDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setIcon(R.drawable.outline_sign_out)
                .setTitle("Aviso")
                .setMessage("¿Seguro deseas cerrar sesión?")
                .setPositiveButton("Cerrar sesión", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MetodosFirebase.signOut(Inicio.this);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        builder.create();
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}