package Adapter;

import android.content.Context;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a1200134_nsralla_hassan_finalproject.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ObjectClasses.Order;

public class ViewAllOrdersAdapter extends ArrayAdapter<Order> {
    private Context context;
    private ArrayList<Order> orders;
    private ListView listView;
    private String userEmail;

    public ViewAllOrdersAdapter(Context context, ArrayList<Order> orders,ListView listView, String userEmail ){
        super(context, 0 , orders);
        this.context = context;
        this.orders = orders;
        this.listView = listView;
        this.userEmail = userEmail;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.order,parent, false);
        TextView emailT = convertView.findViewById(R.id.emailTextView);
        TextView TotalPriceT = convertView.findViewById(R.id.totalPriceTextView);
        TextView quantityT = convertView.findViewById(R.id.quantityTextView);
        TextView pizzaTypeT = convertView.findViewById(R.id.pizzaTypeTextView);
        TextView timeT = convertView.findViewById(R.id.timeTextView);
        TextView dateT = convertView.findViewById(R.id.dateTextView);
        TextView sizeT = convertView.findViewById(R.id.size);

        Order order = getItem(position);
        emailT.setText(order.getCustomerEmail());
        TotalPriceT.setText(String.valueOf(order.getTotalPrice()));
        quantityT.setText(String.valueOf(order.getQuantity())); // Assuming you have a method getQuantity() in Order class
        pizzaTypeT.setText(order.getPizzaType());
        sizeT.setText(order.getPizzaSize());

        if (order.getOrderDateTime() != null && !order.getOrderDateTime().isEmpty()) {
            String[] dateTimeParts = order.getOrderDateTime().split(" ");
            if (dateTimeParts.length == 2) {
                dateT.setText(dateTimeParts[0]);  // Date in YYYY-MM-DD
                timeT.setText(dateTimeParts[1]);
            }
        }
        return convertView;
    }
}
