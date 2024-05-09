package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ObjectClasses.Admin;
import ObjectClasses.Client;
import ObjectClasses.User;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for Admins
        db.execSQL("CREATE TABLE Admins (" +
                "EMAIL TEXT PRIMARY KEY, " +
                "FIRSTNAME TEXT, " +
                "LASTNAME TEXT, " +
                "PHONE TEXT, " +
                "HASHEDPASSWORD TEXT, " +
                "GENDER TEXT)");

        // Create table for Clients
        db.execSQL("CREATE TABLE Clients (" +
                "EMAIL TEXT PRIMARY KEY, " +
                "FIRSTNAME TEXT, " +
                "LASTNAME TEXT, " +
                "PHONE TEXT, " +
                "HASHEDPASSWORD TEXT, " +
                "GENDER TEXT)");
    }


    public void insertClient(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("FIRSTNAME", user.getFirstName());
        contentValues.put("LASTNAME", user.getLastName());
        contentValues.put("PHONE", user.getPhone()); // Ensure the getter method matches your User class.
        contentValues.put("HASHEDPASSWORD", user.getHashedPassword()); // Corrected column name
        contentValues.put("GENDER", user.getGender());
        sqLiteDatabase.insert("Clients", null, contentValues);
    }

    public void updateClient(User user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRSTNAME", user.getFirstName());
        contentValues.put("LASTNAME", user.getLastName());
        contentValues.put("PHONE", user.getPhone());
        contentValues.put("HASHEDPASSWORD", user.getHashedPassword());
        contentValues.put("GENDER", user.getGender());

        // Define the selection criteria (where clause)
        String selection = "EMAIL = ?";
        String[] selectionArgs = { user.getEmail() };

        // Perform the update on the database table
        sqLiteDatabase.update("Clients", contentValues, selection, selectionArgs);
    }

    public void insertAdmin(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("FIRSTNAME", user.getFirstName());
        contentValues.put("LASTNAME", user.getLastName());
        contentValues.put("PHONE", user.getPhone()); // Ensure the getter method matches your User class.
        contentValues.put("HASHEDPASSWORD", user.getHashedPassword()); // Corrected column name
        contentValues.put("GENDER", user.getGender());
        sqLiteDatabase.insert("Admins", null, contentValues);
    }

    public void updateAdmin(Client user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRSTNAME", user.getFirstName());
        contentValues.put("LASTNAME", user.getLastName());
        contentValues.put("PHONE", user.getPhone());
        contentValues.put("HASHEDPASSWORD", user.getHashedPassword());
        contentValues.put("GENDER", user.getGender());

        // Define the selection criteria (where clause)
        String selection = "EMAIL = ?";
        String[] selectionArgs = { user.getEmail() };

        // Perform the update on the database table
        sqLiteDatabase.update("Admins", contentValues, selection, selectionArgs);
    }


    public Cursor getAllUsers(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USERS",null);
    }

    public Cursor getAllAdmins() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM Admins", null);
    }

    public Cursor getAllClients() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM Clients", null);
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
