package Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.a1200134_nsralla_hassan_finalproject.PizzaDetailsFragment;
import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;

import ObjectClasses.PizzaType;
import User_Navbar_classes.Nav_PizzaMenu;

public class PizzaListAdapter extends ArrayAdapter<PizzaType> {

    private Context context;
    private ArrayList<PizzaType> pizzaTypeList;
    private ListView listView;
//    private PizzaDetailsFragment.Communicator communicator;


    public PizzaListAdapter(Context context, ArrayList<PizzaType>pizzaList, ListView listView) {
        super(context, 0, pizzaList);
        this.context = context;
        this.pizzaTypeList = pizzaList;
        this.listView = listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.pizza_item, parent, false);
        PizzaType pizza = getItem(position);
        TextView pizzaName = convertView.findViewById(R.id.txtPizzaName);
//        ImageView pizzaImage = convertView.findViewById(R.id.imgPizza);
        Button btnFavorite = convertView.findViewById(R.id.btnFavorite);
        Button btnOrder = convertView.findViewById(R.id.btnOrder);
        pizzaName.setText(pizza.getPizzaType());

//        pizzaImage.setImageResource(context.getResources().getIdentifier(
//                "pizza_" + position, "drawable", context.getPackageName()));
        btnFavorite.setOnClickListener(v -> {
            // Handle Add to Favorites
        });

        btnOrder.setOnClickListener(v -> {
            // Handle Order
        });
//        ListView listView = convertView.findViewById(R.id.listViewPizzas);
        Log.d("ListViewVisibility", "ListView visibility before transaction: " + listView.getVisibility());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use NavController to navigate
                NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_home_layout);

                // Create a bundle to pass the pizza details
                Bundle bundle = new Bundle();
                bundle.putString("pizzaType", pizza.getPizzaType());
                bundle.putFloat("pizzaPrice", (float) pizza.getPrice());
                bundle.putString("pizzaSize", pizza.getSize());
                bundle.putString("pizzaCategory", pizza.getCategory());

                // Navigate with the provided arguments
                navController.navigate(R.id.action_nav_pizza_menu_to_nav_pizza_details, bundle);
            }
        });

        return convertView;
    }
}
