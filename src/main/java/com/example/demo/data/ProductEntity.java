package com.example.demo.data;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

@Node(labels = "PRODUCTS")
public class ProductEntity {
	@Id private Long id;
	private String name;
	private double price;
	private String image;
	@Relationship(type = "labled_as", direction = Direction.OUTGOING) private CategoryEntity category;
	private String productDetails;

	public ProductEntity() {
		super();
	}

	public ProductEntity(Long id, String name, double price, String image, String productDetails,
			CategoryEntity category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.productDetails = productDetails;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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


	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

}
