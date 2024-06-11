package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import java.util.ArrayList;

import DialogManager.OrderDetailsDialogManager;
import ObjectClasses.Order;
import ObjectClasses.PizzaType;

public class PizzaOrdersAdapter extends ArrayAdapter<Order> {
    Context context;
    ArrayList<Order> orders;
    ListView listView;

    private TextView emptyTextView;


    public PizzaOrdersAdapter(@NonNull Context context, ArrayList<Order> orders, ListView listView, TextView emptyTextView) {
        super(context, 0, orders);
        this.context = context;
        this.orders = orders;
        this.listView = listView;
        this.emptyTextView = emptyTextView;
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (orders.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);

        Order order = getItem(position);

        TextView pizzaTypeText = convertView.findViewById(R.id.tvOrderType);
        TextView pizzaTotalPriceText = convertView.findViewById(R.id.tvOrderTotalPrice);
        TextView dateText = convertView.findViewById(R.id.tvOrderDate);

        // Concatenate all pizza types into a single string
        StringBuilder pizzaTypesBuilder = new StringBuilder();
        for (PizzaType pizza : order.getPizzas()) {
            pizzaTypesBuilder.append(pizza.getPizzaType()).append(", ");
        }

        // Remove the trailing comma and space
        if (pizzaTypesBuilder.length() > 0) {
            pizzaTypesBuilder.setLength(pizzaTypesBuilder.length() - 2);
        }

        pizzaTypeText.setText(pizzaTypesBuilder.toString());
        pizzaTotalPriceText.setText(String.format("$%.2f", order.getTotalPrice()));

        // Split the orderDateTime string to extract date
        if (order.getOrderDateTime() != null && !order.getOrderDateTime().isEmpty()) {
            String[] dateTimeParts = order.getOrderDateTime().split(" ");
            if (dateTimeParts.length == 2) {
                dateText.setText(dateTimeParts[0]);  // Date in YYYY-MM-DD
            }
        }

        convertView.setOnClickListener(v -> {
            OrderDetailsDialogManager orderDetailsDialogManager = new OrderDetailsDialogManager(order, context);
            orderDetailsDialogManager.show();
        });

        return convertView;
    }
}
