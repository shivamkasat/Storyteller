package com.storyteller.storyteller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RatingRequestDTO {
    @NotNull
    @Min(value = 1, message = "min rating you can give is 1")
    @Max(value = 5, message = "max rating you can give is 5")
    private int ratingValue;

    @NotNull
    @Min(1)
    private int storyId;

    @NotNull
    @Min(1)
    private int userId;
}
