package com.sit.shopping.product.controller;

import com.sit.shopping.product.dto.ProductsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @GetMapping
    public ProductsResponse getProducts() {
        return new ProductsResponse();
    }
}
