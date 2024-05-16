package User_Navbar_classes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.a1200134_nsralla_hassan_finalproject.PizzaDetailsFragment;
import com.example.a1200134_nsralla_hassan_finalproject.R;

import Adapter.PizzaListAdapter;
import ObjectClasses.PizzaType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nav_PizzaMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nav_PizzaMenu extends Fragment  {

    private ListView listView;
    private PizzaListAdapter adapter;

    EditText searchEditText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nav_PizzaMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PizzaMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static Nav_PizzaMenu newInstance(String param1, String param2) {
        Nav_PizzaMenu fragment = new Nav_PizzaMenu();
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
        View rootView = inflater.inflate(R.layout.fragment_pizza_menu, container, false);
        listView = rootView.findViewById(R.id.listViewPizzas);
        adapter = new PizzaListAdapter(getContext(),PizzaType.getPizzaTypes(), listView);
        listView.setAdapter(adapter);
        searchEditText = rootView.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               adapter.handleSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return rootView;
    }



}

