package com.storyteller.storyteller.mapper;

import com.storyteller.storyteller.dto.CategoryDTO;
import com.storyteller.storyteller.entity.Category;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryName(category.getCategoryName());
        return categoryDTO;
    }
}
