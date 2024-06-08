package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dto.UserDTO;
import com.storyteller.storyteller.dto.UserEmailDTO;
import com.storyteller.storyteller.dto.UserRequestDTO;
import com.storyteller.storyteller.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();

    UserDTO findById(int id);

    void deleteById(int id);

    UserDTO createUser(UserRequestDTO user);

    UserDTO updateEmail(UserEmailDTO userEmail, int userId);

}
