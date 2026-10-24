package com.example.campusstore;

public class Order {

    private int id;
    private String orderDate;
    private double totalAmount;
    private String customerName;


    // Empty constructor
    public Order() {
    }


    // Constructor used by DBHelper
    public Order(
            int id,
            String orderDate,
            double totalAmount,
            String customerName) {

        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.customerName = customerName;
    }


    // Get ID
    public int getId() {
        return id;
    }


    // Get order date
    public String getOrderDate() {
        return orderDate;
    }


    // Get total amount
    public double getTotalAmount() {
        return totalAmount;
    }


    // Get customer name
    public String getCustomerName() {
        return customerName;
    }
}