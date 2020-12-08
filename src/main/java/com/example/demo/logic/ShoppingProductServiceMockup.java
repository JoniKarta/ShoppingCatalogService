package com.example.demo.logic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Override
	public Category createCategory(Category category) {
		Optional<CategoryEntity> categoryEntity = this.categoryDao.findOneByName(category.getName());
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
		
		//List<CategoryEntity> categoryEntities = this.categoryDao.findAllByName(productBoundary.getCategory().getName());
		Optional<CategoryEntity> categoryEntity = this.categoryDao.findOneByName(productBoundary.getCategory().getName());
		if (!categoryEntity.isPresent()/* categoryEntities.isEmpty() */) {
			throw new DuplicateCategoryFoundException("Category with this name does not exist");
		}
		
		ProductEntity savedProduct = this.productConverter.BoundaryToEntity(productBoundary);
		//savedProduct.setCategory(categoryEntities.get(0));
		savedProduct.setCategory(categoryEntity.get());
		savedProduct = this.productDao.save(savedProduct);
	
		return this.productConverter.entityToBoundary(savedProduct);
	}
}
