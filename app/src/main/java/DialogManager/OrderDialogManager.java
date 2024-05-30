package DialogManager;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Database.DataBaseHelper;
import ObjectClasses.Order;
import ObjectClasses.PizzaType;

public class OrderDialogManager {
    private Context context;
    private Dialog dialog;
    private Spinner spinnerSize;
    private EditText editTextQuantity;
    private TextView priceText;
    private String[] PIZZA_SIZES = {"Small", "Medium", "Large"};
    private String loggedInEmail;
    private String pizzaType;
    private double pizzaPrice;
    private String pizzaSize;
    private String pizzaCategory;
    Button cancelDialog;
    Button submitDialog;

    public OrderDialogManager(Context context, String loggedInEmail, String pizzaType, String pizzaSize, double pizzaPrice, String pizzaCategory) {
        this.context = context;
        this.loggedInEmail = loggedInEmail;
        this.pizzaType = pizzaType;
        this.pizzaSize = pizzaSize;
        this.pizzaPrice = pizzaPrice;
        this.pizzaCategory = pizzaCategory;
        initDialog();
    }

    private void initDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_order_pizza);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);

        cancelDialog = dialog.findViewById(R.id.buttonCancelOrder);
        submitDialog = dialog.findViewById(R.id.buttonSubmitOrder);

        spinnerSize = dialog.findViewById(R.id.spinnerPizzaSize);
        editTextQuantity = dialog.findViewById(R.id.editTextQuantity);
        priceText = dialog.findViewById(R.id.priceTextView);
        editTextQuantity.setText("1");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, PIZZA_SIZES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapter);

        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePrice();  // Update the price based on the new size selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editTextQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                updatePrice();  // Update the price whenever the quantity changes
            }
        });

        cancelDialog.setOnClickListener(cancel -> dialog.dismiss());
        submitDialog.setOnClickListener(v -> handleSubmitOrder());
    }

    public void show() {
        dialog.show();
    }

    private void handleSubmitOrder() {
        int quantity = Integer.parseInt(editTextQuantity.getText().toString());
        if (quantity > 0) {
            double pizzaPrice;
            if(spinnerSize.getSelectedItem().toString() == "Large"){
                pizzaPrice = 10;
            }
            else if(spinnerSize.getSelectedItem().toString() == "Medium"){
                pizzaPrice = 7;
            }
            else{
                pizzaPrice = 5;
            }
            String priceString = priceText.getText().toString();
            String priceWithoutSymbol = priceString.replaceAll("[^\\d.]", ""); // Remove all non-digit characters except the decimal point
            double totalPrice = Double.parseDouble(priceWithoutSymbol);

            PizzaType pizza = new PizzaType(pizzaType, spinnerSize.getSelectedItem().toString(),pizzaPrice , quantity,pizzaCategory);
            ArrayList<PizzaType> pizzas = new ArrayList<>();
            pizzas.add(pizza);

            Order order = new Order(loggedInEmail, pizzas, quantity, getCurrentDateTime(), totalPrice);
            //ADD THE ORDER TO THE DB
            DataBaseHelper db = new DataBaseHelper(context, "1200134_nsralla_hassan_finalProject", null, 1);
            db.addOrder(order,0);
            dialog.dismiss();
            Toast.makeText(context, "Order placed successfully!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Quantity must be greater than 0", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePrice() {
        int quantity = 1;
        try {
            quantity = Integer.parseInt(editTextQuantity.getText().toString());
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        double pricePerUnit = getCurrentPricePerUnit();
        double total = quantity * pricePerUnit;
        priceText.setText(String.format("Price: $%.2f", total));
    }

    private double getCurrentPricePerUnit() {
        String selectedSize = spinnerSize.getSelectedItem().toString();
        switch (selectedSize) {
            case "Small": return 5.0;
            case "Medium": return 7.0;
            case "Large": return 10.0;
            default: return 5.0;
        }
    }
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}


