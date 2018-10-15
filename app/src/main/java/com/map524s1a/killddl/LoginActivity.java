package com.map524s1a.killddl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button fb_loginButton;
    private Button loginBtn;
    private EditText email;
    private EditText passwd;
    private Button registerBtn;

    private String emailAdd;
    private String Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fb_loginButton = findViewById(R.id.fb);
        loginBtn = findViewById(R.id.LoginBtn);
        registerBtn = findViewById(R.id.register);
        email = findViewById(R.id.email);
        passwd = findViewById(R.id.pswd);


        fb_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAdd = email.getText().toString();
                Password = passwd.getText().toString();
                Toast.makeText(getApplicationContext(),emailAdd.toString() + "\n" + Password.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                Log.d("FUCK","lolololloo");
                startActivity(i);
            }
        });

    }
}
