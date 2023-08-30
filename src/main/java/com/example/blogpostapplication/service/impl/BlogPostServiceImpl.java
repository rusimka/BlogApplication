package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.BlogPostDTO;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.service.BlogPostService;
import com.example.blogpostapplication.service.TagService;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BlogPostServiceImpl implements BlogPostService {

  private static final String EXCEPTION_TEXT = "Blog post with ID %d not found";
  private final BlogPostRepository blogPostRepository;
  private final TagService tagService;

  @Value("${blogpost.summary.length}")
  private int summaryLength;

  public BlogPostServiceImpl(BlogPostRepository blogPostRepository, TagService tagService) {
    this.blogPostRepository = blogPostRepository;
    this.tagService = tagService;
  }

  public BlogPost getBlogPostById(Long blogPostId) {
    return blogPostRepository
        .findById(blogPostId)
        .orElseThrow(() -> new RecordNotFoundException(String.format(EXCEPTION_TEXT, blogPostId)));
  }

  @Override
  public BlogPost createBlogPost(BlogPostDTO blogPostDTO) {
    BlogPostDTO.BlogPostDTOBuilder blogPostDTOBuilder =
        new BlogPostDTO.BlogPostDTOBuilder()
            .blogPostTitle(blogPostDTO.getBlogPostTitle())
            .blogPostText(blogPostDTO.getBlogPostText());
    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostTitle(blogPostDTOBuilder.getBlogPostTitle());
    blogPost.setBlogPostText(blogPostDTOBuilder.getBlogPostText());
    return this.blogPostRepository.save(blogPost);
  }

  @Override
  public List<BlogPostDTO> getAllBlogPosts() {
    List<BlogPost> allBlogPosts = blogPostRepository.findAll();
    return allBlogPosts.stream().map(this::mapToSimplifiedDTO).toList();
  }

  public BlogPostDTO mapToSimplifiedDTO(BlogPost blogPost) {
    return new BlogPostDTO.BlogPostDTOBuilder()
        .blogPostTitle(blogPost.getBlogPostTitle())
        .blogPostText(getShortSummary(blogPost.getBlogPostText()))
        .build();
  }

  public String getShortSummary(String blogPostText) {
    return blogPostText.length() > summaryLength
        ? blogPostText.substring(0, summaryLength) + " ..."
        : blogPostText;
  }

  @Override
  public BlogPost updateBlogPost(Long blogPostId, BlogPostDTO blogPostDTO) {
    BlogPost updatedBlogPost = getBlogPostById(blogPostId);

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

    TagDTO.TagDTOBuilder tagDTOBuilder = new TagDTO.TagDTOBuilder().tagName(tagDTO.getTagName());

    BlogPost blogPost = getBlogPostById(blogPostId);

    Tag newTag = tagService.findTagByTagName(tagDTOBuilder.getTagName());

    blogPost.getTags().add(newTag);
    blogPostRepository.save(blogPost);
  }

  @Override
  public void deleteTagFromBlogPost(Long blogPostId, TagDTO tagDTO) {
    TagDTO.TagDTOBuilder tagDTOBuilder = new TagDTO.TagDTOBuilder().tagName(tagDTO.getTagName());

    BlogPost blogPost = getBlogPostById(blogPostId);

    Tag tagToDelete = tagService.findTagByTagName(tagDTOBuilder.getTagName());

    blogPost.getTags().remove(tagToDelete);
    blogPostRepository.save(blogPost);
  }
}
