package com.storyteller.storyteller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CommentRequestDTO {
    @NotEmpty
    @Size(min = 5, max = 5000, message = "comment should be between 5 to 5000 characters")
    private String content;

    @NotNull
    @Min(1)
    private int userId;

    @NotNull
    @Min(1)
    private int storyId;
}
