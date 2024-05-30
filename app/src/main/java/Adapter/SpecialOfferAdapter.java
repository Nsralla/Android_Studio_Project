package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import DialogManager.OrderDialogManager;
import DialogManager.SpecialOfferOrderManager;
import ObjectClasses.PizzaType;
import ObjectClasses.SpecialOffer;

public class SpecialOfferAdapter extends ArrayAdapter<SpecialOffer> {
    private Context context;
    private ArrayList<SpecialOffer> specialOffers;
    private ListView listView;

    TextView textViewPizzaType;
    TextView textViewPizzaSize;

    TextView textViewTotalPrice;
    TextView textViewStartOfferDate;
    TextView textViewEndOfferDate;
    TextView textViewPizzaQuantity;


    Button buttonOrder;

    String loggedInEmail;

    public SpecialOfferAdapter(@NonNull Context context, ArrayList<SpecialOffer> specialOffers, ListView listView) {
        super(context, 0,specialOffers );
        this.context = context;
        this.specialOffers = specialOffers;
        this.listView = listView;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.offer_item, parent, false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        loggedInEmail = sharedPreferences.getString("currentLoggedInUserEmail", null);

        SpecialOffer offer = getItem(position);

        textViewPizzaType = convertView.findViewById(R.id.textViewPizzaType);
        textViewPizzaSize = convertView.findViewById(R.id.textViewPizzaSize);
        textViewPizzaQuantity = convertView.findViewById(R.id.textViewPizzaQuantity);

        textViewTotalPrice = convertView.findViewById(R.id.textViewTotalPrice);
        textViewStartOfferDate = convertView.findViewById(R.id.textViewStartOfferDate);
        textViewEndOfferDate = convertView.findViewById(R.id.textViewEndOfferDate);
        buttonOrder = convertView.findViewById(R.id.buttonOrder);

        // Concatenate all pizza types and sizes into respective strings
        StringBuilder pizzaTypesBuilder = new StringBuilder();
        StringBuilder pizzaSizesBuilder = new StringBuilder();
        StringBuilder pizzaQuantitiesBuilder = new StringBuilder();


        for (PizzaType pizza : offer.getPizzas()) {
            pizzaTypesBuilder.append(pizza.getPizzaType()).append(", ");
            pizzaSizesBuilder.append(pizza.getSize()).append(", ");
            pizzaQuantitiesBuilder.append(pizza.getQuantity()).append(", ");

        }

        // Remove the trailing comma and space
        if (pizzaTypesBuilder.length() > 0) {
            pizzaTypesBuilder.setLength(pizzaTypesBuilder.length() - 2);
            pizzaSizesBuilder.setLength(pizzaSizesBuilder.length() - 2);
            pizzaQuantitiesBuilder.setLength(pizzaQuantitiesBuilder.length() - 2);

        }

        textViewPizzaType.setText(pizzaTypesBuilder.toString());
        textViewPizzaSize.setText(pizzaSizesBuilder.toString());
        textViewPizzaQuantity.setText(pizzaQuantitiesBuilder.toString());
        textViewTotalPrice.setText(String.format("$%.2f", offer.getTotalPrice()));
        textViewStartOfferDate.setText(offer.getStartingOfferDate());
        textViewEndOfferDate.setText(offer.getEndingOfferDate());

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecialOfferOrderManager specialOfferOrderManager = new SpecialOfferOrderManager(context, loggedInEmail, offer);
                specialOfferOrderManager.show();
            }
        });

        return convertView;
    }

}
