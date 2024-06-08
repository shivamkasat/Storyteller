package com.storyteller.storyteller.dao;

import com.storyteller.storyteller.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Integer> {
}
