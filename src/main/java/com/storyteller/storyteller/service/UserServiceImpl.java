package com.storyteller.storyteller.service;

import com.storyteller.storyteller.dao.UserRepsository;
import com.storyteller.storyteller.dto.UserDTO;
import com.storyteller.storyteller.dto.UserEmailDTO;
import com.storyteller.storyteller.dto.UserRequestDTO;
import com.storyteller.storyteller.entity.Author;
import com.storyteller.storyteller.entity.Story;
import com.storyteller.storyteller.entity.User;
import com.storyteller.storyteller.exception.UserNotFoundException;
import com.storyteller.storyteller.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepsository userRepsository;

    @Autowired
    public UserServiceImpl(UserRepsository userRepsository) {
        this.userRepsository = userRepsository;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepsository.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(int id) throws UserNotFoundException{
        Optional<User> optionalUser = userRepsository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with " + id + "not found");
        }
        User user = optionalUser.get();
        UserDTO userDTO = UserMapper.toDTO(user);
        return userDTO;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
       Optional<User> optionalUser = userRepsository.findById(id);
       if (optionalUser.isEmpty()) {
           throw new UserNotFoundException("User with " + id + " not found");
       }
       User user = optionalUser.get();
       Author author = user.getAuthor();
       if (author != null) {
           List<Story> authorStories = author.getStories();
           for(Story story : authorStories) {
               story.setAuthor(null);
           }
       }
       userRepsository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserRequestDTO user) {
        User newUser = User.builder()
                .username(user.getUsername())
                .passwordHash(user.getPassword())
                .email(user.getEmail())
                .dateJoined(user.getDateOfBirth())
                .role(user.getRole())
                .dateJoined(new Date(System.currentTimeMillis()))
                .build();

        User savedUser = userRepsository.save(newUser);
        UserDTO savedUserDto = UserMapper.toDTO(savedUser);
        return savedUserDto;
    }

    @Override
    public UserDTO updateEmail(UserEmailDTO userEmailDTO, int userId) {
        Optional<User> optionalUser = userRepsository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }

        User user = optionalUser.get();
        user.setEmail(userEmailDTO.getEmail());
        User savedUser = userRepsository.save(user);
        return UserMapper.toDTO(savedUser);
    }


}
