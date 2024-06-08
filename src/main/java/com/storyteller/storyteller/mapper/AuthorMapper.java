package com.storyteller.storyteller.mapper;

import com.storyteller.storyteller.dto.AuthorDTO;
import com.storyteller.storyteller.dto.UserDTO;
import com.storyteller.storyteller.entity.Author;
import com.storyteller.storyteller.entity.User;

public class AuthorMapper {
    public static AuthorDTO toDTO(Author author) {
        User user = author.getUser();
        UserDTO userDTO = UserMapper.toDTO(user);

        AuthorDTO authorDTO = AuthorDTO.builder()
                .bio(author.getBio())
                .id(author.getId())
                .user(userDTO)
                .profilePictureUrl("/authors/picture/" + author.getId())
                .build();
        return authorDTO;
    }

}
