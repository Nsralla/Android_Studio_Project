package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;
import java.util.List;

import ObjectClasses.PizzaType;

public class PizzaListAdapter extends ArrayAdapter<PizzaType> {

    private Context context;
    private ArrayList<PizzaType> pizzaTypeList;

    public PizzaListAdapter(Context context, ArrayList<PizzaType>pizzaList) {
        super(context, 0, pizzaList);
        this.context = context;
        this.pizzaTypeList = pizzaList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.pizza_item, parent, false);
        PizzaType pizza = getItem(position);
        TextView pizzaName = convertView.findViewById(R.id.txtPizzaName);
//        ImageView pizzaImage = convertView.findViewById(R.id.imgPizza);
        Button btnFavorite = convertView.findViewById(R.id.btnFavorite);
        Button btnOrder = convertView.findViewById(R.id.btnOrder);
        pizzaName.setText(pizza.getPizzaType());

//        pizzaImage.setImageResource(context.getResources().getIdentifier(
//                "pizza_" + position, "drawable", context.getPackageName()));
        btnFavorite.setOnClickListener(v -> {
            // Handle Add to Favorites
        });

        btnOrder.setOnClickListener(v -> {
            // Handle Order
        });
        return convertView;
    }
}
