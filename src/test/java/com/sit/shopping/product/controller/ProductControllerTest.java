package com.sit.shopping.product.controller;

import com.sit.shopping.exception.EntityNotFoundException;
import com.sit.shopping.product.model.Product;
import com.sit.shopping.product.repository.ProductRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class ProductControllerTest {
    private ProductController underTest;
    private ProductRepository mockProductRepository;

    @BeforeEach
    void setUp() {
        underTest = new ProductController();

        mockProductRepository = Mockito.mock(ProductRepository.class);
        underTest.setProductRepository(mockProductRepository);
    }

    @Test
    void testGetProductById() {
        String expectedId = "id-product-001";
        Product expected = new Product("p1", 10.0, "https://image.com/p1.png");
        expected.setId(expectedId);

        Mockito.when(mockProductRepository.findByProductId(expectedId)).thenReturn(expected);

        Product product = underTest.getProductById(expectedId);

        MatcherAssert.assertThat(product.getId(), CoreMatchers.equalTo(expected.getId()));
        MatcherAssert.assertThat(product.getName(), CoreMatchers.equalTo(expected.getName()));
        MatcherAssert.assertThat(product.getPrice(), CoreMatchers.equalTo(expected.getPrice()));
        MatcherAssert.assertThat(product.getImageUrl(), CoreMatchers.equalTo(expected.getImageUrl()));
    }

    @Test
    void testGetProductByIdButNotFound() {
        String expectedId = "id-product-001";

        Mockito.when(mockProductRepository.findByProductId(expectedId)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            underTest.getProductById(expectedId);
        });
    }
}