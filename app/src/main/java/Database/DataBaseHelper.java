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
import ObjectClasses.PizzaType;
import ObjectClasses.SpecialOffer;
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
                "GENDER TEXT," +
                "PROFILE_PICTURE TEXT" +
                ")" );

        // SQL statement to create an 'Order' table with IsOffer column
        db.execSQL("CREATE TABLE IF NOT EXISTS Orders (" +
                "OrderID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CustomerEmail TEXT, " +
                "TotalQuantity INTEGER, " +
                "OrderDateTime TEXT, " +
                "TotalPrice REAL, " +
                "IsOffer INTEGER DEFAULT 0, " +
                "FOREIGN KEY (CustomerEmail) REFERENCES Clients(EMAIL))");

        // SQL statement to create a 'Pizzas' table
        db.execSQL("CREATE TABLE IF NOT EXISTS Pizzas (" +
                "PizzaID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "OrderID INTEGER, " +
                "PizzaType TEXT, " +
                "PizzaSize TEXT, " +
                "PizzaPrice REAL, " +
                "Quantity INTEGER,"+
                "Category TEXT,"+
                "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID))");

        db.execSQL("CREATE TABLE IF NOT EXISTS SpecialOffers (" +
                "OfferID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "StartingOfferDate Text, " +
                "EndingOfferDate TEXT, " +
                "TotalPrice REAL)");

        // Create SpecialOfferPizzas table
        db.execSQL("CREATE TABLE IF NOT EXISTS SpecialOfferPizzas (" +
                "SpecialOfferPizzaID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "OfferID INTEGER, " +
                "PizzaType TEXT, " +
                "PizzaSize TEXT, " +
                "Quantity INTEGER,"+
                "FOREIGN KEY (OfferID) REFERENCES SpecialOffers(OfferID))");

    }

    public Cursor getOrdersIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT P.PizzaType, COUNT(*), SUM(P.PizzaPrice * P.Quantity) AS TotalIncome " +
                "FROM Orders O " +
                "JOIN Pizzas P ON O.OrderID = P.OrderID " +
                "WHERE O.IsOffer = 0 " +
                "GROUP BY P.PizzaType " +
                "UNION ALL " +
                "SELECT 'Offer' AS PizzaType, COUNT(*), SUM(O.TotalPrice) AS TotalIncome " +
                "FROM Orders O " +
                "WHERE O.IsOffer = 1 " +
                "GROUP BY O.IsOffer";
        return db.rawQuery(query, null);
    }


    public String getProfilePicture(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Clients", new String[]{"PROFILE_PICTURE"}, "EMAIL = ?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int imageIndex = cursor.getColumnIndex("PROFILE_PICTURE");
            String imagePath = cursor.getString(imageIndex);
            cursor.close();
            db.close();
            return imagePath;
        } else {
            db.close();
            return null;
        }
    }

    public void updateProfilePicture(String email, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PROFILE_PICTURE", imagePath);
        db.update("Clients", values, "EMAIL = ?", new String[]{email});
        db.close();
    }

    public ArrayList<SpecialOffer> getAllOffers() {
        ArrayList<SpecialOffer> specialOffers = new ArrayList<>();
        String selectOffersQuery = "SELECT * FROM SpecialOffers";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor offersCursor = db.rawQuery(selectOffersQuery, null);

        if (offersCursor.moveToFirst()) {
            int offerIdIndex = offersCursor.getColumnIndex("OfferID");
            int startIndex = offersCursor.getColumnIndex("StartingOfferDate");
            int endIndex = offersCursor.getColumnIndex("EndingOfferDate");
            int priceIndex = offersCursor.getColumnIndex("TotalPrice");

            do {
                SpecialOffer specialOffer = new SpecialOffer();
                int offerId = offersCursor.getInt(offerIdIndex);
                specialOffer.setStartingOfferDate(offersCursor.getString(startIndex));
                specialOffer.setEndingOfferDate(offersCursor.getString(endIndex));
                specialOffer.setTotalPrice(offersCursor.getDouble(priceIndex));

                // Fetch associated pizzas
                String selectPizzasQuery = "SELECT * FROM SpecialOfferPizzas WHERE OfferID = ?";
                Cursor pizzasCursor = db.rawQuery(selectPizzasQuery, new String[]{String.valueOf(offerId)});

                ArrayList<PizzaType> pizzas = new ArrayList<>();
                if (pizzasCursor.moveToFirst()) {
                    int pizzaTypeIndex = pizzasCursor.getColumnIndex("PizzaType");
                    int pizzaSizeIndex = pizzasCursor.getColumnIndex("PizzaSize");
                    int pizzaQuantityIndex = pizzasCursor.getColumnIndex("Quantity");
                    do {
                        String pizzaType = pizzasCursor.getString(pizzaTypeIndex);
                        String pizzaSize = pizzasCursor.getString(pizzaSizeIndex);
                        int pizzaQuantity = pizzasCursor.getInt(pizzaQuantityIndex);
                        pizzas.add(new PizzaType(pizzaType, pizzaSize, 0, pizzaQuantity)); // Assuming price is not relevant here
                    } while (pizzasCursor.moveToNext());
                }
                pizzasCursor.close();

                specialOffer.setPizzas(pizzas);
                specialOffers.add(specialOffer);
            } while (offersCursor.moveToNext());
        }
        offersCursor.close();
        db.close();
        return specialOffers;
    }

    public void addSpecialOffer(Context context,SpecialOffer specialOffer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("StartingOfferDate", specialOffer.getStartingOfferDate());
        values.put("EndingOfferDate", specialOffer.getEndingOfferDate());
        values.put("TotalPrice", specialOffer.getTotalPrice());
        long offerId = db.insert("SpecialOffers", null, values);

        // Insert the new row, returning the primary key value of the new row
        if (offerId == -1) {
            Toast.makeText(context, "Failed to add Special offer.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Insertion successful.", Toast.LENGTH_SHORT).show();
        }

        for (PizzaType pizza : specialOffer.getPizzas()) {
            ContentValues pizzaValues = new ContentValues();
            pizzaValues.put("OfferID", offerId);
            pizzaValues.put("PizzaType", pizza.getPizzaType());
            pizzaValues.put("PizzaSize", pizza.getSize());
            pizzaValues.put("Quantity", pizza.getQuantity());
            db.insert("SpecialOfferPizzas", null, pizzaValues);
        }
    }


    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Orders", null);

        if (cursor.moveToFirst()) {
            int orderIdIndex = cursor.getColumnIndex("OrderID");
            int customerEmailIndex = cursor.getColumnIndex("CustomerEmail");
            int totalQuantityIndex = cursor.getColumnIndex("TotalQuantity");
            int orderDateTimeIndex = cursor.getColumnIndex("OrderDateTime");
            int totalPriceIndex = cursor.getColumnIndex("TotalPrice");
            while (!cursor.isAfterLast()) {
                if (orderIdIndex != -1 && customerEmailIndex != -1 && totalQuantityIndex != -1 &&
                        orderDateTimeIndex != -1 && totalPriceIndex != -1) {
                    int orderId = cursor.getInt(orderIdIndex);
                    String customerEmail = cursor.getString(customerEmailIndex);
                    int totalQuantity = cursor.getInt(totalQuantityIndex);
                    String orderDateTime = cursor.getString(orderDateTimeIndex);
                    double totalPrice = cursor.getDouble(totalPriceIndex);
                    // get pizzas for this order
                    ArrayList<PizzaType> pizzas = getPizzasForOrder(orderId, db);

                    Order order = new Order(customerEmail, pizzas, totalQuantity, orderDateTime, totalPrice);
                    orders.add(order);

                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return orders;
    }

    private ArrayList<PizzaType> getPizzasForOrder(int orderId, SQLiteDatabase db) {
        ArrayList<PizzaType> pizzas = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Pizzas WHERE OrderID = ?", new String[]{String.valueOf(orderId)});

        if (cursor.moveToFirst()) {
            int pizzaTypeIndex = cursor.getColumnIndex("PizzaType");
            int pizzaSizeIndex = cursor.getColumnIndex("PizzaSize");
            int pizzaPriceIndex = cursor.getColumnIndex("PizzaPrice");
            int quantityIndex = cursor.getColumnIndex("Quantity");
            int categoryIndex = cursor.getColumnIndex("Category");

            while (!cursor.isAfterLast()) {
                if (pizzaTypeIndex != -1 && pizzaSizeIndex != -1 && pizzaPriceIndex != -1 && quantityIndex != -1) {
                    String pizzaType = cursor.getString(pizzaTypeIndex);
                    String pizzaSize = cursor.getString(pizzaSizeIndex);
                    double pizzaPrice = cursor.getDouble(pizzaPriceIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    String category = cursor.getString(categoryIndex);

                    pizzas.add(new PizzaType(pizzaType, pizzaSize, pizzaPrice, quantity, category));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        return pizzas;
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

    public ArrayList<String> getAllCustomersEmails() {
        ArrayList<String> emails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Clients", new String[] {"EMAIL"}, null, null, null, null, null);

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
        Cursor cursor = db.rawQuery("SELECT * FROM Orders WHERE CustomerEmail = ?", new String[]{email});

        if (cursor.moveToFirst()) {
            int orderIdIndex = cursor.getColumnIndex("OrderID");
            int customerEmailIndex = cursor.getColumnIndex("CustomerEmail");
            int totalQuantityIndex = cursor.getColumnIndex("TotalQuantity");
            int orderDateTimeIndex = cursor.getColumnIndex("OrderDateTime");
            int totalPriceIndex = cursor.getColumnIndex("TotalPrice");

            while (!cursor.isAfterLast()) {
                if (orderIdIndex != -1 && customerEmailIndex != -1 && totalQuantityIndex != -1 &&
                        orderDateTimeIndex != -1 && totalPriceIndex != -1) {

                    int orderId = cursor.getInt(orderIdIndex);
                    String customerEmail = cursor.getString(customerEmailIndex);
                    int totalQuantity = cursor.getInt(totalQuantityIndex);
                    String orderDateTime = cursor.getString(orderDateTimeIndex);
                    double totalPrice = cursor.getDouble(totalPriceIndex);

                    // Get pizzas for this order
                    ArrayList<PizzaType> pizzas = getPizzasForOrder(orderId, db);

                    Order order = new Order(customerEmail, pizzas, totalQuantity, orderDateTime, totalPrice);
                    orders.add(order);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return orders;
    }


    public boolean addOrder(Order order, int isOffer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CustomerEmail", order.getCustomerEmail());
        values.put("TotalQuantity", order.getQuantity());
        values.put("OrderDateTime", order.getOrderDateTime());
        values.put("TotalPrice", order.getTotalPrice());
        if(isOffer == 0)
            values.put("IsOffer",0);
        else
            values.put("IsOffer", 1);

        // Inserting Order
        long orderId = db.insert("Orders", null, values);
        if (orderId == -1) {
            db.close();
            return false;
        }

        // Insert pizzas for the order
        for (PizzaType pizza : order.getPizzas()) {
            ContentValues pizzaValues = new ContentValues();
            pizzaValues.put("OrderID", orderId);
            pizzaValues.put("PizzaType", pizza.getPizzaType());
            pizzaValues.put("PizzaSize", pizza.getSize());
            pizzaValues.put("PizzaPrice", pizza.getPrice());
            pizzaValues.put("Quantity", pizza.getQuantity());
            pizzaValues.put("Category", pizza.getCategory());

            db.insert("Pizzas", null, pizzaValues);
        }

        db.close(); // Closing database connection
        return true;
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
        contentValues.put("PROFILE_PICTURE", "");
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
        db.execSQL("DROP TABLE IF EXISTS Pizzas");
        db.execSQL("DROP TABLE IF EXISTS SpecialOffers");
        db.execSQL("DROP TABLE IF EXISTS SpecialOfferPizzas");


        onCreate(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
