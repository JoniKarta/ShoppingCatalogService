package com.example.demo.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.data.ProductEntity;

/**
 * 
 * @TODO: Fix the methods
 *
 */
public interface ProductDao extends PagingAndSortingRepository<ProductEntity, Long> {
	
	public List<ProductEntity> findAllByPriceLessThanEqual(@Param("price") double price, Pageable pageable);
	
	public List<ProductEntity> findAllByPriceGreaterThanEqual(@Param("price") double price, Pageable pageable);

}
