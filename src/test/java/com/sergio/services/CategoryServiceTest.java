package com.sergio.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyString;

import com.sergio.api.v1.mapper.CategoryMapper;
import com.sergio.api.v1.model.CategoryDTO;
import com.sergio.domain.Category;
import com.sergio.repositories.CategoryRepository;
import com.sergio.service.CategoryService;
import com.sergio.service.CategoryServiceImpl;

public class CategoryServiceTest {

	private static final long ID = 1L;
	private static final String NAME = "Joe";

	CategoryService categoryService;

	@Mock
	CategoryRepository categoryRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
	}

	@Test
	public void getAllCategories() throws Exception {

		// given
		List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

		when(categoryRepository.findAll()).thenReturn(categories);

		// when
		List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();

		// then
		assertEquals(3, categoryDTOs.size());
	}

	@Test
	public void getCategoryByName() throws Exception {

		// given
		Category category = new Category();
		category.setId(ID);
		category.setName(NAME);

		when(categoryRepository.findByName(anyString())).thenReturn(category);

		// when
		CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

		// then
		assertEquals(Long.valueOf(ID), categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}

}
