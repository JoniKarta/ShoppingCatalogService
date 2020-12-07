package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.boundaries.Category;
import com.example.demo.boundaries.ProductBoundary;
import com.example.demo.logic.ShoppingProductService;
import com.example.demo.utility.ControllerTypes;

@RestController
public class ShoppingProductController {
	


	private ShoppingProductService shoppingProductService;

	@Autowired
	public void setShoppingProductService(ShoppingProductService shoppingProductService) {
		this.shoppingProductService = shoppingProductService;
	}
	

	
	
	@RequestMapping(
			path = "/shopping/products/{productId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductBoundary getSpecificProduct(@PathVariable("productId") String productId) {
		return this.shoppingProductService.getSpecificProduct(productId);
	}
	
	@RequestMapping(
			path = "/shopping/categories",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Category[] getAllCategories(
			@RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") String sortOrder,
			@RequestParam(name= "page", required = false, defaultValue = "0") int page,
			@RequestParam(name= "size", required = false, defaultValue = "10") int size) {
		
		return this.shoppingProductService.getAllCategories(size, page, sortBy, sortOrder).toArray(new Category[0]);
	}
	
	
	@RequestMapping(
			path = "/shopping/products",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductBoundary[] searchProduct (
			@RequestParam(name = "filterType", required = false, defaultValue = "") String type,
			@RequestParam(name = "filterValue", required = false) String value,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortAttribute,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") String sortOrder) {
			
		switch (type) {
		case ControllerTypes.BY_NAME:
			break;
		
		case ControllerTypes.BY_MINIMUM_PRICE:
			return this.shoppingProductService.searchByMinimumPrice(value, size, page, sortAttribute, sortOrder).toArray(new ProductBoundary[0]);
		
		case ControllerTypes.BY_MAXIMUM_PRICE:
			return this.shoppingProductService.searchByMaximumPrice(value, size, page, sortAttribute, sortOrder).toArray(new ProductBoundary[0]);
		
		
		case ControllerTypes.BY_CATEGORY_NAME:
			break;
		default:
			break; 
		}
		
		return null;
		
	}
			
	
	
	
}
