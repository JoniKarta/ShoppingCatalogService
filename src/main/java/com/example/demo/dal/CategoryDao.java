package com.example.demo.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.data.CategoryEntity;

// Method not being used for now (using default findById), need to check
public interface CategoryDao extends PagingAndSortingRepository<CategoryEntity, String> {
	public List<CategoryEntity> findAllByName(@Param("name") String name, Pageable pageable);
}
