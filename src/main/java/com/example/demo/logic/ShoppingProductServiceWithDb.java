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
import com.example.demo.data.ProductEntity;
import com.example.demo.data.converter.CategoryConverter;
import com.example.demo.data.converter.ProductConverter;
import com.example.demo.exceptions.DuplicateCategoryFoundException;
import com.example.demo.exceptions.DuplicateProductFoundException;
import com.example.demo.exceptions.ProductNotFoundException;

@Service
public class ShoppingProductServiceWithDb implements ShoppingProductService {

	private ProductDao productDao;
	private CategoryDao categoryDao;
	private ProductConverter productConverter;
	private CategoryConverter categoryConverter;

	@Autowired
	public void setCategoryConverter(CategoryConverter categoryConverter) {
		this.categoryConverter = categoryConverter;
	}

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
	@Transactional(readOnly = true)
	public List<Category> getAllCategories(int size, int page, String sortBy, String sortOrder) {

		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;

		return this.categoryDao.findAll(PageRequest.of(page, size, direction, sortBy)).stream()
				.map(this.categoryConverter::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ProductBoundary getSpecificProduct(String productId) {
		Optional<ProductEntity> productFromDB = this.productDao.findById(Long.parseLong(productId));
		if (productFromDB.isPresent())
			return this.productConverter.entityToBoundary(productFromDB.get());
		throw new ProductNotFoundException("could not find product by id: " + productId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductBoundary> searchByProductName(String filterValue, int size, int page, String sortBy,
			String sortOrder) {
		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;
		return this.productDao.findAllByNameLikeIgnoreCase(filterValue, PageRequest.of(page, size, direction, sortBy))
				.stream().map(this.productConverter::entityToBoundary).collect(Collectors.toList());

	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductBoundary> searchByMinimumPrice(String value, int size, int page, String sortAttribute,
			String sortOrder) {
		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;
		return this.productDao
				.findAllByPriceGreaterThanEqual(Double.parseDouble(value),
						PageRequest.of(page, size, direction, sortAttribute))
				.stream().map(this.productConverter::entityToBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductBoundary> searchByMaximumPrice(String value, int size, int page, String sortAttribute,
			String sortOrder) {
		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;
		return this.productDao
				.findAllByPriceLessThanEqual(Double.parseDouble(value),
						PageRequest.of(page, size, direction, sortAttribute))
				.stream().map(this.productConverter::entityToBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Category createCategory(Category category) {
		Optional<CategoryEntity> categoryEntity = this.categoryDao.findOneByName(category.getName());
		if (categoryEntity.isPresent()) {
			throw new DuplicateCategoryFoundException("Category with this name already exists");
		}
		CategoryEntity savedCategory = this.categoryDao.save(this.categoryConverter.toEntity(category));
		return this.categoryConverter.toBoundary(savedCategory);
	}

	@Override
	@Transactional
	public ProductBoundary createProduct(ProductBoundary productBoundary) {

		Optional<ProductEntity> productEntity = this.productDao.findById(Long.parseLong(productBoundary.getId()));
		if (productEntity.isPresent()) {
			throw new DuplicateProductFoundException("Product with this ID already exists");
		}
		Optional<CategoryEntity> categoryEntity = this.categoryDao
				.findOneByName(productBoundary.getCategory().getName());
		if (!categoryEntity.isPresent()) {
			throw new DuplicateCategoryFoundException("Category with this name does not exist");
		}

		ProductEntity savedProduct = this.productConverter.boundaryToEntity(productBoundary);
		savedProduct.setCategory(categoryEntity.get());
		savedProduct = this.productDao.save(savedProduct);

		return this.productConverter.entityToBoundary(savedProduct);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductBoundary> getAllProducts(String filterValue, int size, int page, String sortBy,
			String sortOrder) {
		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;

		return this.productDao.findAll(PageRequest.of(page, size, direction, sortBy)).getContent().stream()
				.map(this.productConverter::entityToBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductBoundary> searchByCategoryName(String filterValue, int size, int page, String sortBy,
			String sortOrder) {
		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;
		return this.productDao.findAllByCategory_name(filterValue, PageRequest.of(page, size, direction, sortBy))
				.stream().map(this.productConverter::entityToBoundary).collect(Collectors.toList());

	}

	@Override
	@Transactional
	public void delete() {
		this.productDao.deleteAll();
		this.categoryDao.deleteAll();
	}

}
