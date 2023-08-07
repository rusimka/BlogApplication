package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.SimplifiedBlogPostDTO;
import com.example.blogpostapplication.model.exceptions.BlogPostDoesNotExists;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.BlogPostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogPostServiceImpl implements BlogPostService {

  private final BlogPostRepository blogPostRepository;

  public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
    this.blogPostRepository = blogPostRepository;
  }

  @Override
  public BlogPost createBlogPost(BlogPost blogPost) {
    return this.blogPostRepository.save(blogPost);
  }

  @Override
  public List<SimplifiedBlogPostDTO> getAllBlogPosts() {
    List<BlogPost> allBlogPosts = blogPostRepository.findAll();
    return allBlogPosts.stream().map(this::mapToSimplifiedDTO).collect(Collectors.toList());
  }

  private SimplifiedBlogPostDTO mapToSimplifiedDTO(BlogPost blogPost) {
    SimplifiedBlogPostDTO simplifiedBlogPostDTO = new SimplifiedBlogPostDTO();
    simplifiedBlogPostDTO.setBlogPostTitle(blogPost.getBlogPostTitle());
    simplifiedBlogPostDTO.setBlogPostShortSummary(getShortSummary(blogPost.getBlogPostText()));
    return simplifiedBlogPostDTO;
  }

  private String getShortSummary(String blogPostText) {
    return blogPostText.substring(0, 6);
  }

 @Override
  public BlogPost updateBlogPost(Long blogPostId, BlogPost blogPost) {
    BlogPost updatedBlogPost =
        this.blogPostRepository
            .findById(blogPostId)
            .orElseThrow(() -> new BlogPostDoesNotExists(blogPostId));
    updatedBlogPost.setBlogPostTitle(blogPost.getBlogPostTitle());
    updatedBlogPost.setBlogPostText(blogPost.getBlogPostText());
    return this.blogPostRepository.save(updatedBlogPost);
  }


}
