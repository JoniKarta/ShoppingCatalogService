package com.example.demo.logic;

import java.util.List;

import com.example.demo.boundaries.Category;
import com.example.demo.boundaries.ProductBoundary;

public interface ShoppingProductService {

	ProductBoundary getSpecificProduct(String productId);

	List<Category> getAllCategories(int size, int page, String sortBy, String sortOrder);

	List<ProductBoundary> searchByMinimumPrice(String value, int size, int page, String sortAttribute, String sortOrder);

	List<ProductBoundary> searchByMaximumPrice(String value, int size, int page, String sortAttribute, String sortOrder);


}
