package User_Navbar_classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;

import Adapter.PizzaOrdersAdapter;
import Database.DataBaseHelper;
import ObjectClasses.Order;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nav_Orders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nav_Orders extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView listView;
    private PizzaOrdersAdapter adapter;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nav_Orders() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Nav_Orders.
     */
    // TODO: Rename and change types and number of parameters
    public static Nav_Orders newInstance(String param1, String param2) {
        Nav_Orders fragment = new Nav_Orders();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View rootView = inflater.inflate(R.layout.fragment_nav__orders, container, false);
        listView = rootView.findViewById(R.id.listViewOrders);
        // connect to data base and get all orders
        DataBaseHelper db = new DataBaseHelper(getContext(),"1200134_nsralla_hassan_finalProject", null, 1);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString("currentLoggedInUserEmail", null);
        ArrayList<Order> orders = db.getAllOrdersByEmail(loggedInEmail);
        adapter = new PizzaOrdersAdapter(getContext(),orders,listView);
        listView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }
}