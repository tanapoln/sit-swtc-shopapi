package com.sit.shopping.product.repository;

import com.sit.shopping.product.model.Product;

import java.util.List;

public interface ProductRepository {
    Product findByProductId(String productId);

    List<Product> findAll();
}
