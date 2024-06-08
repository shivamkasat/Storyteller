package com.storyteller.storyteller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {
    @NotEmpty
    @Size(min = 3, max = 20, message = "category name should be between 3 to 20 characters")
    private String categoryName;
}
