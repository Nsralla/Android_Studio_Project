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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;

import Database.DataBaseHelper;
import DialogManager.OrderDialogManager;
import ObjectClasses.Favorite;

public class FavoritePizzaAdapter extends ArrayAdapter<Favorite> {
    private Context context;
    private ArrayList<Favorite> favorites;
    private ListView listView;

    private String userEmail;



    Button undo;
    Button order;


   public FavoritePizzaAdapter(Context context, ArrayList<Favorite> favorites, ListView listView, String userEmail){
       super(context, 0 ,favorites);
       this.context = context;
       this.favorites = favorites;
       this.listView = listView;
       this.userEmail = userEmail;
   }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.favorite_pizza_item,parent, false);
//        ImageView imageView = convertView.findViewById(R.id.imageViewPizza);
        TextView textViewName = convertView.findViewById(R.id.textViewPizzaName);
        TextView textViewDetails = convertView.findViewById(R.id.textViewPizzaDetails);
        undo = convertView.findViewById(R.id.buttonRemoveFavorite);
        order = convertView.findViewById(R.id.buttonOrder);
        Favorite favorite = getItem(position);
        textViewName.setText(favorite.getPizzaType());
        textViewDetails.setText("Size: " + favorite.getPizzaSize() + ", Price: $" + favorite.getPizzaPrice() + ", Category: " + favorite.getPizzaCategory());


        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRemoveFromFavorite(favorite, position);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDialogManager orderDialogManager = new OrderDialogManager(context, userEmail, favorite.getPizzaType(), favorite.getPizzaSize(), favorite.getPizzaPrice(), favorite.getPizzaCategory());
                orderDialogManager.show();

            }
        });

        return convertView;
    }


    public void handleRemoveFromFavorite(Favorite favorite, int position){
        DataBaseHelper db = new DataBaseHelper(context,"1200134_nsralla_hassan_finalProject", null, 1 );
        boolean hasRemoved = db.removeFavorite(userEmail,favorite.getPizzaType(), favorite.getPizzaSize(), favorite.getPizzaPrice(), favorite.getPizzaCategory() );
        if(hasRemoved){
            Toast.makeText(context, "Has been removed",Toast.LENGTH_SHORT).show();
            this.notifyDataSetChanged(); // Notify the adapter to refresh the list view
            this.favorites.remove(position);
        }
        else
            Toast.makeText(context, "Error Removing the pizza",Toast.LENGTH_SHORT).show();

    }
}
