package Admin_Navbar_classes;

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

import Adapter.ViewAllOrdersAdapter;
import Database.DataBaseHelper;
import ObjectClasses.Order;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewAllOrders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAllOrders extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewAllOrders() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewAllOrders.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewAllOrders newInstance(String param1, String param2) {
        ViewAllOrders fragment = new ViewAllOrders();
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
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("currentLoggedInUserEmail",null);
        System.out.println("USER EMAIL in nav favorites: " + userEmail);
        View view = inflater.inflate(R.layout.fragment_view_all_orders, container, false);
        ListView listView = view.findViewById(R.id.allOrders_admin);
        DataBaseHelper db = new DataBaseHelper(getContext(),"1200134_nsralla_hassan_finalProject", null, 1);
        ArrayList<Order> orders = db.getAllOrders();
        ViewAllOrdersAdapter viewAllOrdersAdapter = new ViewAllOrdersAdapter(getContext(),orders, listView, userEmail);
        listView.setAdapter(viewAllOrdersAdapter);
        return view;
    }
}