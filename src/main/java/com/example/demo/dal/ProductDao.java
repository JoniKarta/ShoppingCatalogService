package com.example.demo.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.data.*;


public interface ProductDao extends PagingAndSortingRepository<ProductEntity, Long>{
	
	public List<ProductEntity> findAllByPriceLessThenEqual(@Param("price") double price, Pageable pageable);
	
	public List<ProductEntity> findAllByPriceGreatThenEqual(@Param("price") double price, Pageable pageable);


}
