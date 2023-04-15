package com.ali.dc.asistencias_uat.Vistas.Pantallas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.WindowCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ali.dc.asistencias_uat.Controlador.Firebase.MetodosFirebase;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Vistas.Utilerias.MetodosVistas;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private MaterialToolbar toolbar;
    private ExtendedFloatingActionButton fab_registrar;
    private CardView cardView;
    private LinearLayout secOptionLty, loginLyt;
    public static TextInputLayout etMailLyt, etPasswordLyt;
    public static TextInputEditText etMail, etPassword;
    private Button btnLogin, btnGoogleLogin;
    private MaterialTextView cardTitle;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_login);

        FirebaseUser user = MetodosFirebase.firebaseAuth.getCurrentUser();
        if (user!= null) {
            goToHomePage();
        }

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        fab_registrar = (ExtendedFloatingActionButton) findViewById(R.id.fab_registrar);
        cardView = (CardView) findViewById(R.id.cardView);
        secOptionLty = (LinearLayout) findViewById(R.id.secOptionLyt);
        loginLyt = (LinearLayout) findViewById(R.id.loginLyt);
        etMailLyt = (TextInputLayout) findViewById(R.id.etMailLyt);
        etPasswordLyt = (TextInputLayout) findViewById(R.id.etPasswordLyt);
        etMail = (TextInputEditText) findViewById(R.id.etMail);
        etPassword = (TextInputEditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnGoogleLogin = (Button) findViewById(R.id.btnGoogleLogin);
        cardTitle = (MaterialTextView) findViewById(R.id.cardTitle);

        fab_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Rellenar los campos", Snackbar.LENGTH_SHORT)
                        .setAnchorView(R.id.fab_registrar)
                        .show();*/
                goToSigninPage();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        etMailLyt.setError(null);
        etPasswordLyt.setError(null);
        if (!etMail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
            String mail = etMail.getText().toString();
            String password = etPassword.getText().toString();
            MetodosFirebase.logIn(this, mail, password);
        } else {
            MetodosVistas.toast(this, "Complete los campos.", 2);
        }
    }

    private void goToSigninPage() {
        Intent intent = new Intent(Login.this, Registrar.class);
        Pair[] pairs = new Pair[10];
        pairs[0] = new Pair<View, String>(cardView, "cardTransition");
        pairs[1] = new Pair<View, String>(etMailLyt, "emailInputTransition");
        pairs[2] = new Pair<View, String>(etPasswordLyt, "passwordInputTransition");
        pairs[3] = new Pair<View, String>(btnLogin, "buttonLoginTransition");
        pairs[4] = new Pair<View, String>(appBarLayout, "toolBarTransition");
        pairs[5] = new Pair<View, String>(cardTitle, "cardTitleTransition");
        pairs[6] = new Pair<View, String>(btnGoogleLogin, "buttonLoginGoogleTransition");
        pairs[7] = new Pair<View, String>(secOptionLty, "secondOptionTransition");
        pairs[8] = new Pair<View, String>(loginLyt, "loginLayoutTransition");
        pairs[9] = new Pair<View, String>(toolbar, "toolBarTransition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent, options.toBundle());
        //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void goToHomePage() {
        Intent intent = new Intent(Login.this, Inicio.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
            | Intent.FLAG_ACTIVITY_NEW_TASK
            | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}