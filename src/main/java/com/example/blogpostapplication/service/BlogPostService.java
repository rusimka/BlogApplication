package com.example.blogpostapplication.service;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.BlogPostDTO;
import com.example.blogpostapplication.model.dto.TagDTO;

import java.util.List;
import java.util.Map;

public interface BlogPostService {
  BlogPost createBlogPost(BlogPostDTO blogPostDTO);

  List<BlogPostDTO> getAllBlogPosts();

  BlogPost updateBlogPost(Long blogPostId, Map<String, String> blogPost);

  List<BlogPost> getAllBlogPostByTagName(String tagName);

  void addTagsToBlogPost(Long blogPostId, TagDTO tagDTO);

  void deleteTagFromBlogPost(Long blogPostId, TagDTO tagDTO);
}
