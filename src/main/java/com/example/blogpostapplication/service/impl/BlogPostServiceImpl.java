package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.BlogPostDTO;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.BlogPostService;
import com.example.blogpostapplication.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

  private final BlogPostRepository blogPostRepository;

  private final TagService tagService;

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
    return blogPostText.length() > 20 ? blogPostText.substring(0, 20) + " ..." : blogPostText;
  }

  @Override
  public BlogPost updateBlogPost(Long blogPostId, Map<String, String> blogPost) {
    BlogPost updatedBlogPost =
        this.blogPostRepository
            .findById(blogPostId)
            .orElseThrow(() -> new RecordNotFoundException("Blog post not found"));

    if (blogPost.containsKey("blogPostTitle")) {
      updatedBlogPost.setBlogPostTitle((String) blogPost.get("blogPostTitle"));
    }
    if (blogPost.containsKey("blogPostText")) {
      updatedBlogPost.setBlogPostText((String) blogPost.get("blogPostText"));
    }

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
            .orElseThrow(() -> new RecordNotFoundException("Blog post not found"));

    Tag newTag = tagService.findTagByTagName(tagDTO.getTagName());

    blogPost.getTags().add(newTag);
    blogPostRepository.save(blogPost);
  }

  @Override
  public void deleteTagFromBlogPost(Long blogPostId, TagDTO tagDTO) {
    BlogPost blogPost =
        blogPostRepository
            .findById(blogPostId)
            .orElseThrow(() -> new RecordNotFoundException("Blog post not found"));

    Tag tagToDelete = tagService.findTagByTagName(tagDTO.getTagName());

    blogPost.getTags().remove(tagToDelete);
    blogPostRepository.save(blogPost);
  }
}
