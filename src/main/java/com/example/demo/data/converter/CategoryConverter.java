package com.example.demo.data.converter;

import org.springframework.stereotype.Component;

import com.example.demo.boundaries.Category;
import com.example.demo.data.CategoryEntity;

@Component
public class CategoryConverter {

	public Category toBoundary(CategoryEntity cEntity) {
		Category c = new Category(cEntity.getName(), cEntity.getDescription());
		return c;
	}

	public CategoryEntity toEntity(Category cBoundary) {
		CategoryEntity c = new CategoryEntity(cBoundary.getName(), cBoundary.getDescription());
		return c;
	}

}
