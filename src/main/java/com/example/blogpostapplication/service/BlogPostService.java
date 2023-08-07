package com.example.blogpostapplication.service;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.dto.SimplifiedBlogPostDTO;

import java.util.List;

public interface BlogPostService {
    BlogPost createBlogPost(BlogPost blogPost);
    List<SimplifiedBlogPostDTO> getAllBlogPosts();
    BlogPost updateBlogPost(Long blogPostId, BlogPost blogPost);

}
