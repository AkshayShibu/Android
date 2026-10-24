package com.example.campusstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerProducts;

    ImageButton btnCart;
    String customerName;
    Button btnAll;
    Button btnClothing,btnlogout;
    Button btnBooks;
    Button btnStationery;
    Button btnAccessories;
    Button btnSort;

    EditText searchBar;

    DBHelper dbHelper;

    ProductAdapter adapter;

    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Database
        dbHelper = new DBHelper(this);

        // Views
        recyclerProducts =
                findViewById(R.id.recyclerProducts);

        customerName = getIntent().getStringExtra(
                "customer_name"
        );
        btnAll =
                findViewById(R.id.btnAll);

        btnClothing =
                findViewById(R.id.btnClothing);

        btnBooks =
                findViewById(R.id.btnBooks);

        btnStationery =
                findViewById(R.id.btnStationery);
        btnlogout=findViewById(R.id.btn_logout);

        btnAccessories =
                findViewById(R.id.btnAccessories);

        btnSort =
                findViewById(R.id.btnSort);

        searchBar =
                findViewById(R.id.searchBar);


        // 2 column grid
        recyclerProducts.setLayoutManager(
                new GridLayoutManager(this, 2)
        );


        // Load all products when page opens
        loadProducts();


        // ==============================
        // CATEGORY BUTTONS
        // ==============================

        btnAll.setOnClickListener(v ->
                loadProducts());

        btnClothing.setOnClickListener(v ->
                loadCategory("Clothing"));

        btnBooks.setOnClickListener(v ->
                loadCategory("Books"));

        btnStationery.setOnClickListener(v ->
                loadCategory("Stationery"));

        btnAccessories.setOnClickListener(v ->
                loadCategory("Accessories"));

        btnlogout.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
        // ==============================
        // SEARCH
        // ==============================

        searchBar.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        String keyword =
                                s.toString().trim();

                        if (keyword.isEmpty()) {

                            loadProducts();

                        } else {

                            searchProducts(keyword);
                        }
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                });


        // ==============================
        // SORT BUTTON
        // ==============================

        btnSort.setOnClickListener(v -> showSortMenu());

    }


    // ==============================
    // LOAD ALL PRODUCTS
    // ==============================

    private void loadProducts() {

        productList =
                dbHelper.getAllProducts();

        adapter = new ProductAdapter(
                productList,
                customerName
        );

        recyclerProducts.setAdapter(adapter);
    }


    // ==============================
    // LOAD CATEGORY
    // ==============================

    private void loadCategory(String category) {

        productList =
                dbHelper.getProductsByCategory(category);

        adapter =
                new ProductAdapter(productList,customerName);

        recyclerProducts.setAdapter(adapter);
    }


    // ==============================
    // SEARCH PRODUCTS
    // ==============================

    private void searchProducts(String keyword) {

        productList =
                dbHelper.searchProducts(keyword);

        adapter =
                new ProductAdapter(productList,customerName);

        recyclerProducts.setAdapter(adapter);
    }


    // ==============================
    // SORT MENU
    // ==============================

    private void showSortMenu() {

        PopupMenu popup = new PopupMenu(this, btnSort);

        popup.getMenu().add("Low to High");
        popup.getMenu().add("High to Low");

        popup.setOnMenuItemClickListener(item -> {

            if (item.getTitle().toString().equals("Low to High")) {

                productList.sort((a, b) ->
                        Double.compare(a.getPrice(), b.getPrice()));

            } else {

                productList.sort((a, b) ->
                        Double.compare(b.getPrice(), a.getPrice()));
            }

            adapter.notifyDataSetChanged();

            return true;
        });

        popup.show();
    }
}