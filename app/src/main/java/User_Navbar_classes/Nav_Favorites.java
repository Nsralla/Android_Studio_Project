package User_Navbar_classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.FavoritePizzaAdapter;
import Database.DataBaseHelper;
import ObjectClasses.Favorite;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nav_Favorites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nav_Favorites extends Fragment {

    ListView listView;
    private FavoritePizzaAdapter adapter;
    private ArrayList<Favorite> favorites;
    private String userEmail;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nav_Favorites() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Nav_Favorites.
     */
    // TODO: Rename and change types and number of parameters
    public static Nav_Favorites newInstance(String param1, String param2) {
        Nav_Favorites fragment = new Nav_Favorites();
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
        userEmail = sharedPreferences.getString("currentLoggedInUserEmail",null);
        System.out.println("USER EMAIL in nav favorites: " + userEmail);
        View view = inflater.inflate(R.layout.fragment_nav__favorites, container, false);
        listView = view.findViewById(R.id.listViewFavorites);
        DataBaseHelper db = new DataBaseHelper(getContext(),"1200134_nsralla_hassan_finalProject", null, 1);
        favorites = db.getAllFavoritesForCustomer(userEmail);
        adapter = new FavoritePizzaAdapter(getContext(),favorites, listView, userEmail);
        listView.setAdapter(adapter);
        return view;
    }
}