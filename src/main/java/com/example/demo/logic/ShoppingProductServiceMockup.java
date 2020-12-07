package com.example.demo.logic;

import java.util.ArrayList;
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
import com.example.demo.dal.ProductDao;
import com.example.demo.data.ProductConverter;
import com.example.demo.data.ProductEntity;
import com.example.demo.exceptions.ProductNotFoundException;

@Service
public class ShoppingProductServiceMockup implements ShoppingProductService {
	private ProductDao productDao;
	private ProductConverter productConverter;

	@Autowired
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Autowired
	public void setProductConverter(ProductConverter productConverter) {
		this.productConverter = productConverter;
	}

	@Override
	@Transactional
	public List<Category> getAllCategories(int size, int page, String sortBy, String sortOrder) {
		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;
		List<ProductBoundary> pList = this.productDao.findAll(PageRequest.of(page, size, direction, sortBy)).stream()
				.map(this.productConverter::entityToBoundary).collect(Collectors.toList());
		List<Category> cList = new ArrayList<Category>();
		for (ProductBoundary p : pList) {
			cList.add(p.getCategory());
		}
		return cList;
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
				.findAllByPriceGreatThenEqual(Double.parseDouble(value),
						PageRequest.of(page, size, direction, sortAttribute))
				.stream().map(this.productConverter::entityToBoundary).collect(Collectors.toList());
	}

	@Override
	public List<ProductBoundary> searchByMaximumPrice(String value, int size, int page, String sortAttribute,
			String sortOrder) {

		Direction direction = sortOrder.equals(Direction.ASC.toString()) ? Direction.ASC : Direction.DESC;

		return this.productDao
				.findAllByPriceLessThenEqual(Double.parseDouble(value),
						PageRequest.of(page, size, direction, sortAttribute))
				.stream().map(this.productConverter::entityToBoundary).collect(Collectors.toList());
	}

}
