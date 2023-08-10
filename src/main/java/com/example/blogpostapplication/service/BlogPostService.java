package com.example.blogpostapplication.service;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.SimplifiedBlogPostDTO;

import java.util.List;
import java.util.Map;

public interface BlogPostService {
  BlogPost createBlogPost(BlogPost blogPost);

  List<SimplifiedBlogPostDTO> getAllBlogPosts();

  BlogPost updateBlogPost(Long blogPostId, Map<String, String> blogPost);

  List<BlogPost> getAllBlogPostByTagName(String tagName);

  void addTagsToBlogPost(Long blogPostId, Tag tag);

  void deleteTagFromBlogPost(Long blogPostId, Tag tag);
}
