package com.example.demo.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Path.ReturnValueNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.boundaries.Category;
import com.example.demo.boundaries.ProductBoundary;
import com.example.demo.dal.CategoryDao;
import com.example.demo.dal.ProductDao;
import com.example.demo.data.CategoryEntity;
import com.example.demo.data.ProductConverter;
import com.example.demo.data.ProductEntity;
import com.example.demo.exceptions.DuplicateCategoryFoundException;
import com.example.demo.exceptions.DuplicateProductFoundException;
import com.example.demo.exceptions.ProductNotFoundException;

@Service
public class ShoppingProductServiceMockup implements ShoppingProductService {
	
	private ProductDao productDao;
	private CategoryDao categoryDao;
	private ProductConverter productConverter;

	@Autowired
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Autowired
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Autowired
	public void setProductConverter(ProductConverter productConverter) {
		this.productConverter = productConverter;
	}

	@Override
	@Transactional
	public List<Category> getAllCategories(int size, int page, String sortBy, String sortOrder) {
		
		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;
		
		return this.categoryDao.findAll(PageRequest.of(page, size, direction, sortBy)).stream()
				.map(this.productConverter::toBoundaryCategory).collect(Collectors.toList());

//		List<ProductBoundary> pList = this.productDao.findAll(PageRequest.of(page, size, direction, sortBy)).stream()
//				.map(this.productConverter::entityToBoundary).collect(Collectors.toList());
//		List<Category> cList = new ArrayList<Category>();
//		for (ProductBoundary p : pList) {
//			cList.add(p.getCategory());
//		}
//		return cList;
	}

	@Override
	@Transactional
	public ProductBoundary getSpecificProduct(String productId) {
		Optional<ProductEntity> productFromDB = this.productDao.findById(Long.parseLong(productId));
		if (productFromDB.isPresent())
			return this.productConverter.entityToBoundary(productFromDB.get());
		throw new ProductNotFoundException("could not find product by id: " + productId);
	}

	@Override
	public List<ProductBoundary> searchByMinimumPrice(String value, int size, int page, String sortAttribute,
			String sortOrder) {

		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;

		return this.productDao
				.findAllByPriceGreaterThanEqual(Double.parseDouble(value),
						PageRequest.of(page, size, direction, sortAttribute))
				.stream().map(this.productConverter::entityToBoundary).collect(Collectors.toList());
//		return null;
	}

	@Override
	public List<ProductBoundary> searchByMaximumPrice(String value, int size, int page, String sortAttribute,
			String sortOrder) {

		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;

		return this.productDao
				.findAllByPriceLessThanEqual(Double.parseDouble(value),
						PageRequest.of(page, size, direction, sortAttribute))
				.stream().map(this.productConverter::entityToBoundary).collect(Collectors.toList());
	}

	// Kinda overkill since there has to be only one category with the same name
	// which means the list will have 1 category max.
	// Two implementations
	@Override
	public Category createCategory(Category category) {
		/*
		 * List<Category> cateList; int i = 0; if (category != null) { do { cateList =
		 * this.categoryDao .findAllByName(category.getName(),
		 * PageRequest.of(i, (i + 1) * 10, Direction.ASC, "name")) .stream()
		 * .map(this.productConverter::toBoundaryCategory)
		 * .collect(Collectors.toList());
		 * 
		 * for (Category categoryFromDB : cateList) { if
		 * (categoryFromDB.getName().equalsIgnoreCase(category.getName())) { throw new
		 * DuplicateCategoryFoundException( "There is existing category with the name: "
		 * + category.getName()); } } i++; } while (!cateList.isEmpty()); }
		 * CategoryEntity savedCategory =
		 * this.categoryDao.save(this.productConverter.toCategoryEntity(category));
		 * return this.productConverter.toBoundaryCategory(savedCategory);
		 */
		
		Optional<CategoryEntity> categoryEntity = this.categoryDao.findById(category.getName());
		if (categoryEntity.isPresent()) {
			throw new DuplicateCategoryFoundException("Category with this name already exists");
		}
		CategoryEntity savedCategory = this.categoryDao.save(this.productConverter.toCategoryEntity(category));
		return this.productConverter.toBoundaryCategory(savedCategory);
	}

	@Override
	public ProductBoundary createProduct(ProductBoundary productBoundary) {
		Optional<ProductEntity> productEntity = this.productDao.findById(Long.parseLong(productBoundary.getId()));
		if (productEntity.isPresent()) {
			throw new DuplicateProductFoundException("Product with this ID already exists");
		}

		Optional<CategoryEntity> categoryEntity = this.categoryDao.findById(productBoundary.getCategory().getName());
		if (!categoryEntity.isPresent()) {
			throw new DuplicateCategoryFoundException("Category with this name does not exist");
		}

		ProductEntity savedProduct = this.productDao.save(this.productConverter.BoundaryToEntity(productBoundary));
		return this.productConverter.entityToBoundary(savedProduct);
	}

}
