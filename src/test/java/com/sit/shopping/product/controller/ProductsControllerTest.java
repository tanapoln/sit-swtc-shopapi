package com.sit.shopping.product.controller;

import com.sit.shopping.product.dto.ProductsResponse;
import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

class ProductsControllerTest {
    private ProductsController underTest;
    private ProductRepository mockProductRepository;

    @BeforeEach
    void setUp() {
        underTest = new ProductsController();

        mockProductRepository = Mockito.mock(ProductRepository.class);
        underTest.setProductRepository(mockProductRepository);
    }

    @Test
    void testGetProducts() {
        Mockito.when(mockProductRepository.findAll()).thenReturn(List.of(new Product()));

        ProductsResponse response = underTest.getProducts();

        MatcherAssert.assertThat(response.getData().size(), CoreMatchers.equalTo(1));
    }

    @Test
    void testGetEmptyProducts() {
        Mockito.when(mockProductRepository.findAll()).thenReturn(Collections.emptyList());

        ProductsResponse response = underTest.getProducts();

        MatcherAssert.assertThat(response.getData().size(), CoreMatchers.equalTo(0));
    }
}