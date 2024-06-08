package com.storyteller.storyteller.rest;

import com.storyteller.storyteller.dto.CategoryDTO;
import com.storyteller.storyteller.dto.CategoryRequestDTO;
import com.storyteller.storyteller.dto.StoryDTO;
import com.storyteller.storyteller.entity.Category;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.exception.CategoryNotFound;
import com.storyteller.storyteller.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryRequestDTO category) {
        CategoryDTO createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/{categoryId}/stories")
    public ResponseEntity<List<StoryDTO>> findStoriesByCategory(@PathVariable int categoryId) {
        try {
            List<StoryDTO> categoryStories = categoryService.findStoriesByCategoryId(categoryId);
            return ResponseEntity.ok(categoryStories);
        } catch (CategoryNotFound ex) {
            System.out.println("here we go " + ex.getStackTrace());
            return ResponseEntity.notFound().build();
        }
    }
}
