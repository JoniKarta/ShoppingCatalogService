package com.example.demo.boundaries;

import java.util.Map;

public class ProductBoundary {
	private String id;
	private String name;
	private double price;
	private String image;
	private Category category;
	private Map<String, Object> productDetails;

	public ProductBoundary() {
		super();
	}

	public ProductBoundary(String id, String name, double price, String image, Map<String, Object> productDetails,
			Category category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.productDetails = productDetails;
		this.category = category;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Map<String, Object> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(Map<String, Object> productDetails) {
		this.productDetails = productDetails;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
