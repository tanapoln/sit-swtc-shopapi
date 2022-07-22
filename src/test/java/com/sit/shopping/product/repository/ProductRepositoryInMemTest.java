package com.sit.shopping.product.repository;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sit.shopping.exception.EntityNotFoundException;
import com.sit.shopping.product.model.Product;

class ProductRepositoryInMemTest {
	private ProductRepositoryInMem underTest;
	private Product testProduct;

	@BeforeEach
	void setUp() {
		underTest = new ProductRepositoryInMem();

		testProduct = Product.create("test product", 10, "https://image");
		underTest.addProduct(testProduct);
	}

	@Test
	void testFindByProductId() {
		Product product = underTest.findByProductId(testProduct.getId());

		MatcherAssert.assertThat(product.getId(), CoreMatchers.equalTo(testProduct.getId()));
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

		MatcherAssert.assertThat(products.size(), Matchers.greaterThan(0));
		MatcherAssert.assertThat(products.stream().findFirst().get().getId(), CoreMatchers.equalTo(testProduct.getId()));
	}
}
