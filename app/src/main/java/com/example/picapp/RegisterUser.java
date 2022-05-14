package com.example.picapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView buttonRegister, banner;
    private FirebaseAuth mAuth;
    private EditText Correo, Contrasena, ConfirmarContrasena;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        banner = (TextView) findViewById(R.id.Titulo);
        banner.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        buttonRegister = (Button) findViewById(R.id.confirmacion);
        buttonRegister.setOnClickListener(this);

        Correo = (EditText) findViewById(R.id.Correo);
        Contrasena = (EditText) findViewById(R.id.Contrasena);
        ConfirmarContrasena = (EditText) findViewById(R.id.ConfirmarContrasena);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Titulo:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.confirmacion:
                regiterUser();

        }
    }

    private void regiterUser(){
        String email = Correo.getText().toString().trim();
        String contrasena = Contrasena.getText().toString().trim();
        String confirmarContrasena = ConfirmarContrasena.getText().toString().trim();

        if(email.isEmpty()){
            Correo.setError("Se requiere el nombre completo");
            Correo.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Correo.setError("Por favor ingrese un correo valido");
            Correo.requestFocus();
            return;
        }
        if (contrasena.isEmpty()){
            Contrasena.setError("Se requiere una contraseña");
            Contrasena.requestFocus();
            return;
        } else if (contrasena.length() < 6){
            Contrasena.setError("La contraseña debe de contar con minimo 6 caracteres");
            Contrasena.requestFocus();
            return;
        }
        if (confirmarContrasena.isEmpty()){
            ConfirmarContrasena.setError("Se requiere confirmar la contraseña");
            ConfirmarContrasena.requestFocus();
            return;
        } else if(!confirmarContrasena.equals(contrasena)){
            ConfirmarContrasena.setError("Las contraseñas no son iguales");
            ConfirmarContrasena.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,contrasena)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Usuario user = new Usuario(email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "El usuario ha sido registrado", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                        //Devolverlo al LOG IN
                                    } else {
                                        Toast.makeText(RegisterUser.this, "No se pudo registrar, intentalo de nuevo", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterUser.this, "No se pudo registrar, intentalo de nuevo", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });


    }

}