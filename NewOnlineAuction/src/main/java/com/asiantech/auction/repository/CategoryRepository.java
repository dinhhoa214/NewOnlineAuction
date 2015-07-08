package com.asiantech.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.data.jpa.repository.Query;

import com.asiantech.auction.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	@Query("select c from Category c where c.name like %?1")
	Category findOneByString(String firstname);
}
