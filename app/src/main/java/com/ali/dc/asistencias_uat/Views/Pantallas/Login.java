package com.ali.dc.asistencias_uat.Views.Pantallas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.view.WindowCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ali.dc.asistencias_uat.Controller.Firebase.MetodosFirebase;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Views.Utilerias.MetodosVistas;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

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

        if (user != null) {
            goToHomePage();
        }

        appBarLayout = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);
        fab_registrar = findViewById(R.id.fab_registrar);
        cardView = findViewById(R.id.cardView);
        secOptionLty = findViewById(R.id.secOptionLyt);
        loginLyt = findViewById(R.id.loginLyt);
        etMailLyt = findViewById(R.id.etMailLyt);
        etPasswordLyt = findViewById(R.id.etPasswordLyt);
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        cardTitle = findViewById(R.id.cardTitle);

        fab_registrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnGoogleLogin.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_registrar:
                goToSigninPage();
                break;
            case R.id.btnLogin:
                loginUser();
                break;
            case R.id.btnGoogleLogin:
                MetodosVistas.basicDialog(
                        this,
                        getString(R.string.google_unenable_title),
                        getString(R.string.google_unenable_text),
                        "De acuerdo",
                        AppCompatResources.getDrawable(this, R.drawable.google));
                break;
        }
    }
}