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
import ObjectClasses.SpecialOffer;

public class SpecialOfferOrderManager {
    private Context context;
    private Dialog dialog;
    private TextView PizzaSize;
    private EditText editTextQuantity;
    private TextView priceText;
    private String loggedInEmail;
    private String pizzaType;
    private double pizzaPrice;
    private String pizzaSize;
    private String pizzaCategory;
    Button cancelDialog;
    Button submitDialog;

    SpecialOffer specialOffer;

    public SpecialOfferOrderManager(Context context, String loggedInEmail, SpecialOffer specialOffer) {
        this.context = context;
        this.loggedInEmail = loggedInEmail;
        this.specialOffer = specialOffer;
        initDialog();
    }

    private void initDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_special_offer_order);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);

        cancelDialog = dialog.findViewById(R.id.buttonCancelOrder);
        submitDialog = dialog.findViewById(R.id.buttonSubmitOrder);

        editTextQuantity = dialog.findViewById(R.id.editTextQuantity);
        priceText = dialog.findViewById(R.id.priceTextView);

        priceText.setText(String.format("Price: $%.2f", specialOffer.getTotalPrice()));
        editTextQuantity.setText("1");



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
        int totalPizzasCount = 0;
        if (quantity > 0) {
            // Create list of pizzas (even if it's just one for now)
            ArrayList<PizzaType> pizzas = new ArrayList<>();
            for(int i = 0; i < specialOffer.getPizzas().size(); i++){
                pizzas.add(specialOffer.getPizzas().get(i));
                totalPizzasCount += specialOffer.getPizzas().get(i).getQuantity();
            }


            String priceString = priceText.getText().toString();
            String priceWithoutSymbol = priceString.replaceAll("[^\\d.]", ""); // Remove all non-digit characters except the decimal point
            double totalPrice = Double.parseDouble(priceWithoutSymbol);

            //TODO: CALCULATE THE CORRECT QUANTITY FOR THE ORDER
            // Create Order object
            Order order = new Order(loggedInEmail, pizzas, Integer.parseInt(editTextQuantity.getText().toString()), getCurrentDateTime(), totalPrice);
            System.out.println("Order total  = "+ order.getTotalPrice());

            //ADD THE ORDER TO THE DB
            DataBaseHelper db = new DataBaseHelper(context, "1200134_nsralla_hassan_finalProject", null, 1);
            db.addOrder(order,1);
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
        double pricePerUnit = specialOffer.getTotalPrice();
        double total = quantity * pricePerUnit;
        priceText.setText(String.format("Price: $%.2f", total));
    }
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}


