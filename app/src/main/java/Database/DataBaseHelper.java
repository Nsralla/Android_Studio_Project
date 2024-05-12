package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ObjectClasses.Client;
import ObjectClasses.Favorite;
import ObjectClasses.User;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE FavoritePizzas(" +
                "CUSTOMER_EMAIL TEXT, " +
                "PIZZA_TYPE TEXT, " +
                "PIZZA_SIZE TEXT, " +
                "PIZZA_PRICE REAL, " +
                "PIZZA_CATEGORY TEXT, " +
                "PRIMARY KEY (CUSTOMER_EMAIL, PIZZA_TYPE, PIZZA_SIZE, PIZZA_CATEGORY), " +
                "FOREIGN KEY (CUSTOMER_EMAIL) REFERENCES Clients(EMAIL))");
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


    public ArrayList<Favorite> getAllFavoritesForCustomer(String customerEmail) {
        ArrayList<Favorite> favorites = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PIZZA_TYPE, PIZZA_SIZE, PIZZA_PRICE, PIZZA_CATEGORY  FROM FavoritePizzas WHERE CUSTOMER_EMAIL = ?", new String[] { customerEmail.trim() });
        if(cursor != null && cursor.moveToNext()){
            System.out.println("CURSOR NOT NULL");
            int pizzaTypeIndex = cursor.getColumnIndex("PIZZA_TYPE");
            int pizzaPriceIndex = cursor.getColumnIndex("PIZZA_PRICE");
            int pizzaSizeIndex = cursor.getColumnIndex("PIZZA_SIZE");
            int pizzaCategoryIndex = cursor.getColumnIndex("PIZZA_CATEGORY");
            if (pizzaSizeIndex !=-1 && pizzaPriceIndex!=-1 && pizzaTypeIndex!=-1 && pizzaCategoryIndex !=-1 ) {
                do {
                    System.out.println("PIZZA FOUND");
                    String pizzaType = cursor.getString(pizzaTypeIndex);
                    String pizzaSize = cursor.getString(pizzaSizeIndex);
                    double pizzaPrice = cursor.getDouble(pizzaPriceIndex);
                    String pizzaCategory = cursor.getString(pizzaCategoryIndex);
                    favorites.add(new Favorite(customerEmail, pizzaType, pizzaSize, pizzaPrice, pizzaCategory));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return favorites;
    }

    public void addFavorite(String customerEmail, String pizzaType, String pizzaSize, double pizzaPrice, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CUSTOMER_EMAIL", customerEmail);
        values.put("PIZZA_TYPE", pizzaType);
        values.put("PIZZA_SIZE", pizzaSize);
        values.put("PIZZA_PRICE", pizzaPrice);
        values.put("PIZZA_CATEGORY", category);
        // Inserting Row
        long result = db.insert("FavoritePizzas", null, values);
        if (result == -1) {
            System.out.println("Insertion failed");
        } else {
            System.out.println("Insertion successful, row ID = " + result);
        }
        db.close(); // Closing database connection

        // Check for successful insertion
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

    public int updateClientPassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HASHEDPASSWORD", newPassword);  // Assuming the password is already hashed
        // Define the criteria for selecting the specific row
        String selection = "email = ?";
        String[] selectionArgs = { email };

        // Perform the update on the database table
        int count = db.update("Clients", values, selection, selectionArgs);
        db.close();
        return count;  // Return the number of rows affected
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
        db.execSQL("DROP TABLE IF EXISTS Clients");
        db.execSQL("DROP TABLE IF EXISTS Admins ");
        db.execSQL("DROP TABLE IF EXISTS FavoritePizzas");

        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Create table for Favorite Pizzas
        db.execSQL("CREATE TABLE FavoritePizzas(" +
                "CUSTOMER_EMAIL TEXT, " +
                "PIZZA_TYPE TEXT, " +
                "PIZZA_SIZE TEXT, " +
                "PIZZA_PRICE REAL, " +
                "PIZZA_CATEGORY TEXT, " +
                "PRIMARY KEY (CUSTOMER_EMAIL, PIZZA_TYPE, PIZZA_SIZE, PIZZA_CATEGORY), " +
                "FOREIGN KEY (CUSTOMER_EMAIL) REFERENCES Clients(EMAIL))");
    }
}
