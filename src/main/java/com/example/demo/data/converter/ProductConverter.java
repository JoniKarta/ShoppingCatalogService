package com.example.demo.data.converter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.boundaries.ProductBoundary;
import com.example.demo.data.ProductEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProductConverter {
	private ObjectMapper jackson;

	@Autowired
	private CategoryConverter categoryConverter;

	public ProductConverter() {
		jackson = new ObjectMapper();
	}

	public ProductBoundary entityToBoundary(ProductEntity entity) {
		ProductBoundary boundary = new ProductBoundary(entity.getId().toString(), entity.getName(), entity.getPrice(),
				entity.getImage(), unMarshElementAttribute(entity.getProductDetails()),
				categoryConverter.toBoundary(entity.getCategory()));
		return boundary;
	}

	public ProductEntity boundaryToEntity(ProductBoundary boundary) {
		ProductEntity entity = new ProductEntity(Long.parseLong(boundary.getId()), boundary.getName(),
				boundary.getPrice(), boundary.getImage(), marshElementAttribute(boundary.getProductDetails()),
				categoryConverter.toEntity(boundary.getCategory()));
		return entity;
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
