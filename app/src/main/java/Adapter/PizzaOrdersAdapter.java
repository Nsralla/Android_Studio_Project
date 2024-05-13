package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;
import java.util.List;

import ObjectClasses.Order;

public class PizzaOrdersAdapter extends ArrayAdapter<Order> {

    Context context;
    ArrayList<Order>orders;
    ListView listView;

    String loggedInEmail;
    public PizzaOrdersAdapter(@NonNull Context context, ArrayList<Order> orders, ListView listView) {
        super(context,0, orders);
        this.context = context;
        this.orders = orders;
        this.listView = listView;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        Order order = getItem(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        loggedInEmail = sharedPreferences.getString("currentLoggedInUserEmail", null);

        TextView pizzaTypeText = convertView.findViewById(R.id.tvOrderType);
        TextView pizzaTotalPriceText = convertView.findViewById(R.id.tvOrderTotalPrice);
        TextView pizzaSizeText = convertView.findViewById(R.id.tvOrderSize);
//        TextView pizzaCategoryText = convertView.findViewById(R.id.tvOrderCategory);
        TextView pizzaPriceText = convertView.findViewById(R.id.tvOrderPrice);
        TextView DateText = convertView.findViewById(R.id.tvOrderDate);
        TextView pizzaQuantityText = convertView.findViewById(R.id.tvOrderQuantity);
        TextView pizzaTimeText = convertView.findViewById(R.id.tvOrderTime);

        // FILL THE VALUES

        return convertView;

    }

}
