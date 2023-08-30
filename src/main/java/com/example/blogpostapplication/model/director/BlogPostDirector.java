package com.example.blogpostapplication.model.director;

import com.example.blogpostapplication.model.dto.BlogPostDTO;

public class BlogPostDirector {

  private BlogPostDTO.BlogPostDTOBuilder blogPostDTOBuilder;

  public BlogPostDirector() {
    this.blogPostDTOBuilder = new BlogPostDTO.BlogPostDTOBuilder();
  }

  public BlogPostDTO createCustomBlogPost(String title, String text) {
    return blogPostDTOBuilder.blogPostTitle(title).blogPostText(text).build();
  }
}
