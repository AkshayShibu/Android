package com.example.campusstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    EditText name, description, price, category, stock;
    Button add;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_product);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        category = findViewById(R.id.category);
        stock = findViewById(R.id.stock);

        add = findViewById(R.id.add);

        db = new DBHelper(this);

        add.setOnClickListener(v -> {

            String n = name.getText().toString();
            String d = description.getText().toString();
            double p = Double.parseDouble(price.getText().toString());
            String c = category.getText().toString();
            int s = Integer.parseInt(stock.getText().toString());

            db.addProduct(n, d, p, c, s);

            Toast.makeText(
                    this,
                    "Product Added",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });
    }
}