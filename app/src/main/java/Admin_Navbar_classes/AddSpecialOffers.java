package Admin_Navbar_classes;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.DataBaseHelper;
import ObjectClasses.PizzaType;
import ObjectClasses.SpecialOffer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSpecialOffers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSpecialOffers extends Fragment {

    private Spinner pizzaTypeSpinner;
    private Spinner sizeSpinner;
    private Button startingOfferDateButton;
    private Button endingOfferDateButton;
    private EditText totalPriceText;
    private Button submitButton;

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddSpecialOffers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSpecialOffers.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSpecialOffers newInstance(String param1, String param2) {
        AddSpecialOffers fragment = new AddSpecialOffers();
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
        View rootView = inflater.inflate(R.layout.fragment_add_special_offers, container, false);

        View profileCard = rootView.findViewById(R.id.hall_layout);
        Animation fallDownAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fall_down);
        profileCard.startAnimation(fallDownAnimation);
        //DEFINE THE ELEMENTS
        pizzaTypeSpinner = rootView.findViewById(R.id.pizzaTypesSpinner);
        sizeSpinner = rootView.findViewById(R.id.sizeSpinner);
        startingOfferDateButton = rootView.findViewById(R.id.startingOfferPeriodEditText);
        endingOfferDateButton = rootView.findViewById(R.id.endingOfferPeriodEditText);
        totalPriceText = rootView.findViewById(R.id.totalPriceEditText);
        submitButton = rootView.findViewById(R.id.submitOfferButton);

        //Populate the pizza types spinner
        ArrayList<PizzaType> pizzaTypesAll = PizzaType.getPizzaTypes();
        ArrayList<String> pizzaNames = new ArrayList<>();
        for (PizzaType pizzaType : pizzaTypesAll) {
            pizzaNames.add(pizzaType.getPizzaType());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pizzaNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaTypeSpinner.setAdapter(adapter);

        //POPULATE THE PIZZA SIZE SPINNER
        String[] sizes = {"Small", "Medium", "Large"};
        ArrayAdapter<String> sizesAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sizes);
        sizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizesAdapter);

        //HANDLE THE DATES
        startingOfferDateButton.setOnClickListener(view -> showDatePickerDialog(true));
        endingOfferDateButton.setOnClickListener(view -> showDatePickerDialog(false));

        // HANDLE THE SUBMIT BUTTON
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmitData();
            }
        });

        return rootView;
    }

    private void handleSubmitData() {
        //GET THE DATA
        String pizzaType = pizzaTypeSpinner.getSelectedItem().toString();
        String pizzaSize = sizeSpinner.getSelectedItem().toString();
        String startingOfferDate = startingOfferDateButton.getText().toString();
        String endingOfferDate = endingOfferDateButton.getText().toString();
        double totalPrice = Double.parseDouble(totalPriceText.getText().toString());

        // Check if all ok
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        try {
            Date startDate = simpleDateFormat.parse(startingOfferDate);
            Date endDate = simpleDateFormat.parse(endingOfferDate);

            // Normalize the dates to remove the time component
            startDate = normalizeDate(startDate);
            endDate = normalizeDate(endDate);
            Date today = normalizeDate(Calendar.getInstance().getTime());

            System.out.println("start date = " + startDate);
            System.out.println("today= " + today);

            if (startDate.before(today) && !startDate.equals(today)) {
                Toast.makeText(getContext(), "Starting date cannot be before today", Toast.LENGTH_SHORT).show();
                return;
            }
            if (endDate.before(startDate)) {
                Toast.makeText(getContext(), "Ending date cannot be before starting date", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pizzaType.isEmpty() || pizzaSize.isEmpty() || startingOfferDate.isEmpty() || endingOfferDate.isEmpty() || String.valueOf(totalPrice).isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if(totalPrice <= 0){
                Toast.makeText(getContext(), "Price must be more than zero", Toast.LENGTH_SHORT).show();
                return;
            }

            // SAVE OFFER TO DB
            SpecialOffer specialOffer = new SpecialOffer();
            specialOffer.setPizzaType(pizzaType);
            specialOffer.setSize(pizzaSize);
            specialOffer.setStartingOfferDate(startingOfferDate);
            specialOffer.setEndingOfferDate(endingOfferDate);
            specialOffer.setTotalPrice(totalPrice);

            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext(), "1200134_nsralla_hassan_finalProject", null, 1);
            dataBaseHelper.addSpecialOffer(getContext(), specialOffer.getPizzaType(), specialOffer.getSize(), specialOffer.getStartingOfferDate(), specialOffer.getEndingOfferDate(), specialOffer.getTotalPrice());

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
        }
    }

    private Date normalizeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public void showDatePickerDialog(boolean isStartingDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, day1) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year1, month1, day1);
            String formattedDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(newDate.getTime());
            if (isStartingDate) {
                startingOfferDateButton.setText(formattedDate);
            } else {
                endingOfferDateButton.setText(formattedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

}
