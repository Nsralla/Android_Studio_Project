package Admin_Navbar_classes;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.a1200134_nsralla_hassan_finalproject.R;
import java.util.ArrayList;

import Adapter.OrdersIncomeAdapter;
import Database.DataBaseHelper;
import ObjectClasses.OrderIncome;

public class CalculateOrdersIncomeFragment extends Fragment {

    private TextView textOverallIncome;
    private ListView listViewOrdersIncome;
    private OrdersIncomeAdapter adapter;
    private DataBaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate_orders_income, container, false);

        textOverallIncome = view.findViewById(R.id.text_overall_income);
        listViewOrdersIncome = view.findViewById(R.id.list_view_orders_income);
        dbHelper = new DataBaseHelper(getContext(), "1200134_nsralla_hassan_finalProject", null, 1);

        ArrayList<OrderIncome> ordersIncomeList = getOrdersIncome();
        adapter = new OrdersIncomeAdapter(getContext(), ordersIncomeList);
        listViewOrdersIncome.setAdapter(adapter);

        double overallIncome = calculateOverallIncome(ordersIncomeList);
        textOverallIncome.setText("Overall Income: $" + overallIncome);

        return view;
    }

    private ArrayList<OrderIncome> getOrdersIncome() {
        ArrayList<OrderIncome> ordersIncomeList = new ArrayList<>();
        Cursor cursor = dbHelper.getOrdersIncome();

        if (cursor.moveToFirst()) {
            do {
                String pizzaType = cursor.getString(0);
                int numberOfOrders = cursor.getInt(1);
                double totalIncome = cursor.getDouble(2);
                System.out.println("total income = "+ totalIncome);
                ordersIncomeList.add(new OrderIncome(pizzaType, numberOfOrders, totalIncome));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ordersIncomeList;
    }

    private double calculateOverallIncome(ArrayList<OrderIncome> ordersIncomeList) {
        double overallIncome = 0;
        for (OrderIncome orderIncome : ordersIncomeList) {
            overallIncome += orderIncome.getTotalIncome();
        }
        return overallIncome;
    }
}
