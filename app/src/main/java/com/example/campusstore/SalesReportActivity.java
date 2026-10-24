package com.example.campusstore;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SalesReportActivity extends AppCompatActivity {

    TextView salesList;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sales_report);

        salesList =
                findViewById(R.id.salesList);

        db = new DBHelper(this);

        ArrayList<Order> orders =
                db.getAllOrders();

        String text = "";

        for (Order order : orders) {

            text = text +
                    "Order ID: " +
                    order.getId() +
                    "\n";

            text = text +
                    "Products: ";

            ArrayList<OrderItem> items =
                    db.getOrderItems(
                            order.getId()
                    );

            for (OrderItem item : items) {

                text = text +
                        item.getProductName() +
                        " x " +
                        item.getQuantity() +
                        ", ";
            }

            text = text +
                    "\n";

            text = text +
                    "Cost: ₹" +
                    order.getTotalAmount() +
                    "\n";

            text = text +
                    "Customer: " +
                    order.getCustomerName() +
                    "\n\n";
        }

        if (text.isEmpty()) {

            text = "No orders found";
        }

        salesList.setText(text);
    }
}