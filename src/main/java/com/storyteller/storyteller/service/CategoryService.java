package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dto.CategoryDTO;
import com.storyteller.storyteller.dto.CategoryRequestDTO;
import com.storyteller.storyteller.dto.StoryDTO;
import com.storyteller.storyteller.entity.Category;
import com.storyteller.storyteller.entity.Story;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO createCategory(CategoryRequestDTO category);

    List<StoryDTO> findStoriesByCategoryId(int id);
}
