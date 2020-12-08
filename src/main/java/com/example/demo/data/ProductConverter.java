package com.example.demo.data;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.example.demo.boundaries.Category;
import com.example.demo.boundaries.ProductBoundary;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProductConverter {
	private ObjectMapper jackson;
	
	@PostConstruct
	public void setup() {
		jackson = new ObjectMapper();
	}

	public ProductBoundary entityToBoundary(ProductEntity entity) {
		ProductBoundary boundary = new ProductBoundary(entity.getId().toString(), entity.getName(), entity.getPrice(),
				entity.getImage(), unMarshElementAttribute(entity.getProductDetails()), toBoundaryCategory(entity.getCategory()));
		return boundary;
	}

	public ProductEntity BoundaryToEntity(ProductBoundary boundary) {
		ProductEntity entity = new ProductEntity(Long.parseLong(boundary.getId()), boundary.getName(),
				boundary.getPrice(), boundary.getImage(), marshElementAttribute(boundary.getProductDetails()),
				toCategoryEntity(boundary.getCategory()));
		return entity;
	}

	public Category toBoundaryCategory(CategoryEntity cEntity) {
		Category c = new Category(cEntity.getName(), cEntity.getDescription());
		return c;
	}

	public CategoryEntity toCategoryEntity(Category cBoundary) {
		CategoryEntity c = new CategoryEntity(cBoundary.getName(), cBoundary.getDescription());
		return c;
	}
	

	public String marshElementAttribute(Map<String, Object> input) {
		String elementMarshaling = null;
		try {
			elementMarshaling = this.jackson.writeValueAsString(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return elementMarshaling;
	}

	public Map<String, Object> unMarshElementAttribute(String input) {
		Map<String, Object> elementAttribute;
		try {
			elementAttribute = this.jackson.readValue(input, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return elementAttribute;
	}
}
