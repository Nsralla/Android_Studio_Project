package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import ObjectClasses.Admin;
import ObjectClasses.Client;
import ObjectClasses.Favorite;
import ObjectClasses.Order;
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

        // SQL statement to create an 'Order' table
        db.execSQL("CREATE TABLE IF NOT EXISTS Orders (" +
                "OrderID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CustomerEmail TEXT, " +
                "PizzaType TEXT, " +
                "PizzaSize TEXT, " +
                "PizzaPrice REAL, " +
                "Quantity INTEGER, " +
                "OrderDateTime TEXT, " +
                "TotalPrice REAL, " +
                "FOREIGN KEY (CustomerEmail) REFERENCES Clients(EMAIL))");
    }

    public ArrayList<String> getAllAdminEmails() {
        ArrayList<String> emails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Admins", new String[] {"EMAIL"}, null, null, null, null, null);

        int emailIndex = cursor.getColumnIndex("EMAIL");
        if(emailIndex!=-1){
            if (cursor.moveToFirst()) {
                do {
                    emails.add(cursor.getString(emailIndex));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return emails;
    }

    public ArrayList<Order> getAllOrdersByEmail(String email) {
        ArrayList<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Orders WHERE CustomerEmail = ?", new String[] { email });

        if (cursor.moveToFirst()) {
            int orderIdIndex = cursor.getColumnIndex("OrderID");
            int customerEmailIndex = cursor.getColumnIndex("CustomerEmail");
            int pizzaTypeIndex = cursor.getColumnIndex("PizzaType");
            int pizzaSizeIndex = cursor.getColumnIndex("PizzaSize");
            int pizzaPriceIndex = cursor.getColumnIndex("PizzaPrice");
            int quantityIndex = cursor.getColumnIndex("Quantity");
            int orderDateTimeIndex = cursor.getColumnIndex("OrderDateTime");
            int totalPriceIndex = cursor.getColumnIndex("TotalPrice");
//            int category = cursor.getColumnIndex("")

            while (!cursor.isAfterLast()) {
                if (orderIdIndex != -1 && customerEmailIndex != -1 && pizzaTypeIndex != -1 && pizzaSizeIndex != -1 && pizzaPriceIndex != -1 && quantityIndex != -1 && orderDateTimeIndex != -1 && totalPriceIndex != -1) {
                    int orderId = cursor.getInt(orderIdIndex);
                    String customerEmail = cursor.getString(customerEmailIndex);
                    String pizzaType = cursor.getString(pizzaTypeIndex);
                    String pizzaSize = cursor.getString(pizzaSizeIndex);
                    double pizzaPrice = cursor.getDouble(pizzaPriceIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    String orderDateTime = cursor.getString(orderDateTimeIndex);
                    double totalPrice = cursor.getDouble(totalPriceIndex);

                    orders.add(new Order(orderId, customerEmail, pizzaType, pizzaSize, pizzaPrice, quantity, orderDateTime, totalPrice));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return orders;
    }


    public boolean addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CustomerEmail", order.getCustomerEmail());
        values.put("PizzaType", order.getPizzaType());
        values.put("PizzaSize", order.getPizzaSize());
        values.put("PizzaPrice", order.getPizzaPrice());
        values.put("Quantity", order.getQuantity());
        values.put("OrderDateTime", order.getOrderDateTime());
        values.put("TotalPrice", order.getTotalPrice());

        // Inserting Row
        long result = db.insert("Orders", null, values);
        db.close(); // Closing database connection

        // Check for successful insertion
        return result != -1; // return true if insertion is successful
    }



    public ArrayList<Favorite> getAllFavoritesForCustomer(String customerEmail) {
        ArrayList<Favorite> favorites = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  CUSTOMER_EMAIL, PIZZA_TYPE, PIZZA_SIZE, PIZZA_PRICE, PIZZA_CATEGORY  FROM FavoritePizzas WHERE CUSTOMER_EMAIL = ?", new String[] { customerEmail });
        if(cursor != null && cursor.moveToNext()){
            System.out.println("CURSOR NOT NULL");
            System.out.println("FROM DB, CUSTOMER EMAIL = " + customerEmail);
            int pizzaTypeIndex = cursor.getColumnIndex("PIZZA_TYPE");
            int pizzaPriceIndex = cursor.getColumnIndex("PIZZA_PRICE");
            int pizzaSizeIndex = cursor.getColumnIndex("PIZZA_SIZE");
            int pizzaCategoryIndex = cursor.getColumnIndex("PIZZA_CATEGORY");
            int emailIndex = cursor.getColumnIndex("CUSTOMER_EMAIL");
            if (pizzaSizeIndex !=-1 && pizzaPriceIndex!=-1 && pizzaTypeIndex!=-1 && pizzaCategoryIndex !=-1 ) {
                do {
                    System.out.println("PIZZA FOUND");
                    String pizzaType = cursor.getString(pizzaTypeIndex);
                    String pizzaSize = cursor.getString(pizzaSizeIndex);
                    double pizzaPrice = cursor.getDouble(pizzaPriceIndex);
                    String pizzaCategory = cursor.getString(pizzaCategoryIndex);
                    String email = cursor.getString(emailIndex);
                    favorites.add(new Favorite(email, pizzaType, pizzaSize, pizzaPrice, pizzaCategory));
                } while (cursor.moveToNext());
                for(int i = 0;i<favorites.size();i++){
                    System.out.println("favorite: "+ favorites.get(i).toString());
                }
            }
            cursor.close();
            db.close();
        }
        return favorites;
    }

    public boolean removeFavorite(String userEmail, String pizzaType, String pizzaSize, double pizzaPrice, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("FavoritePizzas",
                "CUSTOMER_EMAIL = ? AND PIZZA_TYPE = ? AND PIZZA_SIZE = ? AND PIZZA_PRICE = ? AND PIZZA_CATEGORY = ?  ",
                new String[] { userEmail, pizzaType, pizzaSize, String.valueOf(pizzaPrice),category });
        db.close();
        return deletedRows > 0;
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

    public void insertAdmin(User user, Context context) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("FIRSTNAME", user.getFirstName());
        contentValues.put("LASTNAME", user.getLastName());
        contentValues.put("PHONE", user.getPhone());
        contentValues.put("HASHEDPASSWORD", user.getHashedPassword());
        contentValues.put("GENDER", user.getGender());

        try {
            long result = sqLiteDatabase.insert("Admins", null, contentValues);
            if (result == -1) { // insert method returns -1 if an error occurred
                Toast.makeText(context, "Failed to add admin.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Admin added successfully!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error adding admin: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }




    public void updateAdmin(Admin user) {
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


//    public Cursor getAllUsers(){
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        return sqLiteDatabase.rawQuery("SELECT * FROM USERS",null);
//    }

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
        db.execSQL("DROP TABLE IF EXISTS Orders");

        onCreate(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Create table for Favorite Pizzas
//        db.execSQL("CREATE TABLE IF NOT EXISTS FavoritePizzas(" +
//                "CUSTOMER_EMAIL TEXT, " +
//                "PIZZA_TYPE TEXT, " +
//                "PIZZA_SIZE TEXT, " +
//                "PIZZA_PRICE REAL, " +
//                "PIZZA_CATEGORY TEXT, " +
//                "PRIMARY KEY (CUSTOMER_EMAIL, PIZZA_TYPE, PIZZA_SIZE, PIZZA_CATEGORY), " +
//                "FOREIGN KEY (CUSTOMER_EMAIL) REFERENCES Clients(EMAIL))");
//
//        if (newVersion > oldVersion) {
//            db.execSQL("CREATE TABLE IF NOT EXISTS Orders (" +
//                    "OrderID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    "CustomerEmail TEXT, " +
//                    "PizzaType TEXT, " +
//                    "PizzaSize TEXT, " +
//                    "PizzaPrice REAL, " +
//                    "Quantity INTEGER, " +
//                    "OrderDateTime TEXT, " +
//                    "TotalPrice REAL, " +
//                    "FOREIGN KEY (CustomerEmail) REFERENCES Clients(EMAIL))");
//        }
    }



}
