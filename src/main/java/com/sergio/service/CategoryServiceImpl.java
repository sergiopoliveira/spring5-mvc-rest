package com.sergio.service;

import java.util.List;
import java.util.stream.Collectors;

import com.sergio.api.v1.mapper.CategoryMapper;
import com.sergio.api.v1.model.CategoryDTO;
import com.sergio.domain.Category;
import com.sergio.repositories.CategoryRepository;

public class CategoryServiceImpl implements CategoryService{

	private final CategoryMapper categoryMapper;
	private final CategoryRepository categoryRepository;
	
	public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
		this.categoryMapper = categoryMapper;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {

		return categoryRepository.findAll()
				.stream()
				.map(categoryMapper::categoryToCategoryDTO)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {
		return categoryMapper.categoryToCategoryDTO(categoryRepository.findByName(name));
	}

}
