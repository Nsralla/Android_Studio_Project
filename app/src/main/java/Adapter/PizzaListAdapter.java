package Adapter;

import static android.content.Context.MODE_PRIVATE;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.PrimitiveIterator;

import Database.DataBaseHelper;
import ObjectClasses.Favorite;
import ObjectClasses.Order;
import ObjectClasses.PizzaType;
import androidx.core.content.ContextCompat;

public class PizzaListAdapter extends ArrayAdapter<PizzaType> {

    private Context context;
    private ArrayList<PizzaType> pizzaTypeList;
    private ListView listView;
    String loggedInEmail;
    Dialog dialog;
    Button cancelDialog;
    Button submitDialog;
    Spinner spinnerSize;
    EditText editTextQuantity;

    TextView priceText;
    String[] PIZZA_SIZES = {"Small", "Medium",  "Large"};

    public PizzaListAdapter(Context context, ArrayList<PizzaType> pizzaList, ListView listView) {
        super(context, 0, pizzaList);
        this.context = context;
        this.pizzaTypeList = pizzaList;
        this.listView = listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.pizza_item, parent, false);
        PizzaType pizza = getItem(position);
        TextView pizzaName = convertView.findViewById(R.id.txtPizzaName);
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        loggedInEmail = sharedPreferences.getString("currentLoggedInUserEmail", null);
        Button btnFavorite = convertView.findViewById(R.id.btnFavorite);
        Button btnOrder = convertView.findViewById(R.id.btnOrder);
        pizzaName.setText(pizza.getPizzaType());
        btnFavorite.setOnClickListener(v -> {
            handleAddingPizzaToFavorite(loggedInEmail, pizza.getPizzaType(), pizza.getSize(), pizza.getPrice(), pizza.getCategory());
        });
        btnOrder.setOnClickListener(v -> {
            // Implement order functionality
            handleAddingAnOrder(loggedInEmail,pizza.getPizzaType(), pizza.getSize(), pizza.getPrice(), pizza.getCategory());
        });

        convertView.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_home_layout);
            Bundle bundle = new Bundle();
            bundle.putString("pizzaType", pizza.getPizzaType());
            bundle.putFloat("pizzaPrice", (float) pizza.getPrice());
            bundle.putString("pizzaSize", pizza.getSize());
            bundle.putString("pizzaCategory", pizza.getCategory());
            navController.navigate(R.id.action_nav_pizza_menu_to_nav_pizza_details, bundle);
        });

        return convertView;
    }

    private void handleAddingAnOrder(String loggedInEmail, String name, String size, double price, String category){
        //INIT THE DIALOG
        initDialog();
        //TODO: OPEN DIALOG
        dialog.show();
        // HANDLE WHEN SUBMIT BUTTON CLICKED
        cancelDialog.setOnClickListener(cancel -> dialog.dismiss());

        submitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmitOrder(name,category, price);
            }
        });

    }

    private void handleSubmitOrder(String name, String category, double price){
        //TODO: VALIDATE THE QUANTITY
        if(Integer.parseInt(editTextQuantity.getText().toString()) > 0){
            Order order = new Order();
            order.setCustomerEmail(loggedInEmail);
            order.setPizzaType(name);
            order.setPizzaSize(spinnerSize.getSelectedItem().toString());

            String priceString = priceText.getText().toString();
            String priceWithoutSymbol = priceString.replaceAll("[^\\d.]", ""); // Remove all non-digit characters except the decimal point
            double totalPrice = Double.parseDouble(priceWithoutSymbol);

            order.setTotalPrice(totalPrice);
            order.setPizzaPrice(price); // Ensure 'price' is also parsed correctly if it comes from a similar source
            order.setQuantity(Integer.parseInt(editTextQuantity.getText().toString()));

            // Setting date and time of the order
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateAndTime = sdf.format(new Date());
            order.setOrderDateTime(currentDateAndTime);

            //ADD THE ORDER TO THE DB
            DataBaseHelper db = new DataBaseHelper(context, "1200134_nsralla_hassan_finalProject", null, 1);
            db.addOrder(order);
            dialog.dismiss();
        }

        else{
            //PRINT TOAST MESSAGE
            Toast toast = Toast.makeText(context, "Quantity must be > 0", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private void initDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_order_pizza);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);
        cancelDialog = dialog.findViewById(R.id.buttonCancelOrder);
        submitDialog = dialog.findViewById(R.id.buttonSubmitOrder);

         spinnerSize = dialog.findViewById(R.id.spinnerPizzaSize);
         editTextQuantity = dialog.findViewById(R.id.editTextQuantity);
         priceText = dialog.findViewById(R.id.priceTextView);
         editTextQuantity.setText(String.valueOf(1));
         //TODO: WHEN THE USER CHANGE THE QUANTITY, UPDATE THE PRICE
        editTextQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used, but must be implemented
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used, but must be implemented
            }

            @Override
            public void afterTextChanged(Editable s) {
                updatePrice();  // Update the price whenever the quantity changes
            }
        });
        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePrice();  // Update the price based on the new size selection
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // You can ignore this case or handle it if necessary
            }
        });
        // Setup spinner options for pizza sizes
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, PIZZA_SIZES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapter);

    }
    private void updatePrice() {
        int quantity = 1;
        try {
            quantity = Integer.parseInt(editTextQuantity.getText().toString());
        } catch (NumberFormatException e) {
            // If the number is not valid or empty, default to 1
            quantity = 1;
        }

        // Assuming you have a method to get the price per unit or you can directly access it
        double pricePerUnit = getCurrentPricePerUnit();  // Implement this method to fetch the current price per unit
        double total = quantity * pricePerUnit;
        priceText.setText(String.format("Price: $%.2f", total));
    }
    private double getCurrentPricePerUnit() {
        // This could depend on other selections like pizza size
        String selectedSize = spinnerSize.getSelectedItem().toString();
        switch (selectedSize) {
            case "Small":
                return 5.0;
            case "Medium":
                return 7.0;
            case "Large":
                return 10.0;
            default:
                return 5.0;  // Default price if size is unhandled
        }
    }
    private void handleAddingPizzaToFavorite(String loggedInEmail, String name, String size, double price, String category) {
        DataBaseHelper db = new DataBaseHelper(context,"1200134_nsralla_hassan_finalProject", null, 1);
        ArrayList<Favorite> favorites = db.getAllFavoritesForCustomer(loggedInEmail);

        boolean exists = false;
        for (Favorite favorite : favorites) {
            if (favorite.getPizzaType().equalsIgnoreCase(name) &&favorite.getPizzaPrice() == price && favorite.getPizzaSize().equalsIgnoreCase(size)  && favorite.getPizzaCategory().equalsIgnoreCase(category)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            db.addFavorite(loggedInEmail, name, size, price, category);
            Toast.makeText(context, "Added to favorites!", Toast.LENGTH_SHORT).show();
            System.out.println("added pizza name: " + name);
        } else {
            Toast.makeText(context, "Already in favorites!", Toast.LENGTH_SHORT).show();
        }
    }
}