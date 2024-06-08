package com.storyteller.storyteller.exception;

import com.storyteller.storyteller.entity.User;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
