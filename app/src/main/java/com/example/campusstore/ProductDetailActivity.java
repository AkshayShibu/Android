package com.example.campusstore;

import android.content.ContentValues;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    DBHelper dbHelper;

    ImageView imgProduct;
    TextView txtProductName;
    TextView txtPrice;
    TextView txtCategory;
    TextView txtStock;
    TextView txtDescription;
    TextView txtQuantity;

    Button btnBack;
    Button btnMinus;
    Button btnPlus;
    Button btnAddToCart;
    Button btnBuy;

    Product product;

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        dbHelper = new DBHelper(this);

        imgProduct = findViewById(R.id.imgProduct);
        txtProductName = findViewById(R.id.txtProductName);
        txtPrice = findViewById(R.id.txtPrice);
        txtCategory = findViewById(R.id.txtCategory);
        txtStock = findViewById(R.id.txtStock);
        txtDescription = findViewById(R.id.txtDescription);
        txtQuantity = findViewById(R.id.txtQuantity);

        btnBack = findViewById(R.id.btnBack);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuy = findViewById(R.id.btnBuy);

        int productId =
                getIntent().getIntExtra(
                        "product_id",
                        -1
                );
        String customername=getIntent().getStringExtra("customer_name");
        if (productId == -1) {

            Toast.makeText(
                    this,
                    "Product not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        product =
                dbHelper.getProduct(productId);

        if (product == null) {

            Toast.makeText(
                    this,
                    "Product not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        displayProduct();

        btnBack.setOnClickListener(v -> finish());

        btnMinus.setOnClickListener(v -> {

            if (quantity > 1) {

                quantity--;

                txtQuantity.setText(
                        String.valueOf(quantity)
                );
            }
        });

        btnPlus.setOnClickListener(v -> {

            if (quantity < product.getStock()) {

                quantity++;

                txtQuantity.setText(
                        String.valueOf(quantity)
                );
            }
        });

        btnAddToCart.setOnClickListener(v -> {

            dbHelper.addToCart(
                    product.getId(),
                    quantity
            );

            Toast.makeText(
                    this,
                    "Added to cart successfully!",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // BUY
        btnBuy.setOnClickListener(v -> {

            if (product.getStock() <= 0) {

                btnBuy.setText("Out of Stock");
                btnBuy.setEnabled(false);

                return;
            }

            if (quantity > product.getStock()) {

                Toast.makeText(
                        this,
                        "Not enough stock",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Save sale in database
            dbHelper.addSale(
                    product.getId(),
                    quantity,
                    customername
            );

            // Reduce stock
            int newStock =
                    product.getStock() - quantity;

            dbHelper.updateStock(
                    product.getId(),
                    newStock
            );

            product.setStock(newStock);

            // Update screen
            txtStock.setText(
                    "Stock: " + newStock
            );

            Toast.makeText(
                    this,
                    "Bought",
                    Toast.LENGTH_SHORT
            ).show();

            // Reset quantity
            quantity = 1;

            txtQuantity.setText("1");

            // If no stock remains
            if (newStock == 0) {

                btnBuy.setText("Out of Stock");
                btnBuy.setEnabled(false);

                btnPlus.setEnabled(false);
            }
        });
    }

    private void displayProduct() {

        txtProductName.setText(
                product.getName()
        );

        txtPrice.setText(
                "₹" +
                        String.format(
                                "%.0f",
                                product.getPrice()
                        )
        );

        txtCategory.setText(
                product.getCategory()
        );

        txtStock.setText(
                "Stock: " +
                        product.getStock()
        );

        txtDescription.setText(
                product.getDescription()
        );

        txtQuantity.setText("1");

        if (product.getImageRes() != 0) {

            imgProduct.setImageResource(
                    product.getImageRes()
            );
        }

        if (product.getStock() <= 0) {

            btnBuy.setText("Out of Stock");
            btnBuy.setEnabled(false);

            btnPlus.setEnabled(false);
        }
    }
}