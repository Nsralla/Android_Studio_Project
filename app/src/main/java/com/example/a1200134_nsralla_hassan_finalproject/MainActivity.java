package com.example.a1200134_nsralla_hassan_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import APIConnection.FetchPizzaTypes;
import Database.DataBaseHelper;
import Hashing.Hash;
import ObjectClasses.Admin;

public class MainActivity extends AppCompatActivity  {
    Button getStartedButton;

    //TODO: SPECIAL OFFER MUST EXISTS ON THE PIZZA LIST ALSO.
    // NO TWO USERS WITH THE SAME EMAIL
    // CHECK THE HOUR FOR THE ORDERS(when user place an order) (i REMEmBER ERROR IN THE HOUR FOR ORDER FROM SPECIAL OFFER ORDERS).
    // when there is an offer, and hasn't started yet, disable the order button
    // the profile picture

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         get the get started button
        getStartedButton = findViewById(R.id.get_started_button);
        DataBaseHelper dataBaseHelper =new DataBaseHelper(MainActivity.this,"1200134_nsralla_hassan_finalProject",null,1);
//        dataBaseHelper.resetDatabase();

        //Insert admin user
        if(needToAddAdmin(dataBaseHelper)){
            insertAdmin(dataBaseHelper);
        }

        // add event listener to the button
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // connect to API
                new FetchPizzaTypes(MainActivity.this, getStartedButton).execute();
            }
        });

    }

    private boolean needToAddAdmin(DataBaseHelper db){
        // Check if the admin exists,we can check by email
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT EMAIL FROM Admins WHERE EMAIL = ?", new String[]{"admin@example.com"});
        boolean exists = cursor.getCount()>0;
        cursor.close();
        return  !exists;
    }

    private void insertAdmin(DataBaseHelper db){
        Admin admin = new Admin("nsralla","hassan","admin@example.com","0594693082", Hash.hashPassword("jj137157177jj"),"Male");
        db.insertAdmin(admin, MainActivity.this);
    }





}