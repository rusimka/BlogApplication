package com.example.blogpostapplication.service;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.dto.BlogPostDTO;
import com.example.blogpostapplication.model.dto.TagDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogPostService {
  BlogPost createBlogPost(BlogPostDTO blogPostDTO);

  Page<BlogPostDTO> getAllBlogPosts(int page,int size);

  BlogPost updateBlogPost(Long blogPostId, BlogPostDTO blogPostDTO);

  List<BlogPost> getAllBlogPostByTagName(String tagName);

  void addTagsToBlogPost(Long blogPostId, TagDTO tagDTO);

  void deleteTagFromBlogPost(Long blogPostId, TagDTO tagDTO);

  List<BlogPostDTO> getAllBlogPostsByUserId(Long userId);

  void deleteAllBlogPostsByUserId();

  void deleteBlogPostByBlogPostId(Long blogPostId);

}
