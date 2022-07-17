package com.sit.shopping.cart.model;

import com.sit.shopping.product.model.Product;

import java.util.List;

public class Cart {
    private String id;
    private List<Product> products;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
