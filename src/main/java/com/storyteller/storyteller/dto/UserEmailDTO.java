package com.storyteller.storyteller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserEmailDTO {
    @Email
    @NotEmpty
    private String email;
}
