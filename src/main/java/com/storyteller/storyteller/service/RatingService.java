package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dto.RatingDTO;
import com.storyteller.storyteller.dto.RatingRequestDTO;
import com.storyteller.storyteller.entity.Rating;

import java.util.List;

public interface RatingService {

    RatingDTO createRating(RatingRequestDTO ratingRequestDTO);

    void deleteById(int id);

    List<RatingDTO> findRatingsWithStoryId(int storyId);
}
