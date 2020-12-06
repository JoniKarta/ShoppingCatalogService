package com.example.demo.data;

import com.example.demo.boundaries.Category;
import com.example.demo.boundaries.ProductBoundary;

public class ProductConverter {
	public ProductBoundary entityToBoundary(ProductEntity entity) {
		ProductBoundary boundary = new ProductBoundary(entity.getId().toString(),entity.getName(),entity.getPrice(),
				entity.getImage(),entity.getProductDetails(),toBoundaryCategory( entity.getCategory()));
		return boundary;
	}
	
	public ProductEntity BoundaryToEntity(ProductBoundary boundary) {
		ProductEntity entity = new ProductEntity(Long.parseLong(boundary.getId()), boundary.getName(), boundary.getPrice(),
				boundary.getImage(), boundary.getProductDetails(),toCategoryEntity(boundary.getCategory()));
		return entity;
	}
	
	public Category toBoundaryCategory(CategoryEntity cEntity) {
		Category c =new Category(cEntity.getName(),cEntity.getDescription());
		return c;
	}
	
	public CategoryEntity toCategoryEntity(Category cBoundary) {
		CategoryEntity c= new CategoryEntity(cBoundary.getName(),cBoundary.getDescription());
		return c;
	}
}
