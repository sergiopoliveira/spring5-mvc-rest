package com.sergio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergio.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Category findByName(String name);
}
