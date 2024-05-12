package Adapter;

import static android.content.Context.MODE_PRIVATE;
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

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.a1200134_nsralla_hassan_finalproject.R;
import java.util.ArrayList;

import Database.DataBaseHelper;
import ObjectClasses.Favorite;
import ObjectClasses.PizzaType;
public class PizzaListAdapter extends ArrayAdapter<PizzaType> {

    private Context context;
    private ArrayList<PizzaType> pizzaTypeList;
    private ListView listView;

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
        Button btnFavorite = convertView.findViewById(R.id.btnFavorite);
        Button btnOrder = convertView.findViewById(R.id.btnOrder);

        pizzaName.setText(pizza.getPizzaType());

        btnFavorite.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
            String loggedInEmail = sharedPreferences.getString("email", null);
            System.out.println("logged ine email: "+ loggedInEmail);
            handleAddingPizzaToFavorite(loggedInEmail, pizza.getPizzaType(), pizza.getSize(), pizza.getPrice(), pizza.getCategory());
        });

        btnOrder.setOnClickListener(v -> {
            // Implement order functionality
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