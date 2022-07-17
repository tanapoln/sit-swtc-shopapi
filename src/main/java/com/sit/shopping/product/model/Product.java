package com.sit.shopping.product.model;

public class Product {
    private String id;
    private String name;
    private double price;
    private String imageUrl;

    public Product() {
        this.id = "generated-" + System.currentTimeMillis();
    }

    public Product(String name, double price, String imageUrl) {
        this();
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
