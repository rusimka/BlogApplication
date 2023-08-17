package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.BlogPostDTO;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.service.BlogPostService;
import com.example.blogpostapplication.service.TagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogPostServiceImpl implements BlogPostService {

  private final BlogPostRepository blogPostRepository;

  private final TagService tagService;

  @Value("${blogpost.summary.length}")
  private int summaryLength;

  private static final String EXCEPTION_TEXT = "Blog post with ID %d not found";

  public BlogPostServiceImpl(BlogPostRepository blogPostRepository, TagService tagService) {
    this.blogPostRepository = blogPostRepository;
    this.tagService = tagService;
  }

  @Override
  public BlogPost createBlogPost(BlogPostDTO blogPostDTO) {
    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostTitle(blogPostDTO.getBlogPostTitle());
    blogPost.setBlogPostText(blogPostDTO.getBlogPostText());
    return this.blogPostRepository.save(blogPost);
  }

  @Override
  public List<BlogPostDTO> getAllBlogPosts() {
    List<BlogPost> allBlogPosts = blogPostRepository.findAll();
    return allBlogPosts.stream().map(this::mapToSimplifiedDTO).toList();
  }

  private BlogPostDTO mapToSimplifiedDTO(BlogPost blogPost) {
    BlogPostDTO blogPostDTO = new BlogPostDTO();
    blogPostDTO.setBlogPostTitle(blogPost.getBlogPostTitle());
    blogPostDTO.setBlogPostText(getShortSummary(blogPost.getBlogPostText()));
    return blogPostDTO;
  }

  private String getShortSummary(String blogPostText) {
    return blogPostText.length() > summaryLength
        ? blogPostText.substring(0, summaryLength) + " ..."
        : blogPostText;
  }

  @Override
  public BlogPost updateBlogPost(Long blogPostId, BlogPostDTO blogPostDTO) {
    BlogPost updatedBlogPost =
        this.blogPostRepository
            .findById(blogPostId)
            .orElseThrow(
                () -> new RecordNotFoundException(String.format(EXCEPTION_TEXT, blogPostId)));

    Optional.ofNullable(blogPostDTO.getBlogPostTitle())
        .ifPresent(updatedBlogPost::setBlogPostTitle);

    Optional.ofNullable(blogPostDTO.getBlogPostText()).ifPresent(updatedBlogPost::setBlogPostText);

    return blogPostRepository.save(updatedBlogPost);
  }

  @Override
  public List<BlogPost> getAllBlogPostByTagName(String tagName) {
    return Optional.ofNullable(tagService.findTagByTagName(tagName))
        .map(Tag::getBlogPosts)
        .orElse(Collections.emptyList());
  }

  @Override
  public void addTagsToBlogPost(Long blogPostId, TagDTO tagDTO) {

    BlogPost blogPost =
        blogPostRepository
            .findById(blogPostId)
            .orElseThrow(
                () -> new RecordNotFoundException(String.format(EXCEPTION_TEXT, blogPostId)));

    Tag newTag = tagService.findTagByTagName(tagDTO.getTagName());

    blogPost.getTags().add(newTag);
    blogPostRepository.save(blogPost);
  }

  @Override
  public void deleteTagFromBlogPost(Long blogPostId, TagDTO tagDTO) {
    BlogPost blogPost =
        blogPostRepository
            .findById(blogPostId)
            .orElseThrow(
                () -> new RecordNotFoundException(String.format(EXCEPTION_TEXT, blogPostId)));

    Tag tagToDelete = tagService.findTagByTagName(tagDTO.getTagName());

    blogPost.getTags().remove(tagToDelete);
    blogPostRepository.save(blogPost);
  }
}
