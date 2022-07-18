package com.sit.shopping.product.repository;

import com.sit.shopping.exception.EntityNotFoundException;
import com.sit.shopping.product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryInMem implements ProductRepository {
    private List<Product> products;

    public ProductRepositoryInMem() {
        if (products == null) {
            this.products = new ArrayList<>();
        }
        if (products.isEmpty()) {
            Product product = new Product("T-shirt", 10.0, "https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/441598/item/goods_57_441598.jpg?width=1008&impolicy=quality_75");
            product.setId("74aae9d2-e871-4fe6-9414-a5658978af8b");
            this.products.add(product);
        }
    }
    @Override
    public Product findByProductId(String productId) {
        return this.products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("A product cannot be found"));
    }

    @Override
    public List<Product> findAll() {
        return this.products;
    }
}
