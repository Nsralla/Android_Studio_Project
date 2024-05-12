package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;

import ObjectClasses.Favorite;

public class FavoritePizzaAdapter extends ArrayAdapter<Favorite> {
    private Context context;
    private ArrayList<Favorite> favorites;
    private ListView listView;

   public FavoritePizzaAdapter(Context context, ArrayList<Favorite> favorites, ListView listView){
       super(context, 0 ,favorites);
       this.context = context;
       this.favorites = favorites;
       this.listView = listView;
   }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.favorite_pizza_item,parent, false);

//        ImageView imageView = convertView.findViewById(R.id.imageViewPizza);
        TextView textViewName = convertView.findViewById(R.id.textViewPizzaName);
        TextView textViewDetails = convertView.findViewById(R.id.textViewPizzaDetails);

        Favorite favorite = getItem(position);


        textViewName.setText(favorite.getPizzaType());
        textViewDetails.setText("Size: " + favorite.getPizzaSize() + ", Price: $" + favorite.getPizzaPrice() + ", Category: " + favorite.getPizzaCategory());

        return convertView;
    }
}
