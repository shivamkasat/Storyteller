package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dao.RatingRepository;
import com.storyteller.storyteller.dao.StoryRepository;
import com.storyteller.storyteller.dao.UserRepsository;
import com.storyteller.storyteller.dto.RatingDTO;
import com.storyteller.storyteller.dto.RatingRequestDTO;
import com.storyteller.storyteller.entity.Rating;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.entity.User;
import com.storyteller.storyteller.mapper.RatingMapper;
import com.storyteller.storyteller.rest.UserRestController;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService{

    private RatingRepository ratingRepository;
    private UserRepsository userRepsository;

    private StoryRepository storyRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository,
                             UserRepsository userRepsository,
                             StoryRepository storyRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepsository = userRepsository;
        this.storyRepository = storyRepository;
    }

    @Override
    @Transactional
    public RatingDTO createRating(RatingRequestDTO ratingRequestDTO) {
        int userId = ratingRequestDTO.getUserId();
        int storyId = ratingRequestDTO.getStoryId();
        int ratingValue = ratingRequestDTO.getRatingValue();
        Optional<User> optionalUser = userRepsository.findById(userId);
        Optional< Story > optionalStory = storyRepository.findById(storyId);

        Rating rating = Rating.builder()
                .ratingValue(ratingValue)
                .user(optionalUser.get())
                .story(optionalStory.get())
                .ratingDate(new Date(System.currentTimeMillis()))
                .build();

        Rating savedRating = ratingRepository.save(rating);
        return RatingMapper.toDTO(savedRating);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public List<RatingDTO> findRatingsWithStoryId(int storyId) {
        return ratingRepository.findByStoryId(storyId).stream().map(RatingMapper::toDTO).collect(Collectors.toList());
    }
}
