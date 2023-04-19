package com.ali.dc.asistencias_uat.Views.Pantallas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import com.ali.dc.asistencias_uat.Controller.Firebase.MetodosFirebase;
import com.ali.dc.asistencias_uat.DataBase.UsersFirebase;
import com.ali.dc.asistencias_uat.Models.Users;
import com.ali.dc.asistencias_uat.R;
import com.ali.dc.asistencias_uat.Views.Utilerias.MetodosVistas;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Registrar extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnCloseUp;
    public static TextInputLayout etMatriculaLyt, etKindUserLyt, etUserNameLyt, etMailLyt, etPasswordLyt;
    public static TextInputEditText etMatricula, etUserName, etMail, etPassword;
    private AutoCompleteTextView listKindsUsersLyt;
    private Button btnSignUp, btnGoogleSignUp, btnClose;
    private MaterialToolbar toolbarLyt;
    private WindowInsetsControllerCompat windowInsetsController;
    private AwesomeValidation awesomeValidation;
    private String kindUser;
    public static final String TAG = "MI ETIQUETA ==>";
    public static Boolean bandSignUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        SharedPreferences prefs = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        Boolean dialogShown = prefs.getBoolean("dialogShown", false);
        windowInsetsController = MetodosVistas.initWindowController(Registrar.this);


        if(!dialogShown){
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
            setContentView(R.layout.tool_signup_warning);
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

    @Override
    protected void onResume() {
        super.onResume();
        String[] kindsUsers = getResources().getStringArray(R.array.kindUsers);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                kindsUsers);
        listKindsUsersLyt.setAdapter(arrayAdapter);
    }

    private void setWidgets() {
        toolbarLyt = findViewById(R.id.toolbar2);
        etUserNameLyt = findViewById(R.id.etUserNameLyt);
        etMatriculaLyt = findViewById(R.id.etMatriculaLyt);
        etKindUserLyt = findViewById(R.id.etKindUserLyt);
        etMailLyt = findViewById(R.id.etMailLyt2);
        etPasswordLyt = findViewById(R.id.etPasswordLyt2);
        etMatricula = findViewById(R.id.etMatricula);
        etUserName = findViewById(R.id.etUserName);
        etMail = findViewById(R.id.etMail2);
        etPassword = findViewById(R.id.etPassword2);
        listKindsUsersLyt = findViewById(R.id.listKindsUsersLyt);
        btnSignUp = findViewById(R.id.btnSignUp2);
        btnGoogleSignUp = findViewById(R.id.btnGoogleSignUp2);
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
            case R.id.listKindsUsersLyt:
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
        if (validarCampos()) {
            Long matricula = Long.valueOf(etMatricula.getText().toString());
            String userName = etUserName.getText().toString();
            String mail = etMail.getText().toString();
            String password = etPassword.getText().toString();
            kindUser = listKindsUsersLyt.getText().toString();
            Users user = new Users(matricula, userName, mail, password, kindUser);
            Log.d(TAG, user.toString());
            MetodosFirebase.signUp(this, user);
            Log.d("MI ETIQUETA AL FINALIZAR EL METODO ==>", ""+bandSignUp);
            if (bandSignUp){
                MetodosVistas.basicDialog(this,"Usurio creado con exito",
                        "Es necesario verificar la cuenta en su correo electronico regtistrado para poder iniciar sesión.",
                        "De acuerdo",
                        AppCompatResources.getDrawable(this, R.drawable.outline_check_circle));
            }
        }
    }

    private boolean validarCampos() {
        Boolean band = true;
        if (etMatricula.getText().toString().isEmpty()){
            etMatriculaLyt.setError("Matricula necesaria");
            band = false;
        } else if (etMatricula.getText().toString().length() != 10) {
            etMatriculaLyt.setError("La matricula invalida");
            band = false;
        } else {
            etMatriculaLyt.setError(null);
        }
        if (listKindsUsersLyt.getText().toString().isEmpty()){
            etKindUserLyt.setError("Seleccione un tipo de usuario");
            band = false;
        } else {
            etKindUserLyt.setError(null);
        }
        if (etUserName.getText().toString().isEmpty()){
            etUserNameLyt.setError("Nombre de usuario necesario");
            band = false;
        } else {
            etUserNameLyt.setError(null);
        }
        if (etMail.getText().toString().isEmpty()){
            etMailLyt.setError("Correo electronico necesario");
            band = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etMail.getText().toString()).matches()){
            etMailLyt.setError("Correo electronico inválido");
            band = false;
        } else {
            etMailLyt.setError(null);
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPasswordLyt.setError("Contraseña necesaria");
            band = false;
        } else if (etPassword.getText().toString().length() != 8) {
            etPasswordLyt.setError("Contraseña debe tener 8 caracteres");
            band = false;
        } else {
            etPasswordLyt.setError(null);
        }

        return band;
    }

    public static void setBandSignUp(Boolean bandSignUpReturn) {
        Log.d("bandSignUpReturn", "" + bandSignUpReturn);
        bandSignUp = bandSignUpReturn;
    }

}