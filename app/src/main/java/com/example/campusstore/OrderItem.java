package com.example.campusstore;

public class OrderItem {

    private int id;
    private int productId;
    private String productName;
    private int quantity;
    private double price;


    // Empty constructor
    public OrderItem() {
    }


    // Constructor used by DBHelper
    public OrderItem(
            int id,
            int productId,
            String productName,
            int quantity,
            double price) {

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }


    // Get ID
    public int getId() {
        return id;
    }


    // Get product ID
    public int getProductId() {
        return productId;
    }


    // Get product name
    public String getProductName() {
        return productName;
    }


    // Get quantity
    public int getQuantity() {
        return quantity;
    }


    // Get price
    public double getPrice() {
        return price;
    }
}