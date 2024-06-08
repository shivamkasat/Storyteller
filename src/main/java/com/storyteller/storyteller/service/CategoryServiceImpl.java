package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dao.CategoryRepository;
import com.storyteller.storyteller.dto.CategoryDTO;
import com.storyteller.storyteller.dto.CategoryRequestDTO;
import com.storyteller.storyteller.dto.StoryDTO;
import com.storyteller.storyteller.entity.Category;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.exception.CategoryNotFound;
import com.storyteller.storyteller.mapper.CategoryMapper;
import com.storyteller.storyteller.mapper.StoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> allCategories = categoryRepository.findAll();
        return allCategories.stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setCategoryName(categoryRequestDTO.getCategoryName());
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDTO(savedCategory);
    }

    @Override
    public List<StoryDTO> findStoriesByCategoryId(int id) throws CategoryNotFound{
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFound("Category with " + id + " doesn't exist");
        }

        Category category = optionalCategory.get();
        return category.getStories().stream().map(StoryMapper::toDTO).collect(Collectors.toList());
    }
}
