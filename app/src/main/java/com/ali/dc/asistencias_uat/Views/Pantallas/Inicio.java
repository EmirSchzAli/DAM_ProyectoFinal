package com.ali.dc.asistencias_uat.Views.Pantallas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.GravityCompat;
import androidx.core.view.WindowCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ali.dc.asistencias_uat.Controller.Firebase.MetodosFirebase;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Views.Pantallas.Fragments.Menu.Asistencias;
import com.ali.dc.asistencias_uat.Views.Pantallas.Fragments.Menu.Home;
import com.ali.dc.asistencias_uat.Views.Utilerias.MetodosVistas;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

public class Inicio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public static MaterialToolbar toolbar;
    private NavigationView navigationDrawer;
    private TextView userNameTextHeaderNav, userMailTextHeaderNav;
    private View headerNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.tool_nav_drawer_layout);
        FirebaseUser user = MetodosFirebase.firebaseAuth.getCurrentUser();
        String userName = user.getDisplayName();
        String userMail = user.getEmail();

        toolbar = (MaterialToolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        setTitle("Inicio");

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationDrawer = findViewById(R.id.navigationDrawer);

        navigationDrawer.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        headerNavView =  navigationDrawer.getHeaderView(0);
        userNameTextHeaderNav = (TextView) headerNavView.findViewById(R.id.userNameTextHeaderNav);
        userMailTextHeaderNav = (TextView) headerNavView.findViewById(R.id.userMailTextHeaderNav);
        userNameTextHeaderNav.setText(userName);
        userMailTextHeaderNav.setText(userMail);



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Home()).commit();
            navigationDrawer.setCheckedItem(R.id.nav_home);
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
                MetodosVistas.interactiveDialog(this,
                        "",
                        "¿Deseas cerrar sesión?",
                        "Cerrar Sesión",
                        "Cancelar",
                        AppCompatResources.getDrawable(this, R.drawable.outline_sign_out),
                        (dialogInterface, i) -> {
                            MetodosFirebase.signOut(this);
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