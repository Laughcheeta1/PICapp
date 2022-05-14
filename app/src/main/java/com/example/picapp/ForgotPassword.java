package com.example.picapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = (EditText) findViewById(R.id.TextEditEmail);

        resetPasswordButton = (Button) findViewById(R.id.restablecer);
        resetPasswordButton.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        resetPasword();
    }

    private void resetPasword() {
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("El correo es requerido");
            emailEditText.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Por favor entre un correo valido");
            emailEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPassword.this, "Se ha enviado te ha enviado un correo para restablecer la contraseña", Toast.LENGTH_LONG).show();
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPassword.this, "No se pudo completar la accion, trata de nuevo", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}