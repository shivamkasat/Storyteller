package com.storyteller.storyteller.mapper;

import com.storyteller.storyteller.dto.UserDTO;
import com.storyteller.storyteller.entity.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(),
                user.getEmail(), user.getDateOfBirth(),
                user.getRole(), user.getDateJoined());
        return userDTO;
    }
}
