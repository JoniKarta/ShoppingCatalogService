package com.example.demo.data;

import org.springframework.stereotype.Component;

import com.example.demo.boundaries.Category;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO remove category functionality from product and move to here.
//@Component
public class CategoryConverter {
	private ObjectMapper jackson;
	
	
	public CategoryConverter() {
		jackson = new ObjectMapper();
	}
	
	
	public Category toBoundary(CategoryEntity cEntity) {
		Category c = new Category(cEntity.getName(), cEntity.getDescription());
		return c;
	}

	public CategoryEntity toEntity(Category cBoundary) {
		CategoryEntity c = new CategoryEntity(cBoundary.getName(), cBoundary.getDescription());
		return c;
	}
	

	
	
	
}
