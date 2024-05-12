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
import DialogManager.OrderDialogManager;
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
            OrderDialogManager orderDialogManager = new OrderDialogManager(context, loggedInEmail, name, size, price, category);
            orderDialogManager.show();


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