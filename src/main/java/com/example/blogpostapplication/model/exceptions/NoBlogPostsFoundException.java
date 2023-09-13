package com.example.blogpostapplication.model.exceptions;

public class NoBlogPostsFoundException extends RuntimeException {
    public NoBlogPostsFoundException(Long userId) {
        super(String.format("User with ID %d, doesn't have blog posts",userId));
    }
}
