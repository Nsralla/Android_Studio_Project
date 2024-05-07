package com.example.a1200134_nsralla_hassan_finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS(EMAIL TEXT PRIMARY KEY" +
                ",FIRSTNAME TEXT,LASTNAME TEXT,HASHEDPASSWORD TEXT," +
                " GENDER TEXT, PHONE TEXT)");
    }

    public void insertUser(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("FIRSTNAME", user.getFirstName());
        contentValues.put("LASTNAME", user.getLastName());
        contentValues.put("PHONE", user.getPhone()); // Ensure the getter method matches your User class.
        contentValues.put("HASHEDPASSWORD", user.getHashedPassword()); // Corrected column name
        contentValues.put("GENDER", user.getGender());
        sqLiteDatabase.insert("USERS", null, contentValues);
    }

    public Cursor getAllUsers(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USERS",null);
    }
    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS USERS");
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
