package com.example.blogpostapplication.model.exceptions;

public class UsernameDoesNotExistsException extends RuntimeException {
    public UsernameDoesNotExistsException(Long userId){
        super(String.format("User with ID %d not found",userId));
}
}
