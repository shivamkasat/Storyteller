package com.storyteller.storyteller.dto;

import com.storyteller.storyteller.entity.User;
import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private int id;

    private String username;

    private String email;

    private Date dateOfBirth;

    private User.Role role;

    private Date dateJoined;
}
