package com.storyteller.storyteller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryRequestDTO {
    @NotEmpty
    @Size(min=10, max = 100, message = "story title should be between 10 and 50 characters")
    private String title;

    @NotEmpty
    @Size(min = 100, max = 100000, message = "story content should be between 100 and 100000")
    private String content;

    private MultipartFile coverImage;

    @NotNull
    @Min(value = 1)
    private int authorId;

    @NotNull
    @Min(1)
    private int categoryId;
}
