package com.sit.shopping.product.model;

import java.util.UUID;

public class Product {
	private String id;
	private String name;
	private double price;
	private String imageUrl;

	public Product() {
	}

	public static Product create(String name, double price, String imageUrl) {
		Product product = new Product();
		product.id = UUID.randomUUID().toString();
		product.name = name;
		product.price = price;
		product.imageUrl = imageUrl;
		return product;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
