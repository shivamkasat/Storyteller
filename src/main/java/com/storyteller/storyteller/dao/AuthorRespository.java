package com.storyteller.storyteller.dao;

import com.storyteller.storyteller.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRespository extends JpaRepository<Author, Integer> {
}
