package com.storyteller.storyteller.dao;

import com.storyteller.storyteller.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByStoryId(int storyId);
}
