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
public class PizzaOrdersAdapter extends ArrayAdapter<Order> {
    Context context;
    ArrayList<Order>orders;
    ListView listView;
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
        TextView dateText = convertView.findViewById(R.id.tvOrderDate);

        pizzaTypeText.setText(order.getPizzaType());
        pizzaTotalPriceText.setText(String.format("$%.2f", order.getTotalPrice()));

        // Split the orderDateTime string to extract date and time
        if (order.getOrderDateTime() != null && !order.getOrderDateTime().isEmpty()) {
            String[] dateTimeParts = order.getOrderDateTime().split(" ");
            if (dateTimeParts.length == 2) {
                dateText.setText(dateTimeParts[0]);  // Date in YYYY-MM-DD
            }
        }
        convertView.setOnClickListener( v->{
            OrderDetailsDialogManager orderDetailsDialogManager = new OrderDetailsDialogManager(order,context );
            orderDetailsDialogManager.show();
        });
        return convertView;
    }
}