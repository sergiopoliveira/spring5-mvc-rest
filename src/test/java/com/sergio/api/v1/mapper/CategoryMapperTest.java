package com.sergio.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sergio.api.v1.model.CategoryDTO;
import com.sergio.domain.Category;

public class CategoryMapperTest {

	private static final long ID = 1L;
	private static final String NAME = "Joe";
	
	CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

	@Test
	public void categoryToCategoryTest() throws Exception {

		// given
		Category category = new Category();
		category.setName(NAME);
		category.setId(ID);

		// when
		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

		// then
		assertEquals(Long.valueOf(ID), categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}

}
