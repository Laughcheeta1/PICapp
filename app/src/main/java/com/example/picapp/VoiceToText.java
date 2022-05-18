package com.example.picapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceToText extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    Button goMainPage, iniciarVoice;
    TextView muestraVoice;
    DatabaseReference textoMandarDatos;
    ProgressBar barraDeProgreso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_to_text);

        goMainPage = (Button) findViewById(R.id.VolverMain);
        goMainPage.setOnClickListener(this);

        iniciarVoice = (Button) findViewById(R.id.IniciarVoice);
        iniciarVoice.setOnClickListener(this);

        muestraVoice = (TextView) findViewById(R.id.MuestraVoice);
        barraDeProgreso = (ProgressBar) findViewById(R.id.BarraDeProgreso);

        textoMandarDatos = FirebaseDatabase.getInstance().getReference().child("UserText");



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.VolverMain:
                startActivity(new Intent(this, MainPage.class));
            case R.id.IniciarVoice:
                speak();

        }
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hola, que es lo que quieres trasncribir?");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(VoiceToText.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if(resultCode == RESULT_OK && null!=data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    muestraVoice.setText(result.get(0));

                    barraDeProgreso.setVisibility(View.VISIBLE);
                    textoMandarDatos.push().setValue(result.get(0)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(VoiceToText.this, "Se ha subido el texto a la base de datos", Toast.LENGTH_SHORT).show();
                                barraDeProgreso.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(VoiceToText.this, "No se ha podido subir el texto a la base de datos", Toast.LENGTH_SHORT).show();
                                barraDeProgreso.setVisibility(View.GONE);
                            }

                        }
                    });
                }
                break;
            }
        }
    }

}