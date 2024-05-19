package com.example.a1200134_nsralla_hassan_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Activities.LoginActivity;
import Activities.SigninActivity;
import Database.DataBaseHelper;
import Hashing.Hash;
import ObjectClasses.Client;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText newPasswordText;
    EditText confirmNewPasswordText;
    TextView passwordT;
    Button updatePasswordButton;

    String password;
    String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newPasswordText = findViewById(R.id.newPassword);
        confirmNewPasswordText = findViewById(R.id.confirmNewPassword);
        updatePasswordButton = findViewById(R.id.saveNewPassword);
        passwordT = findViewById(R.id.passwordT);

        SharedPreferences sharedPreferences = ChangePasswordActivity.this.getSharedPreferences("PasswordPrefs", Context.MODE_PRIVATE);
        String loggedInPassword = sharedPreferences.getString("currentLoggedInUserPassword", null);

        passwordT.setText(loggedInPassword);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChangingPassword();
            }
        });
    }

    public void handleChangingPassword(){
        password = newPasswordText.getText().toString();
        confirmPassword = confirmNewPasswordText.getText().toString();

        //TODO: ADD VALIDATION
        // THEN HASH THE PASSWORD
        // THEN UPDATE THE DATABASE
        boolean isValid = validateInputs();
        if(isValid){
            //UPDATE THE SHARED PREFRENCE FOR THE PASSWORD
            SharedPreferences sharedPreferences1 = ChangePasswordActivity.this.getSharedPreferences("PasswordPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            editor1.putString("currentLoggedInUserPassword",password);
            editor1.apply();
            finish();
        }



    }

    public boolean validateInputs(){
        boolean isValid = true;
        StringBuilder errors = new StringBuilder();

        //TODO: VALIDATE THE PASSWORD
        if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            errors.append("Password must be at least 8 characters long and include at least one letter and one number.\n");
            isValid = false;
        }

        //TODO: VALIDATE CONFIRM PASSWORD
        if (!confirmPassword.equals(password)) {
            errors.append("Confirm password does not match the password.\n");
            isValid = false;
        }

        if(!isValid){
            Toast.makeText(this, errors.toString(), Toast.LENGTH_LONG).show();
        }else{
            //TODO: USE SHARED PREFRENCES TO GET THE LOGGED IN EMAIL
            SharedPreferences sharedPreferences = this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
            String loggedInEmail = sharedPreferences.getString("currentLoggedInUserEmail", null);
            String encryptedPassword = Hash.hashPassword(password); //TODO: HASH THE PASSWORD
            //TODO:SAVE THE USER INFO INTO DB
            DataBaseHelper dataBaseHelper =new DataBaseHelper(ChangePasswordActivity.this,"1200134_nsralla_hassan_finalProject",null,1);
            dataBaseHelper.updateClientPassword(loggedInEmail, encryptedPassword);
        }
        return isValid;

    }

}