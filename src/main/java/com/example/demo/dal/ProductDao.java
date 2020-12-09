package com.example.demo.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.data.ProductEntity;

public interface ProductDao extends PagingAndSortingRepository<ProductEntity, Long> {
	
	public List<ProductEntity> findAllByPriceLessThanEqual(@Param("price") double price, Pageable pageable);
	
	public List<ProductEntity> findAllByPriceGreaterThanEqual(@Param("price") double price, Pageable pageable);
	
    public List<ProductEntity> findAllByNameLikeIgnoreCase(@Param("name") String name, Pageable pageable);

    // TODO need to improve the performance using set 
	public List<ProductEntity> findAllByCategory_name(@Param("name") String productName, Pageable pageable);
}
