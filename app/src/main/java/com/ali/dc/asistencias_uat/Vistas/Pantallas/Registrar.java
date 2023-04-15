package com.ali.dc.asistencias_uat.Vistas.Pantallas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.ali.dc.asistencias_uat.Controlador.Firebase.MetodosFirebase;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Vistas.Utilerias.MetodosVistas;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class Registrar extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnCloseUp;
    private LinearLayout secOptionLty, loginLyt;
    private CardView cardView;
    public static TextInputLayout etMailLyt, etPasswordLyt;
    public static TextInputEditText etMail, etPassword;
    private Button btnSignUp, btnGoogleSignUp, btnClose;
    private AppBarLayout appBarLayout;
    private MaterialToolbar toolbarLyt;
    private MaterialTextView cardTitle;
    private WindowInsetsControllerCompat windowInsetsController;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        SharedPreferences prefs = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        Boolean dialogShown = prefs.getBoolean("dialogShown", false);
        windowInsetsController = MetodosVistas.initWindowController(Registrar.this);

        if(!dialogShown){
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
            setContentView(R.layout.signup_warning);
            btnCloseUp = findViewById(R.id.btnCloseUp);
            btnClose = findViewById(R.id.btnClose);
            btnCloseUp.setOnClickListener(this);
            btnClose.setOnClickListener(this);
            prefs.edit().putBoolean("dialogShown",true).commit();
        } else {

            setContentView(R.layout.activity_registrar);
            setWidgets();
            btnSignUp.setOnClickListener(this);

        }

    }

    private void setWidgets() {
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar2);
        toolbarLyt = (MaterialToolbar) findViewById(R.id.toolbar2);
        cardView = (CardView) findViewById(R.id.cardView2);
        secOptionLty = (LinearLayout) findViewById(R.id.secOptionLyt2);
        loginLyt = (LinearLayout) findViewById(R.id.loginLyt2);
        etMailLyt = (TextInputLayout) findViewById(R.id.etMailLyt2);
        etPasswordLyt = (TextInputLayout) findViewById(R.id.etPasswordLyt2);
        etMail = (TextInputEditText) findViewById(R.id.etMail2);
        etPassword = (TextInputEditText) findViewById(R.id.etPassword2);
        btnSignUp = (Button) findViewById(R.id.btnSignUp2);
        btnGoogleSignUp = (Button) findViewById(R.id.btnGoogleSignUp2);
        cardTitle = (MaterialTextView) findViewById(R.id.cardTitle2);
        setSupportActionBar(toolbarLyt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCloseUp:
                goToSignInLayout(windowInsetsController);
                break;
            case R.id.btnClose:
                goToSignInLayout(windowInsetsController);
                break;
            case R.id.btnSignUp2:
                signUpNewUser();
                break;
        }
    }

    private void goToSignInLayout(WindowInsetsControllerCompat windowInsetsController) {
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_registrar, (ViewGroup) findViewById(R.id.activity_registrar));
        layout.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        setContentView(layout);
        setWidgets();
    }

    private void signUpNewUser() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.etMail2, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.etPassword2, ".{6,}", R.string.invalid_password);
        if (awesomeValidation.validate()) {
            String mail = etMail.getText().toString();
            String password = etPassword.getText().toString();
            MetodosFirebase.signUp(this, mail, password);
        } else {
            MetodosVistas.toast(this, "Complete los campos.", 2);
        }
    }
}