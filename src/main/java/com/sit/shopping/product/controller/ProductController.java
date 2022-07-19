package com.sit.shopping.product.controller;

import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public Product getProducts(@PathVariable String id) {
        Product product = productRepository.findByProductId(id);
        return product;
    }
}
