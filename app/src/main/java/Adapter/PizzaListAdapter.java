package Adapter;

import android.content.Context;
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

import com.example.a1200134_nsralla_hassan_finalproject.PizzaDetailsFragment;
import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;

import ObjectClasses.PizzaType;

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
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show the details fragment
                listView.setVisibility(View.GONE);
                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                PizzaDetailsFragment detailsFragment = PizzaDetailsFragment.newInstance(
                        pizza.getPizzaType(), pizza.getPrice(), pizza.getSize(), pizza.getCategory());

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, detailsFragment) // Make sure you have a container in your layout
                        .addToBackStack(null) // Adds the transaction to the back stack
                        .commit();
//                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                PizzaDetailsFragment detailsFragment = PizzaDetailsFragment.newInstance(
//                        pizza.getPizzaType(), pizza.getPrice(), pizza.getSize(), pizza.getCategory());
//                transaction.replace(R.id.fragment_container_menu, detailsFragment);
//                transaction.addToBackStack(null); // Optional: Add transaction to back stack if you want to navigate back
//                transaction.commit();
            }
        });

        return convertView;
    }
}
