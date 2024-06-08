package com.storyteller.storyteller.rest;

import com.storyteller.storyteller.dto.UserDTO;
import com.storyteller.storyteller.dto.UserEmailDTO;
import com.storyteller.storyteller.dto.UserRequestDTO;
import com.storyteller.storyteller.entity.User;
import com.storyteller.storyteller.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Map;

// TODO: User class date_joined not setting while creating user from post call

@RestController
@RequestMapping("/users")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int userId) {
        try {
            UserDTO user = userService.findById(userId);
            return ResponseEntity.ok(user);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserRequestDTO user) {
        UserDTO createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{userId}/email")
    ResponseEntity<UserDTO> updateUser(@PathVariable int userId, @RequestBody @Valid UserEmailDTO userEmailBody) {
        try {
            UserDTO userDTO = userService.updateEmail(userEmailBody, userId);
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException ex) {
           return ResponseEntity.notFound().build();
        }
    }
}
