package com.example.campusstore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter
        extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> productList;
    private String customerName;

    public ProductAdapter(
            ArrayList<Product> productList,
            String customerName) {

        this.productList = productList;
        this.customerName = customerName;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ProductViewHolder holder,
            int position) {

        Product product = productList.get(position);

        holder.txtProductName.setText(
                product.getName());

        holder.txtCategory.setText(
                product.getCategory());

        holder.txtPrice.setText(
                "₹" + String.format(
                        "%.0f",
                        product.getPrice()
                )
        );

        // Show image if one is available
        if (product.getImageRes() != 0) {

            holder.imgProduct.setImageResource(
                    product.getImageRes());
        }

        // Open Product Details when product is clicked
        holder.itemView.setOnClickListener(v -> {

            Context context = v.getContext();

            Intent intent = new Intent(
                    context,
                    ProductDetailActivity.class
            );

            // Send product ID to ProductDetailActivity
            intent.putExtra(
                    "product_id",
                    product.getId()
            );

            intent.putExtra(
                    "customer_name",
                    customerName
            );

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder
            extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtProductName;
        TextView txtCategory;
        TextView txtPrice;

        public ProductViewHolder(
                @NonNull View itemView) {

            super(itemView);

            imgProduct = itemView.findViewById(
                    R.id.imgProduct);

            txtProductName = itemView.findViewById(
                    R.id.txtProductName);

            txtCategory = itemView.findViewById(
                    R.id.txtCategory);

            txtPrice = itemView.findViewById(
                    R.id.txtPrice);
        }
    }
}