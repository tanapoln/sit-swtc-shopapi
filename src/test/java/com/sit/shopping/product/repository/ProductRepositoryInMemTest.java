package com.sit.shopping.product.repository;

import com.sit.shopping.exception.EntityNotFoundException;
import com.sit.shopping.product.model.Product;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProductRepositoryInMemTest {
    private ProductRepositoryInMem underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProductRepositoryInMem();
    }

    @Test
    void testFindByProductId() {
        String productId = "74aae9d2-e871-4fe6-9414-a5658978af8b";

        Product product = underTest.findByProductId(productId);

        MatcherAssert.assertThat(product.getId(), CoreMatchers.equalTo(productId));
    }

    @Test
    void testFindByProductIdNotFound() {
        String productId = "xabsf-INVALID-e871-4fe6-9414-a5658978af8b";

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            underTest.findByProductId(productId);
        });
    }

    @Test
    void testFindAll() {
        List<Product> products = underTest.findAll();

        MatcherAssert.assertThat(products.size(), CoreMatchers.equalTo(1));
        MatcherAssert.assertThat(products.stream().findFirst().get().getId(), CoreMatchers.equalTo("74aae9d2-e871-4fe6-9414-a5658978af8b"));
    }
}