package com.storyteller.storyteller.dto;

import com.storyteller.storyteller.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestDTO {
    @NotEmpty
    @Size(min = 5,
            max = 50,
            message = "username length should be between 5 and 50 characters.")
    private String username;

    //TODO: create custom validator for password
    // custom password validator can be added in frontend as well
    // to prevent some attack that can corrupt data we should add validation in backend as well
    @NotEmpty
    @Size(min = 8, message = "minimum length of password should be greater than 8 characters.")
    private String password;

    @NotEmpty
    @Email
    private String email;

    @Past(message = "We are not serving future users so soon! Hope you would have born in past.")
    private Date dateOfBirth;

    private User.Role role;

}
