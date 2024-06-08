package com.storyteller.storyteller.dao;

import com.storyteller.storyteller.entity.Author;
import com.storyteller.storyteller.entity.Category;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AppDaoImpl implements AppDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public Optional<Author> findAuthorById(int id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    @Transactional
    public void saveStory(Story story) {
        entityManager.persist(story);
    }

    @Override
    public Optional<Category> findCategoryById(int id) {
        return Optional.ofNullable(entityManager.find(Category.class, id));
    }

    @Override
    public Optional<Author> findAuthorByIdWithStories(int id) {
        TypedQuery<Author> query = entityManager.createQuery("SELECT a from Author a JOIN FETCH a.stories where a.id = :id", Author.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Optional<Category> findCategoryByIdWithStories(int id) {
        TypedQuery<Category> query = entityManager.createQuery("SELECT c from Category c JOIN FETCH c.stories where c.id = :id", Category.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }
}
