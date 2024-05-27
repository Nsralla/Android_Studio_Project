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

    Button buttonOrder;

    String loggedInEmail;

    public SpecialOfferAdapter(@NonNull Context context, ArrayList<SpecialOffer> specialOffers, ListView listView) {
        super(context, 0,specialOffers );
        this.context = context;
        this.specialOffers = specialOffers;
        this.listView = listView;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.offer_item, parent, false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        loggedInEmail = sharedPreferences.getString("currentLoggedInUserEmail", null);

        SpecialOffer offer = getItem(position);
        textViewPizzaType = convertView.findViewById(R.id.textViewPizzaType);
        textViewPizzaSize = convertView.findViewById(R.id.textViewPizzaSize);
        textViewTotalPrice = convertView.findViewById(R.id.textViewTotalPrice);
        textViewStartOfferDate = convertView.findViewById(R.id.textViewStartOfferDate);
        textViewEndOfferDate = convertView.findViewById(R.id.textViewEndOfferDate);
        buttonOrder = convertView.findViewById(R.id.buttonOrder);

        textViewPizzaType.setText(offer.getPizzaType());
        textViewPizzaSize.setText(offer.getSize());
        System.out.println("Offer total price 2 = " + offer.getTotalPrice());
        textViewTotalPrice.setText(String.format("%.2f", offer.getTotalPrice()));        textViewStartOfferDate.setText(offer.getStartingOfferDate());
        textViewEndOfferDate.setText(offer.getEndingOfferDate());

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecialOfferOrderManager specialOfferOrderManager =  new SpecialOfferOrderManager(context, loggedInEmail, offer.getPizzaType(), offer.getSize() ,offer.getTotalPrice(), "");
                specialOfferOrderManager.show();
            }
        });

        return convertView;
    }
}
