package com.example.demo.data;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;


/*
@Entity
@Table(name = "PRODUCTS")*/
@Node(labels = "PRODUCTS")
public class ProductEntity {
	@Id private Long id;
	private String name;
	private double price;
	private String image;
	@Relationship(type = "of", direction = Direction.INCOMING) private CategoryEntity category;
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

	//@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Need to check this (repeated column name error without)
	//@Column(name = "product_name", insertable = false, updatable = false)
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

	//@Convert(converter = com.example.demo.data.MapToJsonConverter.class)
	//@Lob
	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	//@Embedded
	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

}
