package com.example.campusstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RemoveProductActivity extends AppCompatActivity {

    EditText id;
    Button remove;
    TextView productList;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remove_product);

        id = findViewById(R.id.id);
        remove = findViewById(R.id.remove);
        productList = findViewById(R.id.productList);

        db = new DBHelper(this);

        // Display products
        ArrayList<Product> products = db.getAllProducts();

        String text = "";

        for (Product product : products) {
            text = text + product.getId() + " - "
                    + product.getName() + "\n";
        }

        productList.setText(text);

        // Remove product
        remove.setOnClickListener(v -> {

            int productId = Integer.parseInt(
                    id.getText().toString()
            );

            db.removeProduct(productId);

            Toast.makeText(
                    this,
                    "Product Removed",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });
    }
}