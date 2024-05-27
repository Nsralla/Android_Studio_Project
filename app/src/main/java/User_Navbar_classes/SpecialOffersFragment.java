package User_Navbar_classes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Adapter.SpecialOfferAdapter;
import Database.DataBaseHelper;
import ObjectClasses.SpecialOffer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpecialOffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpecialOffersFragment extends Fragment {

    ListView listView;
    DataBaseHelper dataBaseHelper;
    SpecialOfferAdapter adapter;
    ArrayList<SpecialOffer> specialOffers;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpecialOffersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpecialOffersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpecialOffersFragment newInstance(String param1, String param2) {
        SpecialOffersFragment fragment = new SpecialOffersFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_special_offers, container, false);
        listView = rootView.findViewById(R.id.listview);
        dataBaseHelper = new DataBaseHelper(getContext(), "1200134_nsralla_hassan_finalProject", null, 1);
        specialOffers = dataBaseHelper.getAllOffers();
        System.out.println("-------------------------------------");
        for(int i = 0; i < specialOffers.size();i++){
            System.out.println(specialOffers.get(i).getTotalPrice());
        }
        System.out.println("-------------------------------------");


        // Filter the offers that have expired
        ArrayList<SpecialOffer> validOffers = filterValidOffers(specialOffers);
        System.out.println("-------------------------------------");
        for(int i = 0; i < validOffers.size();i++){
            System.out.println(validOffers.get(i).getTotalPrice());
        }
        System.out.println("-------------------------------------");

        adapter = new SpecialOfferAdapter(getContext(), validOffers, listView);
        listView.setAdapter(adapter);
        return rootView;
    }

    private ArrayList<SpecialOffer> filterValidOffers(ArrayList<SpecialOffer> offers) {
        ArrayList<SpecialOffer> validOffers = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        Date currentDate = new Date();

        for (SpecialOffer offer : offers) {
            try {
                Date endDate = dateFormat.parse(offer.getEndingOfferDate());
                if (endDate != null && (endDate.after(currentDate) || endDate.equals(currentDate))) {
                    validOffers.add(offer);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return validOffers;
    }
}
