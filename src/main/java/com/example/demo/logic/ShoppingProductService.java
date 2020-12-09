package com.example.demo.logic;

import java.util.List;

import com.example.demo.boundaries.Category;
import com.example.demo.boundaries.ProductBoundary;

public interface ShoppingProductService {

	public ProductBoundary getSpecificProduct(String productId);

	public List<Category> getAllCategories(int size, int page, String sortBy, String sortOrder);

	public List<ProductBoundary> searchByProductName(String filterValue, int size, int page, String sortBy, String sortOrder);

	public List<ProductBoundary> searchByMinimumPrice(String filterValue, int size, int page, String sortBy, String sortOrder);

	public List<ProductBoundary> searchByMaximumPrice(String filterValue, int size, int page, String sortBy, String sortOrder);

	public List<ProductBoundary> searchByCategoryName(String filterValue, int size, int page, String sortBy, String sortOrder);

	public List<ProductBoundary> getAllProducts(String filterValue, int size, int page, String sortBy, String sortOrder);

	public Category createCategory(Category category);

	public ProductBoundary createProduct(ProductBoundary category);

	public void delete();




	
}
