package com.storyteller.storyteller.dao;

import com.storyteller.storyteller.entity.Author;
import com.storyteller.storyteller.entity.Category;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.entity.User;

import java.util.Optional;

public interface AppDao {
    void save(User user);

    Optional<Author> findAuthorById(int id);

    void saveStory(Story story);

    Optional<Category> findCategoryById(int id);

    Optional<Author> findAuthorByIdWithStories(int id);

    Optional<Category> findCategoryByIdWithStories(int id);
}
