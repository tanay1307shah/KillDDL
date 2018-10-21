package com.map524s1a.killddl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private LoginButton fb_loginButton;
    private Button loginBtn;
    private EditText email;
    private EditText passwd;
    private Button registerBtn;

    private String emailAdd;
    private String Password;
    private CallbackManager callbackManager;

    TextView genderTextView;
    TextView nameTextView;
    ImageView imageView;



    private static final String EMAIL = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        callbackManager = CallbackManager.Factory.create();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fb_loginButton = (LoginButton) findViewById(R.id.fb);
        loginBtn = findViewById(R.id.LoginBtn);
        registerBtn = findViewById(R.id.register);
        email = findViewById(R.id.email);
        passwd = findViewById(R.id.pswd);

        FacebookSdk.sdkInitialize(getApplicationContext());

        loginWithFB();

        genderTextView = (TextView)findViewById(R.id.gender_val_pro);
        nameTextView = (TextView) findViewById(R.id.name_val_pro);
        imageView = findViewById(R.id.imageView2);

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
        fb_loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        String accesstoken = loginResult.getAccessToken().getToken();

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {

                                        //Log.v("LoginActivity", response.toString());

                                            storeData(object);


                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields","id,first_name,last_name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();

                        Toast.makeText(getApplicationContext(),loginResult.getAccessToken().getUserId(), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(),MainViewActivity.class);

                        startActivity(i);


                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("myTag", "fb didnt log in");
                    }
                });
    }
    protected void storeData(JSONObject object){
        String id = null;
        try {
            id = object.getString("id");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        URL profile_pic;
        try {
            profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
            Log.i("profile_pic", profile_pic + "");
            //bundle.putString("profile_pic", profile_pic.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (object.has("first_name")){
            String first_name = "a";
            try {
                first_name = object.getString("first_name");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.d("print val ", first_name);
            nameTextView.setText(first_name);
        }
        if (object.has("last_name")){
            try {
                String last_name = object.getString("last_name");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        if (object.has("gender")){
            String gender = "a";
            try {
                gender = object.getString("gender");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.d("print val ", gender);
            //genderTextView.setText(gender);
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
