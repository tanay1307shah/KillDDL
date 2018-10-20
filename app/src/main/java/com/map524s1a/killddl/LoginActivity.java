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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private Button fb_loginButton;
    private Button loginBtn;
    private EditText email;
    private EditText passwd;
    private Button registerBtn;

    private String emailAdd;
    private String Password;
    private CallbackManager callbackManager;


    private static final String EMAIL = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fb_loginButton = findViewById(R.id.fb);
        loginBtn = findViewById(R.id.LoginBtn);
        registerBtn = findViewById(R.id.register);
        email = findViewById(R.id.email);
        passwd = findViewById(R.id.pswd);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginWithFB();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAdd = email.getText().toString();
                Password = passwd.getText().toString();

                if(emailAdd.equalsIgnoreCase("killddl@usc.edu") && Password.equalsIgnoreCase("wegotit")){
                    Intent i = new Intent(getApplicationContext(),MainViewActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Wrong Combination\n" +emailAdd.toString() + "\n" + Password.toString() , Toast.LENGTH_SHORT).show();
                }
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

        //fb_loginButton
    }

    private void loginWithFB(){
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getApplicationContext(),loginResult.getAccessToken().getUserId(), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(),MainViewActivity.class);
                        Log.d("FUCK","lolololloo");
                        startActivity(i);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
