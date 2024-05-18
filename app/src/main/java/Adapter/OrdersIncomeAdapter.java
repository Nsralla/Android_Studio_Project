package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.a1200134_nsralla_hassan_finalproject.R;
import java.util.ArrayList;
import ObjectClasses.OrderIncome;

public class OrdersIncomeAdapter extends ArrayAdapter<OrderIncome> {

    public OrdersIncomeAdapter(Context context, ArrayList<OrderIncome> ordersIncomeList) {
        super(context, 0, ordersIncomeList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order_income, parent, false);
        }

        OrderIncome orderIncome = getItem(position);

        TextView textPizzaType = convertView.findViewById(R.id.text_pizza_type);
        TextView textNumberOfOrders = convertView.findViewById(R.id.text_number_of_orders);
        TextView textTotalIncome = convertView.findViewById(R.id.text_total_income);

        textPizzaType.setText(orderIncome.getPizzaType());
        textNumberOfOrders.setText("Orders: " + orderIncome.getNumberOfOrders());
        textTotalIncome.setText("Income: $" + orderIncome.getTotalIncome());

        return convertView;
    }
}
