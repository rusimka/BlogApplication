package com.example.blogpostapplication.model.exceptions;

public class BlogPostDoesNotExists extends RuntimeException {
  public BlogPostDoesNotExists(Long blogPostId) {
    super(String.format("Blog post with id: %d does not exists", blogPostId));
  }
}
