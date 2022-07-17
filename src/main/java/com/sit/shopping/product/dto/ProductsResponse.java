package com.sit.shopping.product.dto;

import com.sit.shopping.product.model.Product;

import java.util.List;

public class ProductsResponse {
    private List<Product> data;

    public ProductsResponse(List<Product> data) {
        this.data = data;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
