package com.example.picapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;

public class MainPage extends AppCompatActivity {


    Intent X;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //estamos declarando el nombre que va tenre en el codigo cada boton
        Button VoiceText = (Button) findViewById(R.id.VoiceTextButton);
        Button TextLog = (Button)  findViewById(R.id.TextLog);
        Button SignOut = (Button) findViewById((R.id.SingOut));

        //Aqui va todos los botones
        VoiceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVoiceText();
            }
        });

        TextLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textLog();
            }
        });

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SingOut();
            }
        });
    }

    //Estas son las funciones que habren las otras paginas
    public void openVoiceText(){
        X = new Intent(this, VoiceToText.class);
        startActivity(X);
    }

    public void textLog(){
        X = new Intent(this, HistoricoTextos.class);
        startActivity(X);
    }

    public void SingOut(){
        X = new Intent(this, MainActivity.class);
        startActivity(X);
    }
}