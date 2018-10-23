package com.map524s1a.killddl;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by tanay on 10/14/2018.
 */

public class RegisterActivity  extends AppCompatActivity{

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    private Button saveBtn;
    private ImageButton imgBtn;
    private EditText name;
    private EditText email;
    private EditText paswd;
    private Spinner genderSpinner;
    private ImageView imgView;
    private String[] genderTypes;
    private String gender;
    private String fName;
    private String emailId;
    private String pwd;
    private String imgUrl = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent i = getIntent();




        saveBtn = findViewById(R.id.saveBtn);
        imgBtn = findViewById(R.id.imageButton);
        name = findViewById(R.id.name);
        email = findViewById(R.id.emailAdd);
        paswd = findViewById(R.id.pswd);
        genderSpinner = findViewById(R.id.genderSpinner);
        imgView = findViewById(R.id.img);

        genderTypes = getResources().getStringArray(R.array.genderArr);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = genderTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions,PERMISSION_CODE);
                }else{
                    pickImageFromGallarey();
                }

            }
        });




        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make user object here and store it to database
                fName = name.getText().toString();
                emailId = email.getText().toString();
                pwd = paswd.getText().toString();


                    if(gender != null && fName != null && emailId != null && pwd != null){

                        joinUserThread jt = new joinUserThread(emailId, pwd, fName, gender, imgUrl, new addUserResponse() {
                            @Override
                            public void addUserCallback(User u) {
                                Intent i = new Intent(getApplicationContext(),MainViewActivity.class);
                                i.putExtra("user",u);
                                startActivity(i);
                            }
                        });




                    }else{
                        Toast.makeText(getApplicationContext(),"Please fill all the data correctly", Toast.LENGTH_LONG).show();
                    }

            }
        });

    }

    private void pickImageFromGallarey() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            Log.d("IMAGE URI" , data.getData().toString());
            imgUrl = data.getData().toString();
            imgView.setImageURI(data.getData());

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallarey();
                }else {
                    Toast.makeText(getApplicationContext(), "Gallarey permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
