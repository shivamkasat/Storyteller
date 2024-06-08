package com.storyteller.storyteller.dto;

import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDTO {
    private int id;

    private int ratingValue;

    private Date ratingDate;

    private UserDTO user;
}
