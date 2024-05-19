package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;
import java.util.List;

import Database.DataBaseHelper;
import DialogManager.OrderDialogManager;
import ObjectClasses.Favorite;
import ObjectClasses.PizzaType;

public class PizzaListAdapter extends ArrayAdapter<PizzaType> {

    private Context context;
    private ArrayList<PizzaType> pizzaTypeList;
    private ArrayList<PizzaType> pizzaSearch;
    private ListView listView;
    String loggedInEmail;

    public PizzaListAdapter(Context context, ArrayList<PizzaType> pizzaList, ListView listView) {
        super(context, 0, pizzaList);
        this.context = context;
        this.pizzaTypeList = new ArrayList<>(pizzaList); // Clone the original list
        this.pizzaSearch = new ArrayList<>(pizzaList); // Initialize the search list with the original list
        this.listView = listView;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pizza_item, parent, false);
        }
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
            handleAddingAnOrder(loggedInEmail, pizza.getPizzaType(), pizza.getSize(), pizza.getPrice(), pizza.getCategory());
        });

        convertView.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_home_layout);
            Bundle bundle = new Bundle();
            bundle.putString("pizzaType", pizza.getPizzaType());
            bundle.putFloat("pizzaPrice", (float) pizza.getPrice());
            System.out.println("PIZZA SIZE = " + pizza.getSize());
            bundle.putString("pizzaSize", pizza.getSize());
            bundle.putString("pizzaCategory", pizza.getCategory());
            navController.navigate(R.id.action_nav_pizza_menu_to_nav_pizza_details, bundle);
        });

        return convertView;
    }

    public void handleSearch(String text) {
        pizzaSearch.clear();
        if (text == null || text.isEmpty()) {
            pizzaSearch.addAll(pizzaTypeList);
        } else {
            for (PizzaType pizza : pizzaTypeList) {
                if (pizza.getPizzaType().toLowerCase().contains(text.toLowerCase()) ||
                        String.valueOf(pizza.getPrice()).contains(text) ||
                        pizza.getCategory().toLowerCase().contains(text.toLowerCase())) {
                    pizzaSearch.add(pizza);
                }
            }
        }
        clear();
        addAll(pizzaSearch);
        notifyDataSetChanged();
    }


    private void handleAddingAnOrder(String loggedInEmail, String name, String size, double price, String category) {
        OrderDialogManager orderDialogManager = new OrderDialogManager(context, loggedInEmail, name, size, price, category);
        orderDialogManager.show();
    }

    private void handleAddingPizzaToFavorite(String loggedInEmail, String name, String size, double price, String category) {
        DataBaseHelper db = new DataBaseHelper(context, "1200134_nsralla_hassan_finalProject", null, 1);
        ArrayList<Favorite> favorites = db.getAllFavoritesForCustomer(loggedInEmail);

        boolean exists = false;
        for (Favorite favorite : favorites) {
            if (favorite.getPizzaType().equalsIgnoreCase(name) && favorite.getPizzaPrice() == price && favorite.getPizzaSize().equalsIgnoreCase(size) && favorite.getPizzaCategory().equalsIgnoreCase(category)) {
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
