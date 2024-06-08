package com.storyteller.storyteller.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {
    private int id;
    private String bio;
    private UserDTO user;
    private String profilePictureUrl;
}
