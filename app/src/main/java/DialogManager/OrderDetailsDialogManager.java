package DialogManager;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.a1200134_nsralla_hassan_finalproject.R;
import ObjectClasses.Order;

public class OrderDetailsDialogManager {
    private Context context;
    private Dialog dialog;
    TextView pizzaTypeText;
    TextView pizzaTotalPriceText;
    TextView pizzaSizeText;
    TextView pizzaPriceText;
    TextView dateText;
    TextView pizzaQuantityText;
    TextView timeText;
    private Order order;
//    private String pizzaCategory;
    Button closeDialog;

    public OrderDetailsDialogManager(Order order, Context context) {
        this.order = order;
        this.context = context;
        initDialog();
    }

    private void initDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_order_details);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_dialog_bg));
        dialog.setCancelable(false);

        pizzaTypeText = dialog.findViewById(R.id.tvOrderType_dialog);
        pizzaSizeText = dialog.findViewById(R.id.tvOrderSize_dialog);
        pizzaPriceText = dialog.findViewById(R.id.tvOrderPrice_dialog);
        pizzaQuantityText = dialog.findViewById(R.id.tvOrderQuantity_dialog);
        pizzaTotalPriceText = dialog.findViewById(R.id.tvOrderTotalPrice_dialog);
        dateText = dialog.findViewById(R.id.tvOrderDate_dialog);
        timeText = dialog.findViewById(R.id.tvOrderTime_dialog);
        closeDialog = dialog.findViewById(R.id.buttonCloseDialog);

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
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public void show() {
        dialog.show();
    }
}
