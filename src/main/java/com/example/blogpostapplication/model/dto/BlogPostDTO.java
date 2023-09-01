package com.example.blogpostapplication.model.dto;

import lombok.Data;

@Data
public class BlogPostDTO {

  private String blogPostTitle;

  private String blogPostText;

  public BlogPostDTO(BlogPostDTOBuilder blogPostDTOBuilder) {
    this.blogPostTitle = blogPostDTOBuilder.blogPostTitle;
    this.blogPostText = blogPostDTOBuilder.blogPostText;
  }

  public BlogPostDTO(){}

  public static BlogPostDTOBuilder builder(){
    return new BlogPostDTOBuilder();
}


  public static class BlogPostDTOBuilder {
    private String blogPostTitle;
    private String blogPostText;

    public BlogPostDTOBuilder blogPostTitle(String title) {
      this.blogPostTitle = title;
      return this;
    }

    public BlogPostDTOBuilder blogPostText(String text) {
      this.blogPostText = text;
      return this;
    }

    public BlogPostDTO build() {
      return new BlogPostDTO(this);
    }
  }
}
