package com.sit.shopping.product.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import com.sit.shopping.exception.EntityNotFoundException;
import com.sit.shopping.product.model.Product;

@Repository
public class ProductRepositoryInMem implements ProductRepository, InitializingBean {
	private final List<Product> products = new ArrayList<>();

	public ProductRepositoryInMem() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.products.add(Product.create("Loose Cropped Jeans (Damaged)", 42.57,
				"https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/448429/sub/goods_448429_sub13"
						+ ".jpg?width=1600&impolicy=quality_75"));
		this.products.add(Product.create("Smart Skort Solid", 28.28,
				"https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/455844/sub/goods_455844_sub14"
						+ ".jpg?width=1600&impolicy=quality_75"));
		this.products.add(Product.create("Smart Tucked Shorts", 22.57,
				"https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/453555/sub/goods_453555_sub14"
						+ ".jpg?width=1600&impolicy=quality_75"));
		this.products.add(Product.create("Printed Cotton Square Neck Short Sleeve Mini Dress", 28.28,
				"https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/449183/sub/goods_449183_sub13"
						+ ".jpg?width=1600&impolicy=quality_75"));
		this.products.add(Product.create("Printed Button Down Camisole Flare Dress", 42.57,
				"https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/455793/sub/goods_455793_sub14"
						+ ".jpg?width=1600&impolicy=quality_75"));
		this.products.add(Product.create("Smooth Cotton Tiered Sleeveless Dress", 22.57,
				"https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/452896/sub/goods_452896_sub14"
						+ ".jpg?width=1600&impolicy=quality_75"));
		this.products.add(Product.create("Soft Twill Stands Collar Long Sleeve Shirt", 28.28,
				"https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/446995/sub/goods_446995_sub13"
						+ ".jpg?width=1600&impolicy=quality_75"));
		this.products.add(Product.create("Oxford Striped Slim Fit Long Sleeve Shirt", 28.28,
				"https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/452300/sub/goods_452300_sub14"
						+ ".jpg?width=1600&impolicy=quality_75"));
		this.products.add(Product.create("Dry Pique Wide Horizontal Stripes Short Sleeve Polo", 22.57,
				"https://image.uniqlo.com/UQ/ST3/AsianCommon/imagesgoods/455676/sub/goods_455676_sub14"
						+ ".jpg?width=1600&impolicy=quality_75"));

	}

	@Override
	public Product findByProductId(String productId) {
		return this.products.stream().filter(product -> product.getId().equals(productId)).findFirst()
				.orElseThrow(() -> new EntityNotFoundException("A product cannot be found"));
	}

	@Override
	public List<Product> findAll() {
		return this.products;
	}

	public void addProduct(Product p) {
		this.products.add(p);
	}
}
