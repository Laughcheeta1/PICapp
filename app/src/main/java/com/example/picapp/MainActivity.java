package com.example.picapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.Username);
        TextView password = (TextView) findViewById(R.id.Password);

        Button loginButton = (Button) findViewById(R.id.LogInButton);
        Button register = (Button) findViewById(R.id.Register);

        //both password and username are admin
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    //correc
                    Toast.makeText(MainActivity.this, "Log In Succesfull", Toast.LENGTH_SHORT).show();
                    openMainPage();
                } else {
                    //incorrect
                    Toast.makeText(MainActivity.this, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterPage();
            }
        });

    }


    public void openMainPage(){
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    public void openRegisterPage(){
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }

}