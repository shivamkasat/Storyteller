package com.storyteller.storyteller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
public class AuthorRequestDTO {
    @NotEmpty
    @Size(min = 50, max = 500, message = "bio should be between 50 to 500 characters")
    private String bio;

    private MultipartFile profilePicture;

    @NotNull
    @Min(1)
    private int userId;

}
