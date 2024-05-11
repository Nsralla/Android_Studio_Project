package com.example.a1200134_nsralla_hassan_finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PizzaDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PizzaDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PIZZA_NAME = "pizza_name";
    private static final String ARG_PIZZA_PRICE = "pizza_price";
    private static final String ARG_PIZZA_SIZE = "pizza_size";
    private static final String ARG_PIZZA_CATEGORY = "pizza_category";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PizzaDetailsFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment PizzaDetailsFragment.
//     */
    // TODO: Rename and change types and number of parameters
    public static PizzaDetailsFragment newInstance(String name, double price, String size, String category) {
        PizzaDetailsFragment fragment = new PizzaDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PIZZA_NAME, name);
        args.putDouble(ARG_PIZZA_PRICE, price);
        args.putString(ARG_PIZZA_SIZE, size);
        args.putString(ARG_PIZZA_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_details, container, false);
        Button btnBack = view.findViewById(R.id.btnBack);
        // Assume you have TextViews for each attribute
        TextView nameView = view.findViewById(R.id.pizzaName);
        TextView priceView = view.findViewById(R.id.pizzaPrice);
        TextView sizeView = view.findViewById(R.id.pizzaSize);
        TextView categoryView = view.findViewById(R.id.pizzaCategory);

        // Set the pizza details
        nameView.setText(getArguments().getString(ARG_PIZZA_NAME));
        priceView.setText(String.valueOf(getArguments().getDouble(ARG_PIZZA_PRICE)));
        sizeView.setText(getArguments().getString(ARG_PIZZA_SIZE));
        categoryView.setText(getArguments().getString(ARG_PIZZA_CATEGORY));

//         Back button logic
//        btnBack.setOnClickListener(v -> {
//            NavController navController = NavHostFragment.findNavController(this);
//            if (navController.getCurrentDestination().getId() == R.id.nav_pizza_details) {
//                navController.navigateUp();
//            }
//        });
//        btnBack.setOnClickListener(v -> {
//            NavController navController = NavHostFragment.findNavController(this);
//            navController.navigateUp();
//        });


        return view;
    }
}