package com.example.campusstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    Button btnAddProduct;
    Button btnRemoveProduct;
    Button btnSalesReport;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);

        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnRemoveProduct = findViewById(R.id.btnRemoveProduct);
        btnSalesReport = findViewById(R.id.btnSalesReport);
        btnLogout = findViewById(R.id.btnLogout);

        btnAddProduct.setOnClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, AddProductActivity.class));
        });

        btnRemoveProduct.setOnClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, RemoveProductActivity.class));
        });

        btnSalesReport.setOnClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, SalesReportActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            finish();
        });
    }
}