package com.ali.dc.asistencias_uat.Views.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.Controller.Firebase;
import com.ali.dc.asistencias_uat.DataBase.AdministradoresDB;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.ali.dc.asistencias_uat.Views.Fragments.Alumnos;
import com.ali.dc.asistencias_uat.Views.Fragments.Asistencias;
import com.ali.dc.asistencias_uat.Views.Fragments.Docentes;
import com.ali.dc.asistencias_uat.Views.Fragments.Home;
import com.ali.dc.asistencias_uat.Controller.MetodosVistas;
import com.ali.dc.asistencias_uat.Views.Fragments.Salones;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Inicio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public static MaterialToolbar toolbar;
    private NavigationView navigationDrawer;
    private TextView userNameTextHeaderNav, userMailTextHeaderNav;
    private View headerNavView;
    private AdministradoresDB adminDB;
    public static String fragmentTAG = "";
    public static FloatingActionButton fabHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_nav_drawer_layout);

        FirebaseUser user = Firebase.firebaseAuth.getCurrentUser();
        String uId = user.getUid();

        toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Inicio");

        fabHome = findViewById(R.id.fabHome);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationDrawer = findViewById(R.id.navigationDrawer);
        headerNavView = navigationDrawer.getHeaderView(0);
        userNameTextHeaderNav = headerNavView.findViewById(R.id.userNameTextHeaderNav);
        userMailTextHeaderNav = headerNavView.findViewById(R.id.userMailTextHeaderNav);

        adminDB = new AdministradoresDB(getApplicationContext());

        navigationDrawer.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        if (savedInstanceState == null) {
            fragmentTAG = "inicioFrag";
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Home(), fragmentTAG).commit();
            navigationDrawer.setCheckedItem(R.id.nav_home);
        }

        adminDB.getByFirebaseId(uId, new VolleyCallback<Administradores>() {
            @Override
            public void onSuccess(Administradores administradores) {
                navigationDrawer.getMenu().clear();
                switch (administradores.getId_tipoAdmin()) {
                    case 1:
                        navigationDrawer.inflateMenu(R.menu.menu_admin);
                        break;
                    case 2:
                        navigationDrawer.inflateMenu(R.menu.menu_sect);
                        break;
                    default:
                        navigationDrawer.inflateMenu(R.menu.menu_check);
                        break;
                }
                printData(administradores);
            }

            @Override
            public void onFailure(String errorMessage, int erroCode) {
                if (erroCode == 0 && erroCode != 404){
                    MetodosVistas.snackBar(Inicio.this, errorMessage);
                } else {
                    MetodosVistas.interactiveDialog(
                            Inicio.this,
                            errorMessage,
                            null,
                            "Regresar",
                            "",
                            AppCompatResources.getDrawable(Inicio.this, R.drawable.outline_error),
                            (dialogInterface, i) -> {
                                startActivity(new Intent(Inicio.this, Login.class));
                                Firebase.signOut(Inicio.this);
                            },
                            (dialogInterface, i) -> {});
                }

            }
        });

    }

    private void printData(Administradores administradores) {
        Log.d("objecto en metodo: ", administradores.toString());
        userNameTextHeaderNav.setText(administradores.getNombre());
        userMailTextHeaderNav.setText(administradores.getCorreo());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                fragmentTAG = "inicioFrag";
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Home(), fragmentTAG).commit();
                break;

            case R.id.nav_asistencia:
                fragmentTAG = "asistenciasFrag";
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Asistencias(), fragmentTAG).commit();
                break;

            case R.id.nav_teachers:
                fragmentTAG = "docentesFrag";
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Docentes(), fragmentTAG).commit();
                break;

            case R.id.nav_students:
                fragmentTAG = "alumnosFrag";
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Alumnos(), fragmentTAG).commit();
                break;

            case R.id.nav_classrooms:
                fragmentTAG = "salonesFrag";
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Salones(), fragmentTAG).commit();
                break;

            case R.id.nav_admins:
                fragmentTAG = "administradoresFrag";
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new com.ali.dc.asistencias_uat.Views.Fragments.Administradores(), fragmentTAG).commit();
                break;

            case R.id.nav_signout:
                MetodosVistas.interactiveDialog(this,
                        "",
                        "¿Deseas cerrar sesión?",
                        "Cerrar Sesión",
                        "Cancelar",
                        AppCompatResources.getDrawable(this, R.drawable.outline_sign_out),
                        (dialogInterface, i) -> {
                            Firebase.signOut(this);
                        },
                        (dialogInterface, i) -> {});
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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