package com.storyteller.storyteller.mapper;

import com.storyteller.storyteller.dto.RatingDTO;
import com.storyteller.storyteller.dto.UserDTO;
import com.storyteller.storyteller.entity.Rating;
import com.storyteller.storyteller.entity.User;

import java.util.Optional;

public class RatingMapper {
    public static RatingDTO toDTO(Rating rating) {
        User user = rating.getUser();
        UserDTO userDTO = new UserDTO();
        if (user != null)
            userDTO = UserMapper.toDTO(user);

        RatingDTO ratingDTO = RatingDTO.builder()
                .ratingValue(rating.getRatingValue())
                .id(rating.getId())
                .ratingDate(rating.getRatingDate())
                .user(userDTO)
                .build();

        return ratingDTO;
    }
}
