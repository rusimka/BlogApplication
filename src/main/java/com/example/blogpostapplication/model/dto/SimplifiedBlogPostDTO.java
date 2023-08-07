package com.example.blogpostapplication.model.dto;

public class SimplifiedBlogPostDTO {

    private String blogPostTitle;
    private String blogPostShortSummary;

    public String getBlogPostTitle() {
        return blogPostTitle;
    }

    public void setBlogPostTitle(String blogPostTitle) {
        this.blogPostTitle = blogPostTitle;
    }

    public String getBlogPostShortSummary() {
        return blogPostShortSummary;
    }

    public void setBlogPostShortSummary(String blogPostShortSummary) {
        this.blogPostShortSummary = blogPostShortSummary;
    }
}
