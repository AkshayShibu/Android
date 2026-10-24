package com.example.campusstore;

public class Product {

    private int id;
    private String name;
    private String description;
    private double price;
    private int imageRes;
    private String category;
    private int stock;

    public Product() {
    }

    public Product(int id, String name, String description,
                   double price, int imageRes,
                   String category, int stock) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageRes = imageRes;
        this.category = category;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}