package com.sit.shopping.product.controller;

import com.sit.shopping.product.dto.ProductsResponse;
import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ProductsResponse getProducts() {
        List<Product> products = productRepository.findAll();
        return new ProductsResponse(products);
    }
}
