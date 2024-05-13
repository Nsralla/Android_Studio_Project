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

        TextView pizzaTypeText = convertView.findViewById(R.id.tvOrderType);
        TextView pizzaTotalPriceText = convertView.findViewById(R.id.tvOrderTotalPrice);
        TextView pizzaSizeText = convertView.findViewById(R.id.tvOrderSize);
        TextView pizzaPriceText = convertView.findViewById(R.id.tvOrderPrice);
        TextView dateText = convertView.findViewById(R.id.tvOrderDate);
        TextView pizzaQuantityText = convertView.findViewById(R.id.tvOrderQuantity);
        TextView timeText = convertView.findViewById(R.id.tvOrderTime);

        // Set the pizza details
        pizzaTypeText.setText(order.getPizzaType());
        pizzaTotalPriceText.setText(String.format("$%.2f", order.getTotalPrice()));
        pizzaQuantityText.setText(String.valueOf(order.getQuantity()));
        pizzaPriceText.setText(String.format("$%.2f", order.getPizzaPrice()));
        pizzaSizeText.setText(order.getPizzaSize());

        // Split the orderDateTime string to extract date and time
        if (order.getOrderDateTime() != null && !order.getOrderDateTime().isEmpty()) {
            String[] dateTimeParts = order.getOrderDateTime().split(" ");
            if (dateTimeParts.length == 2) {
                dateText.setText(dateTimeParts[0]);  // Date in YYYY-MM-DD
                timeText.setText(dateTimeParts[1]);  // Time in HH:MM:SS
            }
        }

        return convertView;
    }


}
