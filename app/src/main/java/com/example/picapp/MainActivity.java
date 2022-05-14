package com.example.picapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView forgotPassword;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button register,loginButton;
    EditText correo;
    EditText contrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = (EditText) findViewById(R.id.Correo);
        contrasena = (EditText) findViewById(R.id.Password);

        loginButton = (Button) findViewById(R.id.LogInButton);
        loginButton.setOnClickListener(this);



        register = (Button) findViewById(R.id.Register);
        register.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = (TextView) findViewById(R.id.ForgotPassword);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.Register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.LogInButton:
                userLogin();
                break;
            case R.id.ForgotPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = correo.getText().toString().trim();
        String contrasena = this.contrasena.getText().toString().trim();

        if (email.isEmpty()) {
            correo.setError("El correo es requerido");
            correo.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            correo.setError("Por favor entre un correo valido");
            correo.requestFocus();
            return;
        }
        if (contrasena.isEmpty()) {
            this.contrasena.setError("La contraseña es requerida");
            this.contrasena.requestFocus();
            return;
        } else if (contrasena.length() < 6){
            this.contrasena.setError("Las contraseñas tiene como minimo 6 caracteres");
            this.contrasena.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, MainPage.class));

                }else{
                    Toast.makeText(MainActivity.this, "No se pudo iniciar con exito, intente de nuevo", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}