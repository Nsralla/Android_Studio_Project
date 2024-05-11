package com.example.a1200134_nsralla_hassan_finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

/**
 * A simple {@link Fragment} subclass that displays the details of a pizza.
 * Use the {@link PizzaDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PizzaDetailsFragment extends Fragment {

    // Define parameter keys consistent with what's passed in navigation
    private static final String ARG_PIZZA_TYPE = "pizzaType";
    private static final String ARG_PIZZA_PRICE = "pizzaPrice";
    private static final String ARG_PIZZA_SIZE = "pizzaSize";
    private static final String ARG_PIZZA_CATEGORY = "pizzaCategory";

    // Variables to store the passed parameters
    private String pizzaType;
    private float pizzaPrice;
    private String pizzaSize;
    private String pizzaCategory;

    public PizzaDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type Parameter for pizza type.
     * @param price Parameter for pizza price.
     * @param size Parameter for pizza size.
     * @param category Parameter for pizza category.
     * @return A new instance of fragment PizzaDetailsFragment.
     */
    public static PizzaDetailsFragment newInstance(String type, float price, String size, String category) {
        PizzaDetailsFragment fragment = new PizzaDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PIZZA_TYPE, type);
        args.putFloat(ARG_PIZZA_PRICE, price);
        args.putString(ARG_PIZZA_SIZE, size);
        args.putString(ARG_PIZZA_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pizzaType = getArguments().getString(ARG_PIZZA_TYPE);
            pizzaPrice = getArguments().getFloat(ARG_PIZZA_PRICE);
            pizzaSize = getArguments().getString(ARG_PIZZA_SIZE);
            pizzaCategory = getArguments().getString(ARG_PIZZA_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("PizzaDetailsFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_pizza_details, container, false);

        // Assume you have TextViews for each attribute
        TextView nameView = view.findViewById(R.id.pizzaName);
        TextView priceView = view.findViewById(R.id.pizzaPrice);
        TextView sizeView = view.findViewById(R.id.pizzaSize);
        TextView categoryView = view.findViewById(R.id.pizzaCategory);

        // Set the pizza details
        nameView.setText(pizzaType);
        priceView.setText(String.format("$%.2f", pizzaPrice));
        sizeView.setText(pizzaSize);
        categoryView.setText(pizzaCategory);

        // Set up the back button
        Button btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            if (navController.getCurrentDestination().getId() == R.id.nav_pizza_details) {
                navController.navigateUp();
            }
        });

        return view;
    }
}
